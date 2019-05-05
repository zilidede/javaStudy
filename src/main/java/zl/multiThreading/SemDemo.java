package zl.multiThreading;

import java.util.concurrent.Semaphore;

/*
 * @Description: 信号量
 * @Param:
 * @Author: zl
 * @Date: 2019/5/1 17:29
 */
public class SemDemo {
    public static int count = 0;
    class IncThread implements Runnable {
        private String n;
        private Semaphore s;

        IncThread(Semaphore sem, String name) {
            n = name;
            s = sem;
            new Thread(this).start();

        }

        public void run() {
            System.out.println("starting " + n);
            try {
                System.out.println(n + " is waiting for a permit.");
                s.acquire();
                System.out.println(n + " gets a permit.");
                for (int i = 0; i < 5; i++) {
                    count++;
                    System.out.println(n + ":" + count);
                    Thread.sleep(10);
                }


            } catch (InterruptedException e) {
                System.out.println(e);
            }
            s.release();
            System.out.println(n + " releases the perimit.");
        }

    }

    class DecThread implements Runnable {
        private String n;
        private Semaphore s;

        DecThread(Semaphore sem, String name) {
            n = name;
            s = sem;
            new Thread(this).start();
        }

        public void run() {
            System.out.println("starting " + n);
            try {
                System.out.println(n + " is waiting for a permit.");
                s.acquire();
                System.out.println(n + " gets a permit.");
                for (int i = 0; i < 5; i++) {
                    count--;
                    System.out.println(n + ":" + count);
                    Thread.sleep(10);
                }


            } catch (InterruptedException e) {
                System.out.println(e);
            }
            s.release();
            System.out.println(n + " releases the perimit.");
        }

    }
    public void   test(){
        Semaphore sem = new Semaphore(1);
         new IncThread(sem, "a");
         new DecThread(sem, "b");
    }
    public static void main(String[] args) {
        SemDemo sem = new SemDemo();
        sem.test();

    }

}
