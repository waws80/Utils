package thanatos.utils.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by lxf52 on 2017/1/5.
 */

public class ImageViewSizeUtils {


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static int [] getImageViewSize(ImageView imageView){
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        int width=imageView.getWidth();
        int height=imageView.getHeight();
        if (width<=0){
            if (params!=null&&params.width>=0)width=params.width;
        }
        if (width<=0)width=imageView.getMaxWidth();
        if (width<=0)width=displayMetrics.widthPixels;

        if (height<=0){
            if (params!=null&&params.height>=0)height=params.height;
        }
        if (height<=0)height=imageView.getMaxHeight();
        if (height<=0)height=displayMetrics.heightPixels;




        return new int[]{width,height};

    }
}
