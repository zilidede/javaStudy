package zl.zlClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Date;

/*
 * @Description: IntelAddress类封装了IP地
址极其对应域名
 * @Param:
 * @Author: zl
 * @Date: 2019/4/18 10:28
 */
public class zlInetAddress {
    public static void testIntelAddress(){
       try{
           InetAddress[] intAddressArr = InetAddress.getAllByName("www.baidu.com");
           for(int i =0;i<intAddressArr.length;i++){
           System.out.println(intAddressArr[i]);
           }
        }
        catch (UnknownHostException e){
           e.printStackTrace();

        }
    }
    public static void testSocket(){
        try{
            String hostname="whois.internet.net";
            Integer port=43;
            Socket sk=new Socket(hostname,port);
            InputStream in=sk.getInputStream();
            OutputStream ou =sk.getOutputStream();
            String s="MHProfessional.com:"+"\n";
            byte [] byArr= s.getBytes();
            ou.write(byArr);
            int c=0;
            while ( (c=in.read())!=-1){
                System.out.print((char)c);
            }
            sk.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void testUrl(){
        try{
            URL ul= new URL("http://HerbSchildt.com/WhatsNew");
            HttpURLConnection ulConn= (HttpURLConnection)ul.openConnection();
            System.out.println("protocol:"+ul.getProtocol());
            System.out.println("host:"+ul.getHost());
            System.out.println("port:"+ul.getPort());
            System.out.println("file:"+ul.getFile());
            System.out.println("date:"+new Date(ulConn.getDate()));
            System.out.println("last modidate:"+new Date(ulConn.getLastModified()));
            System.out.println("type:"+ulConn.getContentType());
            System.out.println("lenght:"+ulConn.getContentLength());
            System.out.println("response code:"+ulConn.getResponseCode());
            System.out.println("response msg:"+ulConn.getResponseMessage());
            System.out.println("request method:"+ulConn.getRequestMethod());
            InputStream in=ulConn.getInputStream();
            int c=0;
            while ( (c=in.read())!=-1){
                System.out.print((char)c);
            }

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        }
        public static  void test(){
            //测试内容纷纷道
        }

    public static void main(String[] args) {
        testUrl();
    }
}
