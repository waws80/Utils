package thanatos.utils.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import thanatos.utils.Adapter.Adapter_main_rv;
import thanatos.utils.R;
import thanatos.utils.base.BaseRVAdapter;
import thanatos.utils.utils.ItemDecoration_main_rv;
import thanatos.utils.utils.List_main_rv_data;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_main);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.srfl_main);
        final List<String> stringList= List_main_rv_data.getInstance().initList();
        final GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position==0||position==stringList.size()-1){
                    return gridLayoutManager.getSpanCount();
                }else {
                    return 1;
                }

            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new ItemDecoration_main_rv(30));
        final BaseRVAdapter adapterMainRv=new Adapter_main_rv(stringList,this);
        mRecyclerView.setAdapter(adapterMainRv);
        refreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorGray,R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapterMainRv.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });
        adapterMainRv.addItemClickListener(new BaseRVAdapter.OnItemClick() {
            @Override
            public void itemClick(Object data, int position) {
                   intent(data,position);
            }
        });
    }

    private void intent(Object data, int position) {
        Intent intent=new Intent();
        intent.putExtra("value",(String) data);
        switch (position){
            case 0:
                Toast.makeText(this, "我是头部", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                intent.setClass(this,JPEGActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(this,Gallery.class);
                startActivity(intent);
                break;
            case 3:
                intent.setClass(this,DragViewActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent.setClass(this,DistributeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
