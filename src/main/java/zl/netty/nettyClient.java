package zl.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Date;
import java.util.List;
import zl.globClass.UnixTime;

/*
 * @Description: netty客户端
 * @Param:
 * @Author: zl
 * @Date: 2019/5/10 21:09
 */
public class nettyClient {

    class TimeDecode extends ByteToMessageDecoder{
        @Override
        protected  void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
            if(in.readableBytes()<4){
                return;
            }
            out.add(new UnixTime(in.readUnsignedInt()));
        }
    }
    class TimeClientHandler extends ChannelInboundHandlerAdapter {
        //时间客户端端
        private ByteBuf buf ;

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            // NOOP
            buf =ctx.alloc().buffer(4);
        }
        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            // NOOP
            buf.release();
            buf =null;
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            UnixTime m = (UnixTime) msg;
            System.out.print(msg);
            try{
                ctx.close();
            }
            finally {

            }

            /* ByteBuf m =(ByteBuf) msg;
            buf.writeBytes(m);
            m.release();
            try{
                if (buf.readableBytes()>=4) {
                    long currentTime = (buf.readUnsignedInt() - 2208988800L) * 1000l;
                    System.out.printf("当前时间："+new Date(currentTime));
                    ctx.close();
                }
            }
            finally {

            }
            */

        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
    public void runClient(){
        int port =2628;
        String host ="192.168.9.13";
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group);
        b.channel(NioSocketChannel.class);

        b.option(ChannelOption.SO_KEEPALIVE,true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            protected  void initChannel(SocketChannel ch) throws Exception{
                //time
                ch.pipeline().addLast(new TimeDecode(),new TimeClientHandler());
            }

        });
        try {
            ChannelFuture f =b.connect(host,port).sync();
            System.out.println(" started client " + f.channel().localAddress());
            f.channel().closeFuture().sync();

        }
        catch (InterruptedException e){
            e.printStackTrace();

        }
        finally {
            try {
                group.shutdownGracefully().sync();
            }
            catch (InterruptedException e){
                e.printStackTrace();

            }

        }
    }
    public static void main(String[] args) {


        int a = 10;  boolean b = true;
        a=b ? 40:20;
        System.out.println(a);
        System.out.println(b);
        nettyClient client =new nettyClient();
        client.runClient();
    }
}
