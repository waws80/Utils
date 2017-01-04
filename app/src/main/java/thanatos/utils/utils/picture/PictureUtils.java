package thanatos.utils.utils.picture;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.WeakReference;

import static thanatos.utils.utils.picture.Pic_Constant.REQUEST_CODE_CAPTURE_CAMEIA;
import static thanatos.utils.utils.picture.Pic_Constant.REQUEST_CODE_PICK_IMAGE;
import static thanatos.utils.utils.picture.Pic_Constant.mFilePath;


/**
 * 从相册和相册返回照片信息类
 */
public class  PictureUtils {

    private static PictureUtils mPictureUtils;

    private static WeakReference<Activity> mActivityWeakReference=null;

     private  PictureUtils(Activity activity) {
         mActivityWeakReference=new WeakReference<>(activity);
    }

    public static PictureUtils register(Activity activity){
        if (mPictureUtils==null){
            synchronized (PictureUtils.class){
                if (mPictureUtils==null)
                    mPictureUtils=new PictureUtils(activity);
            }
        }
        return mPictureUtils;

    }

    public static void unregister(){
        if (mActivityWeakReference!=null){
            mActivityWeakReference.clear();
            mActivityWeakReference=null;
        }
    }

    /**
     * 返回的是原图
     * @param fileName 保存图片的文件夹名字
     */
    public static void startCamera(String fileName){
        // 获取SD卡路径
        mFilePath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(mFilePath + "/"+fileName+"/");
        file.mkdirs();
        // 文件名
        mFilePath = mFilePath + "/"+fileName+"/" + CustomDate.getDate()+"photo.png";
        // 指定拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 加载路径
        Uri uri = Uri.fromFile(new File(mFilePath));
        // 指定存储路径，这样就可以保存原图了
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        // 拍照返回图片
        mActivityWeakReference.get().startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
    }

    public static void startPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");//相片类型
        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mActivityWeakReference.get().startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    public String getPic2Path(int requestCode, Intent data){
        try {
            String path="";
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {//相册获取到的数据
                Uri uri = data.getData();
                //to do find the path of pic by uri
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor =mActivityWeakReference.get(). managedQuery(uri, proj, null, null, null);
                //按我个人理解 这个是获得用户选择的图片的索引值

                if (cursor==null){
                    path = MiPictureHelper.getPath(mActivityWeakReference.get(), uri);  // 获取图片路径的方法调用
                }else {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    path = cursor.getString(column_index);
                }

            } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {//从相机获取到的数据
                path=mFilePath;
            }
            return path;

        }catch (Exception e){
            e.printStackTrace();
            return null;

        }




    }

    public Bitmap getPic2Bitmap( int requestCode, Intent data){
        try {
            return getimagefor300(getPic2Path(requestCode,data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public String getPic2String(int requestCode, Intent data){

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            getPic2Bitmap(requestCode,data).compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] result = baos.toByteArray();//转换成功了
            return Base64.encodeToString(result, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }


    public String getPicFromBitmap2String(Bitmap data){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            data.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] result = baos.toByteArray();//转换成功了
            return Base64.encodeToString(result, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 通过获取图片路径对图片进行压缩
     *
     * @param srcPath
     * @return
     */
    private static Bitmap getimagefor300(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap;//此时返回bm为空

        BitmapFactory.decodeFile(srcPath, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 300;//这里设置高度为800f
        float ww = 300;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inJustDecodeBounds = false;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public Bitmap getimageforSize(String srcPath, int width, int height) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap;//此时返回bm为空

        BitmapFactory.decodeFile(srcPath, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = height;//这里设置高度为800f
        float ww = width;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inJustDecodeBounds = false;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 通过对bitmap的压缩
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
                image.compress(Bitmap.CompressFormat.PNG, 100, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
