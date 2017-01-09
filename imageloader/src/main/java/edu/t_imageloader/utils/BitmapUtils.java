package edu.t_imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片处理类
 */

public class BitmapUtils {


    private static final String TAG = "BitmapUtils";
    private static Bitmap getBitmap(String path, ImageView imageView) {

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(path,options);
        ImageSizeUtil.ImageSize imageSize=ImageSizeUtil.getImageViewSize(imageView);
        options.inSampleSize=ImageSizeUtil.caculateInSampleSize(options,imageSize.width,imageSize.height);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeFile(path,options);
    }

    private static Bitmap getBitmapFromByte(byte[] path, ImageView imageView) {

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeByteArray(path,0,path.length);
        ImageSizeUtil.ImageSize imageSize=ImageSizeUtil.getImageViewSize(imageView);
        options.inSampleSize=ImageSizeUtil.caculateInSampleSize(options,imageSize.width,imageSize.height);
        options.inJustDecodeBounds=false;
        return  BitmapFactory.decodeByteArray(path,0,path.length);
    }


    public static Object[] getNetBitmap(String path, ImageView imageView) {
        Object[] obj;
        try {

            HttpURLConnection conn= (HttpURLConnection) new URL(path).openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            int responseCode = conn.getResponseCode();
            if (responseCode==200){
                Log.w(TAG, "getNetBitmap: success" );
                obj=new Object[]{responseCode,getBitmapFromByte(readStream(conn.getInputStream()),imageView)};
                return obj;
            }else {
                obj=new Object[]{responseCode,null};
                return obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Bitmap getLocalBitmap(String path, ImageView imageView) {
        return getBitmap(path,imageView);
    }

    public static byte[] readStream(InputStream is) throws Exception{
        byte[] bytes = new byte[1024];
        int leng;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while((leng=is.read(bytes)) != -1){
            baos.write(bytes, 0, leng);
        }
        return baos.toByteArray();
    }
}
