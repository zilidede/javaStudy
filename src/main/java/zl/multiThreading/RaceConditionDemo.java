package zl.multiThreading;

/*
 * @Description: 竞态Demo
 * @Param:
 * @Author: zl
 * @Date: 2019/5/31 17:01
 */
public class RaceConditionDemo {
    static class workThread extends Thread{
        private int RequestCount;
        workThread(int id,int requestCount){
            super("worker"+id);
            RequestCount =requestCount;
        }
        private void processRequest(String requestId){
            System.out.printf("%s got RequestId: %s %n ",Thread.currentThread().getName(),requestId);
        }
        @Override
       public void run(){
            int i =RequestCount;
            String sRequestID;
            RequestID requestID1 =RequestID.getInstance();
            while (i-->0){
                sRequestID = requestID1.nextId();
                processRequest(sRequestID);
            }
        }
    }
    public static void main(String[] args) {
        int numThread =4;
        Thread [] works = new  Thread[numThread];
        for(int i =0;i<numThread;i++){
            works[i]  = new workThread(i,10);
        }
        for(Thread t:works){
            t.start();
        }
    }
}
