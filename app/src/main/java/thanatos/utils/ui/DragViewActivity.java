package thanatos.utils.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import thanatos.utils.Adapter.Adapter_dragView_rv;
import thanatos.utils.R;
import thanatos.utils.utils.ItemDecoration_main_rv;
import thanatos.utils.utils.MyItemTouchCallBack;

/**
 * 拖拽的recyclerView
 */
public class DragViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);
        RecyclerView drag_view = (RecyclerView) findViewById(R.id.drag_view);
        GridLayoutManager manager=new GridLayoutManager(this,1);
        drag_view.setLayoutManager(manager);
        drag_view.setItemAnimator(new DefaultItemAnimator());
        drag_view.addItemDecoration(new ItemDecoration_main_rv(20));
        List<String> stringList=new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            stringList.add(i+"");
        }
        Adapter_dragView_rv adapter_dragView_rv = new Adapter_dragView_rv(stringList, this);
        drag_view.setAdapter(adapter_dragView_rv);
        ItemTouchHelper.Callback callback=new MyItemTouchCallBack(adapter_dragView_rv);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(drag_view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
