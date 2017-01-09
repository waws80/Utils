package edu.t_imageloader.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by lxf52 on 2017/1/7.
 */

public class MemeryCache implements ImageCache {
    private static final String TAG = "MemeryCache";

    private int cacheSize= (int) (Runtime.getRuntime().maxMemory()/8);

    private LruCache<String ,Bitmap > mCache=new LruCache<String, Bitmap>(cacheSize){
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes()*value.getHeight();
        }
    };
    @Override
    public Bitmap getCache(String path) {
        Log.w(TAG, "getCache: find image in "+path+" memerycache" );
        return mCache.get(path);
    }

    @Override
    public void putCache(String path, Bitmap bitmap) {
            mCache.put(path, bitmap);
    }
}
