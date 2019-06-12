package zl.zlClass;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;

/*
 * @Description: netty 缓冲区
 * @Param:
 * @Author: zl
 * @Date: 2019/5/18 23:11
 */
public class zlByteBuf {
    public  void test(){
        String  c ="中";
        Charset.forName("utf-8");


        ByteBuf buffer1 =Unpooled.buffer(16);

        Charset utf8 = Charset.forName("utf-8");
        ByteBuf buffer0 =Unpooled.copiedBuffer("the  is stupid ",utf8);
        System.out.println((char) buffer0.getByte(0));

        buffer0.setByte(0,65);
        buffer0.setChar(0,'b');
        System.out.println((char) buffer0.getChar(0));
        System.out.println(buffer0.getClass().getName());
    }
    public static void main(String[] args) {
        zlByteBuf buf =new zlByteBuf();
        buf.test();
    }
}
