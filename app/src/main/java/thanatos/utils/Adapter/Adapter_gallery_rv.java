package thanatos.utils.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.List;

import edu.t_imageloader.ImageLoader;
import edu.t_imageloader.utils.CallBack;
import edu.t_imageloader.utils.DoubleCache;
import thanatos.utils.R;
import thanatos.utils.base.BaseRVAdapter;
import thanatos.utils.entity.PicInfo;

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
        return null;
    }

    @Override
    public View getFooter() {
        return null;
    }

    @Override
    public void setData(final MyRecyclerViewHolder holder, int position, Object data) {
        final PicInfo picInfo= (PicInfo) data;
       ImageLoader.getInstance(new DoubleCache("thanatos-imageload",60),false).load(picInfo.getUrl(), (ImageView)
               holder.setView(R.id.iv_item_gallery_rv)).callBack(new CallBack() {
           @Override
           public void start() {

           }

           @Override
           public void loading() {

           }

           @Override
           public void success() {

           }

           @Override
           public void error(int code) {

           }
       });
        final CheckBox cb=holder.setView(R.id.rb_item_gallery_rv);
        cb.setChecked(picInfo.isChecked());
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picInfo.setChecked(cb.isChecked());
            }
        });

    }
}
