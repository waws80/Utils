package thanatos.utils.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.format.Formatter;

import java.util.ArrayList;
import java.util.List;

import thanatos.utils.entity.PicInfo;

/**
 * Created by lxf52 on 2017/1/5.
 */

public class PhotoUtils {

    public static List<PicInfo> getPhonePic(Context context){

        List<PicInfo> picInfoList=new ArrayList<>();
        Cursor cursor=context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,null,null,null);
        if (cursor==null){
            return null;
        }else {
            while (cursor.moveToNext()){
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                String size = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                String fileSize = Formatter.formatFileSize(context, Long.parseLong(size));
                PicInfo picInfo=new PicInfo();
                picInfo.setUrl(url);
                picInfo.setSize(fileSize);
                picInfoList.add(picInfo);
            }
            cursor.close();
            return picInfoList;
        }

    }
}
