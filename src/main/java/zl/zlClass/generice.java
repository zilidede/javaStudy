package zl.zlClass;

import java.io.File;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/*
 * @Description: 泛型测试
 * @Param:
 * @Author: zl
 * @Date: 2019/5/17 12:59
 */
public class generice {
    class stat<T extends Number>{
       private double obj ;
       stat(T t){


       }
       public double getObj(){
           return obj;
       }

    }
    public void test(){
        stat <Double> st= new stat<Double>(3.4);
        System.out.println(st.getObj());

    }
    public static void main(String[] args) {
        generice g=new generice();
        g.test();

    }
}
