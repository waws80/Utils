package thanatos.utils.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import net.bither.util.NativeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import thanatos.utils.R;

public class JPEGActivity extends AppCompatActivity {

    private ImageView iv,iv1;
    private int quality=50;

    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpeg);
        iv= (ImageView) findViewById(R.id.iv);
        iv1= (ImageView) findViewById(R.id.iv1);
        handler=new MyHandler();
        testJPEG();
    }

    private void testJPEG() {
        new Thread(){
            @Override
            public void run() {
                try {
                    InputStream inputStream = getResources().getAssets().open("test.jpg");
                    Bitmap bit= BitmapFactory.decodeStream(inputStream);
                    Message msg=Message.obtain();
                    msg.obj=bit;
                    msg.what=1;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        new Thread(){
            @Override
            public void run() {
                try {
                    InputStream inputStream = getResources().getAssets().open("test.jpg");
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    File originalFile = new File("sdcard/", "原图.jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            originalFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    File file=new File("sdcard/","压缩后的图.jpg");
                    NativeUtil.compressBitmap(bitmap,20,file.getAbsolutePath(),true);
                    Message msg1=Message.obtain();
                    msg1.obj=bitmap;
                    msg1.what=2;
                    handler.sendMessage(msg1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


     class MyHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                iv.setImageBitmap((Bitmap) msg.obj);
            }else if (msg.what==2){
                iv1.setImageBitmap((Bitmap) msg.obj);
            }




        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
