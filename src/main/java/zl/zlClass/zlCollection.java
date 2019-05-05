package zl.zlClass;

import java.util.ArrayList;
import java.util.List;

/*
 * @Description: 集合
 * @Param:
 * @Author: zl
 * @Date: 2019/4/14 10:26
 */
public class zlCollection{
    public boolean RemoveCollLen(ArrayList list, int len){

        return false;

    }
    public static void  test(){
            List l = new ArrayList();
            l.add("123");
            l.add(12);
            for(int i=0;i<l.size();i++){
                System.out.println(l.get(i));
            }

    }
    public static void main(String[] args) {
        test();
    }
}
