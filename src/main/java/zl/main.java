package zl;

/*
 * @Description: 主类
 * @Param:
 * @Author: zl
 * @Date: 2019/4/24 11:36
 */
public class main {
    class A{
        A(){

        }
        public int i=0;
    }
    class B{
        B(){
            A a = new A();
        }
    }
    public static void main(String[] args) {
        System.out.println("hello,world");
    }
}
