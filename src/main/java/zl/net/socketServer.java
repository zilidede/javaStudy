package zl.net;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
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
                while (in.available() == 0) {
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

            } catch (InterruptedException e) {
                e.printStackTrace();

            } finally {
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

    class sendTask implements Callable<Void> {
        private Socket connection;

        sendTask(Socket connect) {
            connection = connect;
        }

        @Override
        public Void call() {
            try {
                InputStream in = connection.getInputStream();
                while (in.available() == 0) {
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

            } catch (InterruptedException e) {
                e.printStackTrace();

            } finally {
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
        System.out.println("service start");
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try {
            ServerSocket svSk = new ServerSocket(2628);
            while (true) {
                try {
                    Socket sk = svSk.accept();
                    Callable<Void> task = new sendTask(sk);
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

    public void chargenNio() {
        //chargen 服务端
        byte[] rotaion = new byte[95 * 2];
        for (byte i = ' '; i <= '~'; i++) {
            rotaion[i - ' '] = i;
            rotaion[i + 95 - ' '] = i;
        }
        Selector selector;
        ServerSocketChannel serverChannel;
        try {
            //服务端
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(2628));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);


        } catch (IOException e) {
            e.printStackTrace();
            return;

        }

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            //

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = server.accept();
                        clientChannel.configureBlocking(false);
                        SelectionKey key2 = clientChannel.register(selector, SelectionKey.OP_WRITE);
                        ByteBuffer buffer = ByteBuffer.allocate(74);
                        buffer.put(rotaion, 0, 72);
                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');
                        buffer.flip();
                        key2.attach(buffer);
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if (!buffer.hasRemaining()) {
                            buffer.rewind();
                            int first = buffer.get();
                            buffer.rewind();
                            int postion = first - ' ' + 1;
                            buffer.put(rotaion, postion, 72);
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');
                            buffer.flip();
                        }
                        client.write(buffer);

                    }
                } catch (IOException e) {
                    key.channel();
                    try {
                        key.channel().close();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }

                }
            }
        }

    }

    public void integerServer() {
        //intgen 服务端
        Selector selector;
        ServerSocketChannel serverChannel;
        System.out.println("service start");
        try {
            //服务端
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(2628));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;

        }
        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            //

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = server.accept();
                        System.out.println("Accepted connect form " + clientChannel);
                        clientChannel.configureBlocking(false);
                        SelectionKey key2 = clientChannel.register(selector, SelectionKey.OP_WRITE);
                        ByteBuffer buffer = ByteBuffer.allocate(74);
                        buffer.putInt(0);
                        buffer.flip();
                        key2.attach(buffer);
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if (!buffer.hasRemaining()) {
                            buffer.rewind();
                            int value = buffer.getInt();
                            buffer.clear();
                            buffer.putInt(value + 1);
                            buffer.flip();
                        }
                        client.write(buffer);

                    }
                } catch (IOException e) {
                    key.channel();
                    try {
                        key.channel().close();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }

                }
            }
        }
    }
    public void datagramServer(){
        System.out.println("service start");
        try(DatagramSocket server= new DatagramSocket(13) ) {
           while (true){
               try {
                   DatagramPacket request= new DatagramPacket(new byte[1024],1024);
                   server.receive(request);
                   String  daytime=new Date().toString();
                   byte [] data =daytime.getBytes("US-ASCII");
                   DatagramPacket reponse= new DatagramPacket(data,data.length,request.getAddress(),request.getPort());
                   server.send(reponse);

               }
               catch (IOException e){
                  e.printStackTrace();
               }
           }
        }catch (SocketException e){
          e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        socketServer ss = new socketServer();

        ss.acceptMsgMultiprocessPool();
        // acceptMsg();
    }
}
