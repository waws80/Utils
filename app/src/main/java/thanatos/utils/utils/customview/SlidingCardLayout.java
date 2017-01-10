package thanatos.utils.utils.customview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import thanatos.utils.utils.behavior.SlidingBehavior;

/**
 * Created on 2017/1/10.
 * 作者：by thanatos
 * 作用：
 */
@CoordinatorLayout.DefaultBehavior(SlidingBehavior.class)
public class SlidingCardLayout extends LinearLayout {
    private static final String TAG = "SlidingCardLayout";
    public SlidingCardLayout(Context context) {
        this(context,null);
    }

    public SlidingCardLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        View childAt = getChildAt(0);
        if (childAt!=null&&childAt instanceof RecyclerView){
                RecyclerView rv= (RecyclerView) childAt;

        }

    }
}
