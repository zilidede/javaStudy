package zl.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import zl.zlClass.zlFileIo;

/*
 * @Description: socket服务端
 * @Param:
 * @Author: zl
 * @Date: 2019/4/30 15:54
 */
public class socketServer {
    public static void acceptMsg() {
        try {
            ServerSocket svSk = new ServerSocket(2628);
            while (true) {
                try (Socket sk = svSk.accept()) {
                    byte[] bArr = new byte[sk.getReceiveBufferSize()];
                    InputStream in = sk.getInputStream();
                    in.read(bArr);
                    String s = new String(bArr, "utf-8");
                    System.out.println(s);
                    String clintID = s.substring(s.indexOf("客户端id"), s.indexOf(","));
                    System.out.println(clintID);
                    String recievewMsg = "已经收到客户端消息，请发送下一条信息";
                    Writer recieveStream = new OutputStreamWriter(sk.getOutputStream(), "utf-8");
                    recieveStream.write(recievewMsg);
                    recieveStream.flush();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            return;
        }
    }

    public static void acceptBinaryMsg() {
        try (ServerSocket svSk = new ServerSocket(2628);) {
            svSk.setReceiveBufferSize(1024 * 1024 * 50);
            int i = 0;
            while (true) {
                try (Socket sk = svSk.accept()) {
                    InputStream in = sk.getInputStream();
                    int size = in.available();
                    byte[] bArr = new byte[sk.getReceiveBufferSize()];
                    //Thread.sleep(1000);
                    in.read(bArr);
                    String filePath = String.format("d:/test/%d.bmp", i++);
                    zlFileIo.byteArrToFile(bArr, filePath);
                    String s = new String(bArr, "utf-8");
                    //System.out.println(s);
                    String recievewMsg = "已经收到客户端消息，请发送下一条信息";
                    Writer recieveStream = new OutputStreamWriter(sk.getOutputStream(), "utf-8");
                    recieveStream.write(recievewMsg);
                    recieveStream.flush();
                    sk.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
                // catch (InterruptedException e){
                // System.out.println(e);
                // }
            }
        } catch (IOException e) {
            System.out.println(e);
            return;
        }
    }

    public void acceptMsgMultiprocess() {
        //多线程服务端
        try {
            ServerSocket svSk = new ServerSocket(2628);
            while (true) {
                try {
                    Socket sk = svSk.accept();
                    sendMsgThread sMsg = new sendMsgThread(sk);
                    sMsg.start();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

    }

    class sendMsgThread implements Runnable {
        private Socket connecter;

        sendMsgThread(Socket connect) {
            connecter = connect;
        }

        public void start() {
            new Thread(this).start();
        }

        public void run() {

            try {

                InputStream in = connecter.getInputStream();
                while (in.available()==0){
                    Thread.sleep(100);
                }
                byte[] bArr = readInputStream(in);
                String s = new String(bArr, "utf-8");
                int len = s.length();
                System.out.println(s);
                String recievewMsg = "";
                if (len <= 0)
                    recievewMsg = "服务端未收到客户端信息,请重新发送";
                else {
                    String clintID = s.substring(s.indexOf("客户端id"), s.indexOf(","));
                    //System.out.println(clintID);
                    recievewMsg = "服务端已经收到" + clintID + "的消息，请发送下一条信息";
                }


               // String recievewMsg = "服务端未收到客户端信息,请重新发送";
                Writer recieveStream = new OutputStreamWriter(connecter.getOutputStream(), "utf-8");
                recieveStream.write(recievewMsg);
                recieveStream.flush();


            } catch (IOException e) {
                e.printStackTrace();

            }
            catch (InterruptedException e) {
                e.printStackTrace();

            }
            finally {
                try {
                    connecter.close();
                } catch (IOException e) {
                    System.out.println(e);
                }

            }


        }
    }

    public static byte[] readInputStream(InputStream in) {
        byte[] rArr = null;
        try {
            rArr = new byte[in.available()];
            in.read(rArr);
            return rArr;
        } catch (IOException e) {

            return rArr;
        }

    }

    class sendTask implements Callable <Void>{
        private Socket connection;
        sendTask(Socket connect){
            connection =connect;
        }
        @Override
        public Void call() {
            try {
                InputStream in = connection.getInputStream();
                while (in.available()==0){
                    Thread.sleep(100);
                }
                byte[] bArr = readInputStream(in);
                String s = new String(bArr, "utf-8");
                int len = s.length();
                System.out.println(s);
                String recievewMsg = "";
                if (len <= 0)
                    recievewMsg = "服务端未收到客户端信息,请重新发送";
                else {
                    String clintID = s.substring(s.indexOf("客户端id"), s.indexOf(","));
                    //System.out.println(clintID);
                    recievewMsg = "服务端已经收到" + clintID + "的消息，请发送下一条信息";
                }


                // String recievewMsg = "服务端未收到客户端信息,请重新发送";
                Writer recieveStream = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
                recieveStream.write(recievewMsg);
                recieveStream.flush();


            } catch (IOException e) {
                e.printStackTrace();

            }
            catch (InterruptedException e) {
                e.printStackTrace();

            }
            finally {
                try {
                    connection.close();
                } catch (IOException e) {
                    System.out.println(e);
                }

            }
            return null;
        }
    }
    public void acceptMsgMultiprocessPool() {
        //多线程服务端
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try {
            ServerSocket svSk = new ServerSocket(2628);
            while (true) {
                try {
                    Socket sk = svSk.accept();
                    Callable<Void> task= new sendTask(sk);
                    pool.submit(task);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

    }
    public static void main(String[] args) {
        socketServer ss = new socketServer();

        ss.acceptMsgMultiprocessPool();
        // acceptMsg();
    }
}
