package thanatos.utils.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import thanatos.utils.R;
import thanatos.utils.base.BaseRVAdapter;
import thanatos.utils.entity.PicInfo;
import thanatos.utils.utils.ImageLoader;

/**
 * Created by lxf52 on 2017/1/5.
 */

public class Adapter_gallery_rv extends BaseRVAdapter {

    private Context context;
    public Adapter_gallery_rv(List<?> dataList,Context context) {
        super(dataList);
        this.context=context;
    }

    @Override
    public View getItem() {
        return View.inflate(context, R.layout.item_gallery_rv,null);
    }

    @Override
    public View getHeader() {
        return View.inflate(context, R.layout.item_gallery_rv,null);
    }

    @Override
    public View getFooter() {
        return View.inflate(context, R.layout.item_gallery_rv,null);
    }

    @Override
    public void setData(MyRecyclerViewHolder holder, int position, Object data) {
        PicInfo picInfo= (PicInfo) data;
        ImageLoader.getInstance(picInfo.getUrl(), (ImageView) holder.setView(R.id.iv_item_gallery_rv));

    }
}
