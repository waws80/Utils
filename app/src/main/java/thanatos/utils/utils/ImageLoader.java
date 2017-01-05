package thanatos.utils.utils;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by lxf52 on 2017/1/5.
 */

public class ImageLoader {

    private static ImageLoader imageLoader;

    private static Semaphore semaphore=new Semaphore(0);

    private ExecutorService executorService=Executors.newFixedThreadPool(10);

    private ImageLoader(String url,  ImageView imageView){
        init(url,imageView);
    }

    private void init(String url,  ImageView imageView) {
        executorService.execute(prasePic(url,imageView));
    }


    public static ImageLoader getInstance(String url,  ImageView imageView){
        if (imageLoader==null){
            synchronized (ImageLoader.class){
                if (imageLoader==null){
                    imageLoader=new ImageLoader(url,imageView);
                }
            }
        }
        return imageLoader;
    }

    private    Runnable prasePic(final String url, final ImageView imageView) {
        return new Runnable(){
            @Override
            public void run() {
                try {
                    final BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inJustDecodeBounds=true;
                    BitmapFactory.decodeFile(url,options);
                    int ow=options.outWidth;
                    int oh=options.outHeight;
                    int reqWidth=ImageViewSizeUtils.getImageViewSize(imageView)[0];
                    int reqHeight=ImageViewSizeUtils.getImageViewSize(imageView)[1];
                    int inSampleSize=1;
                    if (ow > reqWidth|| oh > reqHeight) {
                        int widthRadio = Math.round(ow * 1.0f / reqWidth);
                        int heightRadio = Math.round(ow * 1.0f / reqHeight);

                        inSampleSize = Math.max(widthRadio, heightRadio);
                    }
                    options.inSampleSize=inSampleSize;
                    options.inJustDecodeBounds=false;
                    Looper.prepare();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("---------------"+url);
                            imageView.setImageBitmap(BitmapFactory.decodeFile(url,options));
                        }
                    });
                    Looper.loop();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
}
