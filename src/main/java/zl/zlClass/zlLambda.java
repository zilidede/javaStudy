package zl.zlClass;

import java.util.function.Function;

/*
 * @Description: lambda表达式
 * @Param:
 * @Author: zl
 * @Date: 2019/5/23 20:31
 */
public class zlLambda {
    interface myNumber{
        double getValue(int n);
    }
    interface  myOut<T>{
        T t();

    }
    interface  myNumber1{
        double getValue();
    }
    static  class Test{
       static double getDouble(){
            return 43;
        }
    }
    static double doubleOf(myNumber1 d){
        return  d.getValue();
    }

    public static void main(String[] args) {
        final int ii=1;

        myNumber number=(n) ->{
            double i =ii+2;
           return i;
        };
        System.out.println(number.getValue(1));


        myOut <String>  out=()->{
            return"你好好";
        };
        System.out.println(out.t());
        myOut <Integer>  out1=()->{
            return 123;
        };
        System.out.println(out1.t());
        double d ;

        d=doubleOf(()->1);
        System.out.println(d);
        d = doubleOf(Test::getDouble);
        System.out.println(d);


    }
}
