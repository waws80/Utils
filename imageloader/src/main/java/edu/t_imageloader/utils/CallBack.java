package edu.t_imageloader.utils;

/**
 * Created by lxf52 on 2017/1/9.
 */

public interface CallBack {
    void start();
    void loading();
    void success();
    void error(int code);
}
