package thanatos.utils.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import thanatos.utils.Adapter.Adapter_distribute_tv;
import thanatos.utils.R;
import thanatos.utils.utils.ItemDecoration_main_rv;

/**
 * 界面布局分布式滑动
 */
public class DistributeActivity extends AppCompatActivity {

    private RecyclerView rv_activity_distribute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute);
        rv_activity_distribute= (RecyclerView) findViewById(R.id.rv_activity_distribute);
        List<String> stringList=new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            stringList.add(""+i);
        }
        rv_activity_distribute.addItemDecoration(new ItemDecoration_main_rv(20));
        rv_activity_distribute.setLayoutManager(new GridLayoutManager(this,1));
        rv_activity_distribute.setItemAnimator(new DefaultItemAnimator());
        rv_activity_distribute.setAdapter(new Adapter_distribute_tv(stringList,this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
