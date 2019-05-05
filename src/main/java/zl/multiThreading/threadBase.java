package zl.multiThreading;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @Description: 线程的创建，暂停与停止
 * @Param:
 * @Author: zl
 * @Date: 2019/4/24 12:31
 */
public class threadBase {
    static  class Counter{
        private int count = 0;
        public void increment(){
            count++;
        }
        private int getCount(){
            return count;
        }
    }
    //创建线程的两种方法 继承thread类和 implement runnalbe接口
    static class InheritThread extends Thread{
        private int maxValue = 10;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        private Counter counter = new Counter();
        @Override
        public void run() {
            String outMsg="";
            while (counter.getCount()<maxValue){
                counter.increment();
                outMsg =String.format("当前时间%s,当前线程id是%d，当前线程名%s,当前计数%d",df.format(new Date()),Thread.currentThread().getId(),Thread.currentThread().getName(),counter.getCount());
                System.out.println(outMsg);
                try{
                    sleep(1000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }

    static class RunnableThread implements Runnable{
        private int maxValue = 100;
        private Object lock = new Object();
        private boolean suspendFlag=false;
        private Counter counter = new Counter();
        private  Thread t=null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        public RunnableThread(){

        }
        private void onPause(){
            try{
            synchronized (lock){
                while (suspendFlag){
                    lock.wait();
                }
            }}
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        public  void run(){
            String outMsg="";
            while (counter.getCount()<maxValue){
                outMsg =String.format("当前时间%s,当前线程id是%d，当前线程名%s,当前计数%d",df.format(new Date()),Thread.currentThread().getId(),Thread.currentThread().getName(),counter.getCount());
               System.out.println(outMsg);
                counter.increment();
                //System.out.println("hello,world");

                try{
                    Thread.sleep(200);
                    onPause();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                    break;
                }


            }
        }
        public void  Start(String threadName){
            //开始
            if (t==null){
                t=new Thread(this,threadName);
                t.start();
            }
        }
        public void Suspeng(){
            //挂起
            suspendFlag =true;

        }
        public void Resume(){
            //恢复
            suspendFlag =false;
            synchronized (lock){
                lock.notify();
            }
        }


    }
    //测试函数
    public  static  void  testCreateThread(){
        //线程创建
        InheritThread mythread = new InheritThread();
        mythread.setName("子线程1");
        Thread mythread2 =new Thread(new RunnableThread());
        mythread2.setName("子线程2");
        mythread.start();
        //mythread.start();
        mythread2.start();

    }
    public static void testThreadShare(){
        //对象共享
        for(int i=0;i<10;i++){
            //InheritThread mythread = new InheritThread();
          //  mythread.setName("子线程"+String.valueOf(i));
          //  mythread.start();
        }
        RunnableThread  r  =new RunnableThread();
        for(int i=0;i<10;i++){

            Thread mythread2 =new Thread(r);
            mythread2.setName("共享线程"+String.valueOf(i));
            mythread2.start();
        }
    }
    public static void testPriority(){
        //线程优先级测试
        for(int i=0;i<5;i++){
            InheritThread mythread = new InheritThread();
            mythread.setName("子线程"+String.valueOf(i));
            mythread.setPriority(5+i);
            mythread.start();

        }
    }
    public static void testDaemon(){
        //守护线程
        Thread mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread daemonThread = new Thread(new RunnableThread()) ;
                daemonThread.setName("守护线程"+String.valueOf(1));
                daemonThread.setDaemon(true);
                daemonThread.start();
                System.out.println("主线程运行中。。。。。");
            }
        });
        mainThread.start();
    }
    public static void testPauseResume(){
        //线程暂停与恢复
        RunnableThread  r  =new RunnableThread();

        try {
            r.Start("子线程");

            Thread.sleep(5000);

            r.Suspeng();
            System.out.println("线程暂停5秒");
            Thread.sleep(5000);
            r.Resume();
            System.out.println("线程恢复");
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }




    }
    public static void main(String[] args) {
        testPauseResume();
    }
}
