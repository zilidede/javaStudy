package zl.zlClass;

import java.io.IOException;
import java.net.*;

/*
 * @Description: UDPSocket
 * @Param:
 * @Author: zl
 * @Date: 2019/5/24 14:45
 */
public class zlUDPClient {
    public static void updClient(){
        try{
            DatagramSocket udp =new DatagramSocket(0);
            udp.setSoTimeout(10000);
            String hostName="time.nist.gov";
            InetAddress host =  InetAddress.getByName(hostName);
            DatagramPacket request = new DatagramPacket(new byte[1],1);
            request.setAddress(host);
            request.setPort(13);
            byte [] data = new byte[1024];
            DatagramPacket response = new DatagramPacket(data,data.length);
            udp.send(request);
            udp.receive(response);
            String s = new String(response.getData(),0,response.getLength(),"utf-8");
            System.out.println(s);
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
        updClient();
    }
}
