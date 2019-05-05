package zl.zlClass;

import java.util.ArrayList;

/*
 * @Description: 多线程读写
 * @Param:
 * @Author: zl
 * @Date: 2019/4/10 11:43
 */
public class zlMultithreading {
    static ArrayList<Integer> objArray = new ArrayList<Integer>();
    static int threadCount = 1000;

    static class myThread extends Thread {
        private    int getTask() {

            return objArray.get(0);
            /*
           // for(int i=0;i<10000;i++)
               // zlDate.GetNow();
            threadCount =threadCount - 1;
            return threadCount;
            */

        }
        public synchronized void add(){
            objArray.add(2);
            try {
                sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notifyAll();
        }
        @Override
        public void run() {
            while (objArray.size() > 0) {
                synchronized(objArray) {
                    if (objArray.size() <= 0) {
                        return;
                    }
                    String s = String.format("当前线程名%s,计数为%d", currentThread().getName(), getTask());
                    System.out.println(s);
                    objArray.remove(0);
                    Thread.yield();
                    try {
                        sleep(0);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }

        }
    }
    static class myThread1 implements Runnable{
        @Override
        public  void run(){
            for(int i=0; i<10; i++)
            {
                String s = String.format("当前线程名%s,输出为%s", Thread.currentThread().getName(), "哈哈");
                System.out.println(s);
                try{
                    Thread.sleep(1000);}
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void test(){
        for (int i = 0; i < 10; i++) {
            objArray.add(i);
        }
        myThread t1 = new myThread();
        myThread t2 = new myThread();

        t1.start();
        t2.start();
    }
    public static void test1(){
        myThread1 t1= new myThread1();
        Thread thread = new Thread(t1);
        thread.start();
        for(int i=0; i<100; i++){
            System.out.println(Thread.currentThread().getName()+"嘿嘿嘿"+i);
            try{
            thread.sleep(10);}
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static  void test2(){

    }
    public static void main(String[] args) {

        test();
    }
}
