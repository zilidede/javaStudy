package zl.zlClass;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/*
 * @Description: 注解
 * @Param:
 * @Author: zl
 * @Date: 2019/5/23 23:27
 */
@Retention(RetentionPolicy.RUNTIME)
@interface myAnnotate{
    String str();
    int i();
}
@Retention(RetentionPolicy.RUNTIME)
@interface myAnnotate1{
    String str() default "000蛋";
    int i()  default 0;
}
public class zlAnnotate {
    @myAnnotate(str = "I am is a dog ",i=12)
    @myAnnotate1()
    public static void test(){

        zlAnnotate annotate = new zlAnnotate();

        Class<?> c=annotate.getClass();
        try {
            Method method= c.getMethod("test");
            Annotation[] anns= method.getAnnotations();
            for(Annotation a:anns){
                System.out.println(a);
            }
           // myAnnotate1 my=method.getAnnotation(myAnnotate1.class);
          //  System.out.println(my.str());
        }
        catch (NoSuchMethodException e){
            e.printStackTrace();

        }
        catch (SecurityException e){
            e.printStackTrace();

        }
    }
    public static void main(String[] args) {
        test();

    }
}
