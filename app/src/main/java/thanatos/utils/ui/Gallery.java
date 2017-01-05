package thanatos.utils.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import thanatos.utils.Adapter.Adapter_gallery_rv;
import thanatos.utils.R;
import thanatos.utils.base.BaseRVAdapter;
import thanatos.utils.entity.PicInfo;
import thanatos.utils.utils.ItemDecoration_main_rv;
import thanatos.utils.utils.PhotoUtils;

public class Gallery extends AppCompatActivity {

    private static final String TAG = "thanatos";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("相册");
       recyclerView= (RecyclerView) findViewById(R.id.gallery_rv);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE,R.id.pic,1,"获取图片");
        menu.add(Menu.NONE,R.id.single,1,"单行");
        menu.add(Menu.NONE,R.id.two,1,"两行");
        menu.add(Menu.NONE,R.id.more,1,"瀑布流");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pic:
                GridLayoutManager manager=new GridLayoutManager(this,2);
                recyclerView.setLayoutManager(manager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new ItemDecoration_main_rv(20));
                Adapter_gallery_rv adapter_gallery_rv = new Adapter_gallery_rv(PhotoUtils.getPhonePic(this), this);
                recyclerView.setAdapter(adapter_gallery_rv);
                adapter_gallery_rv.addItemClickListener(new BaseRVAdapter.OnItemClick() {
                    @Override
                    public void itemClick(Object data, int position) {
                        PicInfo picInfo= (PicInfo) data;
                        Toast.makeText(Gallery.this, ""+picInfo.getUrl(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.single:
                break;
            case R.id.two:
                break;
            case R.id.more:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
