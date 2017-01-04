package thanatos.utils.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created on 2017/1/4.
 * 作者：by Administrator
 * 作用：rvAdapter基类
 */

public abstract class BaseRVAdapter  extends RecyclerView.Adapter<BaseRVAdapter
        .MyRecyclerViewHolder>{

    private  List<?> dataList;


    private static final int HEADER=0x100;
    private static final int FOOTER=0x110;


    private OnItemClick itemClick;

    public BaseRVAdapter(List<?> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (getHeader()!=null&&position==0){
            return HEADER;
        }else if (getFooter()!=null&&position==dataList.size()-1){
            return FOOTER;
        }else {
            return super.getItemViewType(position);
        }

    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==HEADER){
            return new MyRecyclerViewHolder(getHeader());
        }else if (viewType==FOOTER){
            return new MyRecyclerViewHolder(getFooter());
        }else {
            return  new MyRecyclerViewHolder(getItem());
        }

    }

    @Override
    public  void onBindViewHolder(MyRecyclerViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick==null) throw  new NullPointerException("itemClick is null");
                itemClick.itemClick(dataList.get(position),position);
            }
        });
        setData(holder,position,dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder{

        private SparseArray<View> views=new SparseArray<>();

         MyRecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public <V extends View> V setView(int id){
            View view = views.get(id);
            if (view==null){
                view=itemView.findViewById(id);
                views.put(id,view);
            }
            return (V) view;
        }

    }

    public abstract View getItem();

    public abstract View getHeader();

    public abstract View getFooter();
    public abstract  void setData(MyRecyclerViewHolder holder, int position,Object data) ;

    public void addItemClickListener(OnItemClick itemClick){
        this.itemClick=itemClick;

    }

     public interface OnItemClick{
        void itemClick(Object data,int position);
    }


}
