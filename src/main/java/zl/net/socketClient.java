package zl.net;

import edu.princeton.cs.algs4.StdRandom;
import zl.zlClass.zlFileIo;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/*
 * @Description: socket客户端
 * @Param:
 * @Author: zl
 * @Date: 2019/4/29 15:28
 */
public class socketClient {
    final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        for(int i=0;i<25;i++){
            new sendMsgThread("192.168.9.3",2628);
        }
    }
    static class AnalogUser extends Thread{
        //模拟用户姓名
        String workerName;
        String openId;
        String openType;
        String amount;
        CountDownLatch latch;

        public AnalogUser(String workerName, String openId, String openType, String amount,
                          CountDownLatch latch) {
            super();
            this.workerName = workerName;
            this.openId = openId;
            this.openType = openType;
            this.amount = amount;
            this.latch = latch;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                latch.await(); //一直阻塞当前线程，直到计时器的值为0
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            post();//发送post 请求


        }

        public void post(){
            int clientId = StdRandom.uniform(0,100);
            String result = "";
            System.out.println("模拟用户： "+workerName+" 开始发送模拟请求  at "+sdf.format(new Date()));
            try (Socket sk = new Socket("192.168.9.3", 2628)) {
                InetAddress inAdress = InetAddress.getLocalHost();
                NetworkInterface net = NetworkInterface.getByIndex(1);
                // System.out.println();
                sk.setSoTimeout(15000);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String writeMsg = String.format("%s 客户端id号%d ,地址%s对服务端说：你好！",df.format(new Date()),clientId,InetAddress.getLocalHost());
                OutputStream out = sk.getOutputStream();
                Writer writer = new OutputStreamWriter(out, "utf-8");
               // Thread.sleep(1000);
                if (sk.isClosed()) {
                    System.out.println("连接已经断开");
                    return;
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
            }
            //sendPost("http://localhost:8080/Settlement/wallet/walleroptimisticlock.action", "openId="+openId+"&openType="+openType+"&amount="+amount);
           // System.out.println("操作结果："+acceptMsg);
            System.out.println("模拟用户： "+workerName+" 模拟请求结束  at "+sdf.format(new Date()));

        }
    }
    public void highConcurrent(){

        //模拟10000人并发请求，用户钱包
        CountDownLatch latch=new CountDownLatch(1);
        //模拟10000个用户
        for(int i=0;i<1000;i++){
            AnalogUser analogUser = new AnalogUser("user"+i,"58899dcd-46b0-4b16-82df-bdfd0d953bfb"+i,"1","20.024",latch);
            analogUser.start();
        }
        //计数器減一  所有线程释放 并发访问。
        latch.countDown();
        System.out.println("所有模拟请求结束  at "+sdf.format(new Date()));
    }
    public void chargenClient(){
        //基于通道的chargen 客户端
        try{
            SocketAddress address = new InetSocketAddress("192.168.9.13",2628);
            SocketChannel client = SocketChannel.open(address);
            ByteBuffer buffer = ByteBuffer.allocate(74);
            WritableByteChannel out = Channels.newChannel(System.out);
            while (true){
                int  n = client.read(buffer);
                if (n>0) {
                    buffer.flip();
                    out.write(buffer);
                    buffer.clear();
                }
                else if(n== -1){
                    break;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void integerClient(){
        //integer 客户端
        try{
            SocketAddress address = new InetSocketAddress("192.168.9.13",2628);
            SocketChannel client = SocketChannel.open(address);
            ByteBuffer buffer = ByteBuffer.allocate(4);
            IntBuffer view =buffer.asIntBuffer();
            WritableByteChannel out = Channels.newChannel(System.out);
            for(int expected =0;;expected++){
                client.read(buffer);
                int  actual = view.get();
                buffer.clear();
                view.rewind();
                if (actual !=expected){
                    System.err.println("Expected "+ expected +" ;was" +actual);
                    break;
                }
                System.out.println(actual);
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void udpDatagramClient(){
        try {
            DatagramSocket socket = new DatagramSocket(0);
            socket.setSoTimeout(10000);
            InetAddress host = InetAddress.getByName("192.168.9.13");
            DatagramPacket request= new DatagramPacket(new byte[1],1,host,13);
            DatagramPacket reponse= new DatagramPacket(new byte[1024],1024);
            socket.send(request);
            socket.receive(reponse);
            String daytiem = new String (reponse.getData(),0,reponse.getLength(),"US-ASCII");
            System.out.println(daytiem);
        }
        catch (SocketException e){
            e.printStackTrace();

        }
        catch (UnknownHostException e){
            e.printStackTrace();

        }
        catch (IOException e){
            e.printStackTrace();

        }

    }
    public static void main(String[] args) {
        socketClient sc=new socketClient();
        sc.udpDatagramClient();

    }
}
