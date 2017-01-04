package thanatos.utils.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import thanatos.utils.R;
import thanatos.utils.base.BaseRVAdapter;

/**
 * Created on 2017/1/4.
 * 作者：by Administrator
 * 作用：
 */

public class Adapter_main_rv extends BaseRVAdapter {

    private Context context;


    public  Adapter_main_rv(List<String> dataList,Context context) {
        super(dataList);
        this.context=context;
    }


    @Override
    public void setData(MyRecyclerViewHolder holder, int position,Object data) {
        TextView tv=  holder.setView(R.id.tv_item_main_rv);
        tv.setText((CharSequence) data);
        tv.setBackgroundColor(Color.WHITE);
    }

    @Override
    public View getItem() {
        return View.inflate(context, R.layout.item_main_rv,null);
    }

    @Override
    public View getHeader() {
        return View.inflate(context, R.layout.item_main_rv,null);
    }

    @Override
    public View getFooter() {
        return View.inflate(context, R.layout.item_main_rv,null);
    }



}
