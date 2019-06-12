package zl.multiThreading;

/*
 * @Description: 线程可见性
 * @Param:
 * @Author: zl
 * @Date: 2019/6/1 13:03
 */
public class VisualThread {
    static int shareInt ;
    public static void main(String[] args) {
        shareInt =0;
        Thread thread =new Thread(){
            @Override
           public void run(){

                shareInt = 1;
            }
        };
        thread.start();

        try {
            thread.join();
        }
        catch (InterruptedException e){
           e.printStackTrace();
        }

        System.out.println(shareInt);
    }
}
