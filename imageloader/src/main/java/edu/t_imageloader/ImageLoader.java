package edu.t_imageloader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import edu.t_imageloader.utils.BitmapUtils;
import edu.t_imageloader.utils.CallBack;
import edu.t_imageloader.utils.ImageCache;
import edu.t_imageloader.utils.MemeryCache;

/***
 *图片加载类
 */
public class ImageLoader {

    private static final String TAG = "ImageLoader";
    private  ImageCache imageCache;
    private static final int threadCount=Runtime.getRuntime().availableProcessors();
    private ExecutorService mExecutorService;


    private   HandlerThread handlerThread;

    private static ImageLoader mImageLoader;


    private LinkedList<Runnable> mTaskQueue;

    private  Handler mUIHandler;

    private  Handler mThreadHandler;

    private  Handler mCallBackHandler;

    private static final Semaphore semaphore=new Semaphore(0);

    private boolean isNetWork;

    private CallBack mCallBack;

    private ImageLoader(ImageCache cache, boolean isNetWork){
        this.isNetWork=isNetWork;
        if (isNetWork){
            imageCache=cache;
        }else {
            imageCache=new MemeryCache();
        }
        mExecutorService= Executors.newFixedThreadPool(threadCount);
        mTaskQueue=new LinkedList<>();
        handlerThread=new HandlerThread("threadpool-looper");
        callBackHandler();
        doInBackground();
    }

    private void callBackHandler() {
        mCallBackHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ImageHolder holder= (ImageHolder) msg.obj;
                String path=holder.path;
                ImageView iv=holder.imageView;
                int errorCode=holder.errorCode;
                if (mCallBack!=null&&iv.getTag().toString().equals(path)){
                    mCallBack.error(errorCode);
                }
            }
        };
    }

    public static ImageLoader getInstance(ImageCache cache,boolean isNetWork){
        if (mImageLoader==null){
            synchronized (ImageLoader.class){
                if (mImageLoader==null){
                    mImageLoader=new ImageLoader(cache,isNetWork);
                }
            }
        }
        return mImageLoader;
    }

    public ImageLoader load(String path, ImageView imageView){
        imageView.setTag(path);
        if (mCallBack!=null&&imageView.getTag().toString().equals(path)){
            mCallBack.start();
            mCallBack.loading();
        }
        getImageHolder();
        Bitmap bitmap=imageCache.getCache(path);
        if (bitmap!=null){
            postBitmap(path,imageView,bitmap);

        }else {
            addTask(buildTask(path,imageView));
        }

        return this;
    }

    private void doInBackground() {

        handlerThread.start();
        new Thread(){
            @Override
            public void run() {
                super.run();
                mThreadHandler=new Handler(handlerThread.getLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mExecutorService.execute(getTask());
                    }
                };
                semaphore.release();
            }
        }.start();
    }

    private void getImageHolder() {
        if (mUIHandler==null)
        mUIHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ImageHolder imageHolder= (ImageHolder) msg.obj;
                String path=imageHolder.path;
                Bitmap bitmap=imageHolder.bitmap;
                ImageView view=imageHolder.imageView;
                if (view.getTag().toString().equals(path)){
                    view.setImageBitmap(bitmap);
                    if (mCallBack!=null){
                        mCallBack.success();
                    }
                }

            }
        };
    }

    private synchronized void addTask(Runnable runnable) {
        if (mTaskQueue!=null)
            mTaskQueue.add(runnable);
        if (mThreadHandler==null) try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mThreadHandler.sendEmptyMessage(0x100);

    }

    private Runnable getTask(){
        return mTaskQueue.removeFirst();
    }

    private Runnable buildTask(final String path, final ImageView imageView) {
        return new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
                if(imageCache!=null&&imageCache.getCache(path)!=null){
                    bitmap=imageCache.getCache(path);
                }else {
                    if (isNetWork){
                        Object[] objects = BitmapUtils.getNetBitmap(path, imageView);
                        if (objects!=null){
                            bitmap= (Bitmap) objects[1];
                            if (bitmap==null){
                                Message msg=Message.obtain();
                                ImageHolder holder=new ImageHolder();
                                holder.imageView=imageView;
                                holder.path=path;
                                holder.errorCode= (int) objects[0];
                                msg.obj=holder;
                                mCallBackHandler.sendMessage(msg);
                            }
                        }else {
                            bitmap=null;
                            Message msg=Message.obtain();
                            ImageHolder holder=new ImageHolder();
                            holder.imageView=imageView;
                            holder.path=path;
                            holder.errorCode=Integer.MAX_VALUE;
                            msg.obj=holder;
                            mCallBackHandler.sendMessage(msg);
                        }
                    }else {
                        bitmap= BitmapUtils.getLocalBitmap(path,imageView);
                    }
                }

                if (bitmap!=null){
                    imageCache.putCache(path,bitmap);
                    postBitmap(path,imageView,bitmap);
                }else {
                    Message msg=Message.obtain();
                    ImageHolder holder=new ImageHolder();
                    holder.imageView=imageView;
                    holder.path=path;
                    holder.errorCode= Integer.MAX_VALUE;
                    msg.obj=holder;
                    mCallBackHandler.sendMessage(msg);
                }
            }
        };
    }

    private static class ImageHolder{
        String path;
        Bitmap bitmap;
        ImageView imageView;
        int errorCode;
    }

    private void postBitmap(String path,ImageView imageView,Bitmap bitmap){
        Message message = Message.obtain();
        ImageHolder holder = new ImageHolder();
        holder.bitmap = bitmap;
        holder.path = path;
        holder.imageView = imageView;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    public void callBack(CallBack callBack){
        this.mCallBack=callBack;

    }


}