package thanatos.utils.utils;

/**
 * Created on 2017/1/10.
 * 作者：by thanatos
 * 作用：
 */

public interface ItemTouchHelerAdapterCallBack {
    boolean isItemMove(int fromPosition,int toPosition);

    void isItemSwip(int position);
}
