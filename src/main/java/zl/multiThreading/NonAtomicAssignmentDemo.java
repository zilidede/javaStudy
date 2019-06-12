package zl.multiThreading;

/*
 * @Description: long/double 非原子操作
 * @Param:
 * @Author: zl
 * @Date: 2019/5/31 21:47
 */
public class NonAtomicAssignmentDemo implements Runnable {
    static   long value = 0;
    private final long valueToSet;
    NonAtomicAssignmentDemo(long value){
        valueToSet =value;
    }
    @Override
    public void run(){
        for(;;){
           value=valueToSet;
        }
    }
    public static void main(String[] args) {
        Thread updateThread1 = new Thread(new NonAtomicAssignmentDemo(0L));
        Thread updateThread2 = new Thread(new NonAtomicAssignmentDemo(-1L));
        updateThread1.start();
        updateThread2.start();
        long snapshot;
        while (0==(snapshot =value)||-1==snapshot){

        }
        ;
        System.err.printf("UnexPected data : %d(0x%016x)",snapshot,snapshot);
    }
}
