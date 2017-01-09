package edu.t_imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import net.bither.util.NativeUtil;

import java.io.File;

/**
 * 硬盘缓存类
 */

public class DiskCache implements ImageCache {

    private static final String TAG = "DiskCache";
    //图片压缩百分比默认压缩到90%
    private static int PERCENT=90;

    private String cacheDir;

    public DiskCache(String cacheDir,int percent) {
        this.cacheDir = cacheDir;
        if (percent>0&&percent<100){
            PERCENT=percent;
        }

    }

    @Override
    public Bitmap getCache(String path) {
        File file=new File("sdcard/"+cacheDir,MD5Utils.getMd5(path));
        Log.w(TAG, "getCache: find image in "+path+" diskcache" );
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    @Override
    public void putCache(String path, Bitmap bitmap) {
        File file=new File("sdcard/"+cacheDir,MD5Utils.getMd5(path));
        NativeUtil.compressBitmap(bitmap,PERCENT,file.getAbsolutePath(),true);
    }
}
