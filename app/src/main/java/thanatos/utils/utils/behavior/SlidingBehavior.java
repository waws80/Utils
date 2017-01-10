package thanatos.utils.utils.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.LinearLayout;

import thanatos.utils.R;
import thanatos.utils.utils.customview.SlidingCardLayout;

/**
 * Created on 2017/1/10.
 * 作者：by thanatos
 * 作用：
 */

public class SlidingBehavior extends CoordinatorLayout.Behavior<SlidingCardLayout> {

    private static final String TAG = "SlidingBehavior";


    private int mInitialOffset;

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View
            directTargetChild, View target, int nestedScrollAxes) {
        //监听滑动方向（垂直）
        mInitialOffset=coordinatorLayout.getContext().getResources().getDimensionPixelSize(R.dimen.activity_distribute_one);
        boolean isVertical=(nestedScrollAxes& ViewCompat.SCROLL_AXIS_VERTICAL)!=0;
        return isVertical && child==directTargetChild;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View target, int dx, int
            dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, final SlidingCardLayout child, View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        //控制自己滑动
        int shift=scroll(dyUnconsumed, 0,mInitialOffset);
        if (dyConsumed>0&&dyUnconsumed==0){//上滑
            if (child.getTop()>0){
                child.setTop(child.getTop()-dyConsumed);
            }
        }else
            if (dyConsumed==0&&dyUnconsumed<0){//下滑
            if (child.getTop()<mInitialOffset){
                child.setTop(child.getTop()+shift);
            }
        }

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    private int scroll( int dy, int minoffset, int maxoffset) {
        //dy:[0-初始化的高度]
        int offset=clamp(-dy,minoffset,maxoffset);
        return offset;
    }

    public int clamp(int i,int minoffset,int maxoffset){
        if (i>maxoffset){
            return maxoffset;
        }else if (i<minoffset){
            return minoffset;
        }else {
            return i;
        }
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, SlidingCardLayout child, int layoutDirection) {
        //摆放所有子控件
        parent.onLayoutChild(child,layoutDirection);
        //给第二个孩子设置偏移量
        View childAt = parent.getChildAt(1);
        if (child==childAt&&childAt instanceof LinearLayout){
            child.offsetTopAndBottom(parent.getContext().getResources().getDimensionPixelSize(R.dimen.activity_distribute_one));
        }
        mInitialOffset=childAt.getTop();
        return true;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, SlidingCardLayout child, int parentWidthMeasureSpec, int
            widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int heightMeasureSpace=View.MeasureSpec.getSize(parentHeightMeasureSpec);
        child.measure(parentWidthMeasureSpec,View.MeasureSpec.makeMeasureSpec(heightMeasureSpace, View.MeasureSpec.EXACTLY));
        return true;
    }

}
