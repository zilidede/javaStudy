package zl.zlClass;/*
 * @Description: 测试
 * @Param:
 * @Author: zl
 * @Date: 2019-03-21 9:07
 */


public class test {
    public static void testFun(count c){
        c.Inc();
    }
    public void testArr( int [] arrayName){
        int [] tArr=arrayName.clone();
        for(int i=0;i<arrayName.length/2;i++){
            tArr[i] =tArr[arrayName.length - i - 1];
        }
        for(int i=0;i<arrayName.length;i++){
           // System.out.println(arrayName[i]);
        }

    }
    public static void main(String[] args) {


        test t1 = new test();
        count ec= new count("one");
        ec.Inc();
        t1.testFun(ec);
        int [] arrayName ={1,2,3,4,5};
        t1.testArr(arrayName);
        for(int i=0;i<arrayName.length;i++){
            System.out.println(arrayName[i]);
        }

        System.out.println(ec.toString());
    }
}
