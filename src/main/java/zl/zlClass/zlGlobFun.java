package zl.zlClass;

/*
 * @Description: 全局函数
 * @Param:
 * @Author: zl
 * @Date: 2019/4/4 23:35
 */
import java.lang.reflect.Field;

import  sun.misc.Unsafe;
public class zlGlobFun {
    private static class ObjectA {
          String str;   // 4
          int i1;       // 4
         byte b1;      // 1
          byte b2;      // 1
         int i2;       // 4
         ObjectB obj;  //4
          byte b3;      // 1
     }
    private static class ObjectB {

    }
    public static int GetObjectByte(Object obj){
        try {
            getUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            getUnsafe.setAccessible(true);
            unsafe = (Unsafe) getUnsafe.get(null);
            Field[] fields = ObjectA.class.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.getName() + "---offSet:" + unsafe.objectFieldOffset(field));
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  -1;
    }
    private static Unsafe unsafe = null;
    private static Field getUnsafe = null;
    //测试函数
    public static  void test1(){
        try {
            getUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            getUnsafe.setAccessible(true);
            unsafe = (Unsafe) getUnsafe.get(null);
            Field[] fields = ObjectA.class.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.getName() + "---offSet:" + unsafe.objectFieldOffset(field));
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        test1();
    }
}
