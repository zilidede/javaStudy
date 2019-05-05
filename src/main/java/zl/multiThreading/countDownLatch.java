package zl.multiThreading;

import java.util.concurrent.CountDownLatch;

/*
 * @Description: CountDownLatchDemo
 * @Param:
 * @Author: zl
 * @Date: 2019/5/2 11:02
 */
public class countDownLatch {
    class Mythread implements Runnable {
        private CountDownLatch cdl=null;
        Mythread(CountDownLatch cd){
            cdl =cd;
            new Thread(this).start();
        }
        public  void run(){
            for(int i=0;i<5;i++){
                System.out.println(i);
                cdl.countDown();
            }
        }
    }
    public void testCountDownLatch(){
        CountDownLatch cd=new CountDownLatch(5);
        System.out.println("start");
        new Mythread(cd);
        try{
            cd.await();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("end");

    }
    public static void main(String[] args) {
        countDownLatch cdl = new countDownLatch();
        cdl.testCountDownLatch();
    }

}
