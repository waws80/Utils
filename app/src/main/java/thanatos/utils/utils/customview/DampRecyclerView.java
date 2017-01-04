package thanatos.utils.utils.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by lxf52 on 2017/1/4.
 */

public class DampRecyclerView extends RecyclerView {
    private static final String TAG = "DampRecyclerView";

    private static final int OFFSET = 200;
    private int maxOverScrollYValue;
    public DampRecyclerView(Context context) {
        this(context,null);

    }

    public DampRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        final DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        maxOverScrollYValue = (int) (metrics.density * OFFSET);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        Log.w(TAG, "overScrollBy: -----"+deltaY );
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollYValue, isTouchEvent);
    }
}
