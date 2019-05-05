package zl.net;

import edu.princeton.cs.algs4.StdRandom;
import zl.zlClass.zlFileIo;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @Description: socket客户端
 * @Param:
 * @Author: zl
 * @Date: 2019/4/29 15:28
 */
public class socketClient {
    public static void testSocket() {
        try (Socket sk = new Socket("dict.org", 2628)) {
            sk.setSoTimeout(15000);
            OutputStream out = sk.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "utf-8");
            writer.write("DEFINE eng-lat gold\r\n");
            writer.flush();
            InputStream in = sk.getInputStream();

            byte[] bArr = new byte[sk.getReceiveBufferSize()];
            in.read(bArr);
            String s = new String(bArr, "utf-8");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMsg() {
        int clientId = StdRandom.uniform(0,100);
        while (true) {
            Socket sk =null;
            try  {
                sk= new Socket("192.168.9.13", 2628);
                InetAddress inAdress = InetAddress.getLocalHost();
                NetworkInterface net = NetworkInterface.getByIndex(1);
                // System.out.println();
                sk.setSoTimeout(15000);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String writeMsg = String.format("%s 客户端id号%d ,地址%s对服务端说：你好！",df.format(new Date()),clientId,InetAddress.getLocalHost());
                OutputStream out = sk.getOutputStream();
                Writer writer = new OutputStreamWriter(out, "utf-8");

                if (sk.isClosed()) {
                    System.out.println("连接已经断开");
                    break;

                }
                writer.write(writeMsg);
                writer.flush();
                // byte[] bArr = new byte[sk.getReceiveBufferSize()];
                //  in.read(bArr);
                //  if (bArr.length > 1) {
                //    String s = new String(bArr, "utf-8");
                //    System.out.println(s);

                //}
                //等待回复
                Thread.sleep(1000);
                InputStream in = sk.getInputStream();
                byte[] acceptArr = new byte[sk.getReceiveBufferSize()];
                in.read(acceptArr);
                String acceptMsg = new String(acceptArr, "utf-8");
                System.out.println(acceptMsg);


            } catch (IOException e) {
                e.printStackTrace();
            }
            catch  (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    sk.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
            //break;
        }
    }

    public static void sengBinaryData() {
        //send img
        int i =0;
        while (true) {
            //大文件传输。异常 sk.getReceiveBufferSize() 等于64K
            if (i++>100)
                break;

            try (Socket sk = new Socket("192.168.9.13", 2628)) {
                FileInputStream f = new FileInputStream("d:/1.bmp");
                int size =f.available() ;
                sk.setSendBufferSize(1024*1024*50);
                OutputStream out = sk.getOutputStream();
                InputStream in = sk.getInputStream();
                byte[] imgData = new byte[size];
                sk.setSoTimeout(15000);
                f.read(imgData);
                zlFileIo.byteArrToFile(imgData,"d:/3.bmp");
                //传输img
                out.write(imgData);
                out.flush();
                byte[] bArr = new byte[sk.getReceiveBufferSize()];
                in.read(bArr);
                String s = new String(bArr, "utf-8");
                System.out.println(s);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
               // e.printStackTrace();
            }
            break;


        }

    }

    public static void sengMsgError() {
        Socket sk = null;
        try {
            sk = new Socket("192.168.9.5", 2628);
            InetAddress inAdress = InetAddress.getLocalHost();
            NetworkInterface net = NetworkInterface.getByIndex(1);
            // System.out.println();
            sk.setSoTimeout(15000);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String writeMsg = df.format(new Date()) + " 客户端" + inAdress.getHostName() + "对服务端说：你好！";
            InputStream in = sk.getInputStream();
            int i = 0;
            Writer writer = null;
            while (i < 10) {
                Thread.sleep(1000);
                OutputStream out = sk.getOutputStream();
                writer = new OutputStreamWriter(out, "utf-8");
                if (sk.isClosed()) {
                    System.out.println("连接已经断开");
                }
                writer.append(writeMsg);
                writer.flush();
                i++;

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (sk != null) {
                try {
                    sk.close();
                } catch (IOException e) {
                    System.out.println(e);
                }


            }

        }
    }
    class  sendMsgThread implements Runnable{
        private String hostname;
        private int hostPort;
        sendMsgThread(String host,int port){
            hostname =host;
            hostPort =port;
            new Thread(this).start();
        }
        public  void run(){
            int clientId = StdRandom.uniform(0,100);
            while (true) {
                try (Socket sk = new Socket(hostname, hostPort)) {
                    InetAddress inAdress = InetAddress.getLocalHost();
                    NetworkInterface net = NetworkInterface.getByIndex(1);
                    // System.out.println();
                    sk.setSoTimeout(15000);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    String writeMsg = String.format("%s 客户端id号%d ,地址%s对服务端说：你好！",df.format(new Date()),clientId,InetAddress.getLocalHost());
                    OutputStream out = sk.getOutputStream();
                    Writer writer = new OutputStreamWriter(out, "utf-8");
                    Thread.sleep(1000);
                    if (sk.isClosed()) {
                        System.out.println("连接已经断开");
                        break;

                    }
                    writer.write(writeMsg);
                    writer.flush();
                    // byte[] bArr = new byte[sk.getReceiveBufferSize()];
                    //  in.read(bArr);
                    //  if (bArr.length > 1) {
                    //    String s = new String(bArr, "utf-8");
                    //    System.out.println(s);

                    //}
                    InputStream in = sk.getInputStream();
                    byte[] acceptArr = new byte[sk.getReceiveBufferSize()];
                    in.read(acceptArr);
                    String acceptMsg = new String(acceptArr, "utf-8");
                    System.out.println(acceptMsg);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public  void multiprocessSendMsg(){
        for(int i=0;i<5;i++){
            new sendMsgThread("192.168.9.13",2628);
        }
    }
    public static void main(String[] args) {
        socketClient sc=new socketClient();
        sc.multiprocessSendMsg();
        //sendMsg();

    }
}
