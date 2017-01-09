package edu.t_imageloader.utils;

import android.graphics.Bitmap;

/**
 * 双缓存类
 */

public class DoubleCache implements ImageCache {
    private ImageCache memeryCache;
    private ImageCache disCache;

    public DoubleCache(String path,int precent) {
        memeryCache=new MemeryCache();
        disCache=new DiskCache(path,precent);
    }

    @Override
    public Bitmap getCache(String path) {
        Bitmap bitmap=memeryCache.getCache(path);
        if (bitmap==null){
            bitmap=disCache.getCache(path);
        }
        return bitmap;
    }

    @Override
    public void putCache(String path, Bitmap bitmap) {
        memeryCache.putCache(path, bitmap);
        disCache.putCache(path, bitmap);

    }
}
