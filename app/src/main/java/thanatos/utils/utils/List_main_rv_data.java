package thanatos.utils.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/1/4.
 * 作者：by Administrator
 * 作用：数据源
 */

public class List_main_rv_data {

private static List_main_rv_data listMainRvData;

    private List_main_rv_data() {
    }

    public static List_main_rv_data getInstance(){
        if (listMainRvData==null){
            synchronized (List_main_rv_data.class){
                if (listMainRvData==null){
                    listMainRvData=new List_main_rv_data();
                }
            }
        }
        return listMainRvData;
    }

    public List<String> initList(){
        List<String> stringList=new ArrayList<>();
        stringList.add("我是头部");

        stringList.add("相册");

        stringList.add("我是底部");
        return stringList;
    }
}
