package zl.net;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.net.Inet4Address;
import java.net.InetAddress;

/*
 * @Description: 网络接口
 * @Param:
 * @Author: zl
 * @Date: 2019/4/27 18:13
 */
public class IntrefaceLister {
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();


        while (nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();

            // 获得与该网络接口绑定的 IP 地址，一般只有一个
            Enumeration<InetAddress> addresses = nif.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();

                if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
                    System.out.println("网卡接口名字：" + nif.getName());
                    System.out.println("网卡接口地址：" + addr.getHostAddress());
                    System.out.println("网卡接口名称：" + nif.getDisplayName());
                    System.out.println("网卡接口名称：" + addr.getHostAddress());
                    System.out.println();
                }
            }
        }
    }
    catch(SocketException e){
        e.printStackTrace();


    }}
}
