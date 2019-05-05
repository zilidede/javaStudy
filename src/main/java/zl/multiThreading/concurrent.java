package zl.multiThreading;

import java.util.concurrent.*;

/*
 * @Description: 并发学习类
 * @Param:
 * @Author: zl
 * @Date: 2019/5/1 16:11
 */
public class concurrent {
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
    class bardern implements  Runnable{
        bardern(){

        }
        public  void run(){
            System.out.println("bardern");
        }
    }
    class outThread implements Runnable{
        private String Threadname;
        private CyclicBarrier cb;
        outThread(CyclicBarrier cl,String name){
            Threadname =name;
            cb=cl;
            new Thread(this).start();
        }
        public  void run(){

            System.out.println(Threadname);
            try {
                cb.await();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            catch (BrokenBarrierException e){
                e.printStackTrace();
            }

        }
    }
    class useString implements Runnable{
        Exchanger<String > sr;
        String str;
        useString(Exchanger s){
            sr =s;
            new Thread(this).start();

        }
        public  void run(){
            for(int i=0;i<3;i++){
            try{
                str =sr.exchange(new String());
                System.out.println("GOT: " + str);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            }

        }
    }
    class makeString implements Runnable{
        Exchanger<String > sr;
        String str;
        makeString(Exchanger s){
            sr =s;
            str =new String();
            new Thread(this).start();
        }
        public  void run(){
            char ch='A';
            for (int i =0;i<3;i++){
                for (int j =0;j<5;j++){
                    str=str+ch++;
                }
                try{
                    str =sr.exchange(str);

                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }
    class phaserThread implements Runnable{
        private Phaser ph;
        private String Threadname;
        phaserThread(Phaser pr,String name){
            ph =pr;
            Threadname =name;
            ph.register();
            new Thread(this).start();
        }
        public  void run(){
            System.out.println("thread " + Threadname +" begining phase one" );
            ph.arriveAndAwaitAdvance();
            try {
                Thread.sleep(0);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("thread " + Threadname +" begining phase two" );
            ph.arriveAndAwaitAdvance();
            try {
                Thread.sleep(0);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }


            System.out.println("thread " + Threadname +" begining phase three" );
            ph.arriveAndDeregister();

        }
    }
    public void testCountDownLatch(){
        //countDownLathc类demo 希望线程等待直到发生一个或多个事件
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
    public void testCycliBarrier(){
        //CycliBarrier类demo 具备两个或两个以上的线程组必须在预定的执行点等待，直到线程组所有线程都达到线程组。
        CyclicBarrier cb =new CyclicBarrier(3,new bardern());
        outThread t1 =new outThread(cb,"a");
        outThread t2 =new outThread(cb,"b");
        outThread t3 =new outThread(cb,"c");
        outThread t4 =new outThread(cb,"d");
        outThread t5 =new outThread(cb,"e");
        outThread t6 =new outThread(cb,"f");

    }
    public void testExcanger(){
        //Excanger类demo 为简化线程间数据交换使用的类。
        Exchanger<String > sr = new Exchanger<String>();


        new makeString(sr);
        new useString(sr);


    }
    public void testPhaser(){
        //Phaser类demo 允许一个或多个活动线程同步的。
        Phaser ph =new Phaser(1);
        int curPhase;
        System.out.println("starting " );
        //
        new phaserThread(ph,"a");
        new phaserThread(ph,"b");
        new phaserThread(ph,"c");
        curPhase =ph.getPhase();
        ph.arriveAndAwaitAdvance();
        System.out.println("phase " + curPhase +" Complete" );

        curPhase =ph.getPhase();
        ph.arriveAndAwaitAdvance();
        System.out.println("phase " + curPhase +" Complete" );

        curPhase =ph.getPhase();
        ph.arriveAndAwaitAdvance();
        System.out.println("phase " + curPhase +" Complete" );

        ph.arriveAndDeregister();
        if (ph.isTerminated()){
            System.out.println(" the phase is terminated " );
        }

    }
    public void testPhaser2(){
        
    }
    public static void main(String[] args) {
        concurrent cdl = new concurrent();
        //cdl.testCountDownLatch();
        cdl.testPhaser();
    }
}
