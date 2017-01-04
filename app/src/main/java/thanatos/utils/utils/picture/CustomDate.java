package thanatos.utils.utils.picture;


import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取时间格式
 * Created by lxf on 2016/7/17.
 */

public class CustomDate {
    public static String getDate2D() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(System.currentTimeMillis()));// 2016年07月17日
    }

    public static String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format1.format(new Date(System.currentTimeMillis()));// 2016-07-17 23:41:31
    }

}
