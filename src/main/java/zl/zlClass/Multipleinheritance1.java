package zl.zlClass;

/*
 * @Description: 多重继承子类
 * @Param:
 * @Author: zl
 * @Date: 2019-03-21 11:52
 */
public class Multipleinheritance1 extends multipleInheritance {
    public void swim(){
        System.out.println("swim!!!");
    }
    public static void main(String[] args) {
        Multipleinheritance1 m1 = new Multipleinheritance1();
        m1.fly();
    }
}
