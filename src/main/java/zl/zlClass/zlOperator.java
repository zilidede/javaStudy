package zl.zlClass;

/*
 * @Description: 运算符
 * @Param:
 * @Author: zl
 * @Date: 2019/4/14 19:09
 */
public class zlOperator {
    enum Apple{redApp,blue,green}

    public static  void test(Apple a){
        if (a==Apple.redApp)
            System.out.println("红苹果");


    }
    public static void main(String[] args) {
            Integer i=1;
            i=i<<3;
            i=i>>2;
            test(Apple.redApp);
            System.out.println(i);
    }
}
