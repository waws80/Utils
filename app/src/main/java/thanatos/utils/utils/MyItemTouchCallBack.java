package thanatos.utils.utils;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created on 2017/1/10.
 * 作者：by thanatos
 * 作用：recyclerView拖拽回调类
 */

public class MyItemTouchCallBack extends ItemTouchHelper.Callback {

    private static final String TAG = "MyItemTouchCallBack";

    private ItemTouchHelerAdapterCallBack callBack;

    public MyItemTouchCallBack(ItemTouchHelerAdapterCallBack callBack) {
        this.callBack=callBack;
    }

    /**
     * 拖动排序
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //判断是否监听动作：侧滑，拖拽
        //上下滑动的时候
        int dragFlag=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipFlag=ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlag,swipFlag);
    }

    /**
     * 当拖拽的时候回调
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
            target) {
        callBack.isItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    /**
     * 侧滑的时候回调
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        callBack.isItemSwip(viewHolder.getAdapterPosition());
    }

    /**
     * 允许长按拖拽
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, float
            dY, int actionState, boolean isCurrentlyActive) {
        //侧滑缩放、透明度
        if (actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
            //0-1:0-width
            float alpha=1-Math.abs(dX)/viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
//            viewHolder.itemView.setScaleX(alpha);
//            viewHolder.itemView.setScaleY(alpha);
            if (alpha<=0){//重置透明度，防止服复用导致的空白。
                viewHolder.itemView.setAlpha(1);
//                viewHolder.itemView.setScaleX(1);
//                viewHolder.itemView.setScaleY(1);
            }
            viewHolder.itemView.setTranslationX(dX);
//            if (dX<=-0.3f*viewHolder.itemView.getWidth()){
//                viewHolder.itemView.setTranslationX(-0.3f*viewHolder.itemView.getWidth());
//            }else {
//                viewHolder.itemView.setTranslationX(dX);
//
//            }
        }else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }


}
