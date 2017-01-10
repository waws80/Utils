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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)actionBar.setTitle("相册");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery_rv);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
