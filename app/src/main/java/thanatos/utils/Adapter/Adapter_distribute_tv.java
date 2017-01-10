package thanatos.utils.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import thanatos.utils.R;
import thanatos.utils.base.BaseRVAdapter;

/**
 * Created on 2017/1/10.
 * 作者：by thanatos
 * 作用：
 */

public class Adapter_distribute_tv extends BaseRVAdapter {
    private final Context context;

    public Adapter_distribute_tv(List<?> dataList, Context context) {
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
}
