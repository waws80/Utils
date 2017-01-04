package thanatos.utils.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created on 2017/1/4.
 * 作者：by Administrator
 * 作用：RecyclerView条目分割线
 */

public class ItemDecoration_main_rv extends RecyclerView.ItemDecoration {
    private int space;

    public ItemDecoration_main_rv(int space) {
        super();
        this.space=space;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom=space;
        outRect.left=space/2;
        outRect.right=space/2;

    }
}
