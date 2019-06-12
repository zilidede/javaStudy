package zl.zlClass;

/*
 * @Description: junit测试
 * @Param:
 * @Author: zl
 * @Date: 2019/5/23 22:11
 */
public class zlJUnit {
    public int add(int n,int m){
        n=dec(n,m);
        return n+m;

    }
    public int dec(int n,int m){
        return n-m;
    }
    public static void main(String[] args) {

    }
}
