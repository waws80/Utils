package thanatos.utils.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import thanatos.utils.R;
import thanatos.utils.base.BaseRVAdapter;
import thanatos.utils.utils.ItemTouchHelerAdapterCallBack;

/**
 * Created on 2017/1/10.
 * 作者：by thanatos
 * 作用：
 */

public class Adapter_dragView_rv extends BaseRVAdapter implements ItemTouchHelerAdapterCallBack{

    private Context context;
    public Adapter_dragView_rv(List<?> dataList,Context context) {
        super(dataList);
        this.context=context;
    }

    @Override
    public View getItem() {
        return View.inflate(context, R.layout.item_main_rv,null);
    }

    @Override
    public View getHeader() {
        return null;
    }

    @Override
    public View getFooter() {
        return null;
    }

    @Override
    public void setData(MyRecyclerViewHolder holder, int position, Object data) {
        TextView view = holder.setView(R.id.tv_item_main_rv);
        view.setText((CharSequence) data);
    }

    @Override
    public boolean isItemMove(int fromPosition, int toPosition) {
        //1.刷新数据 ---交换两个条目的数据
        Collections.swap(dataList,fromPosition,toPosition);
        //2.刷新界面
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void isItemSwip(int position) {
        //1.刷新数据
        dataList.remove(position);
        //2.刷新界面
        notifyItemRemoved(position);



    }
}
