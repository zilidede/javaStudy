package zl.zlClass;

/*
 * @Description: 可变类型参数
 * @Param:
 * @Author: zl
 * @Date: 2019/5/23 22:31
 */
public class zlVariable {
    public static void  test(int ... n){
        for(int i=0;i<n.length;i++){
            System.out.println(i);
        }
    }
    public static void main(String[] args) {
        test(1,2,3,4);
    }
}
