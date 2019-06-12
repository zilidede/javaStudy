package zl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import zl.globClass.UnixTime;

/*
 * @Description: netty服务端
 * @Param:
 * @Author: zl
 * @Date: 2019/5/10 21:08
 */
public class nettyServer {
    class EchoServerHandler extends ChannelInboundHandlerAdapter {
        //应答服务器
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
             ByteBuf in = (ByteBuf) msg;
             String content = in.toString(CharsetUtil.UTF_8);
             System.out.println(content);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
    class DiscardServerHandler extends ChannelInboundHandlerAdapter {
        //丢弃服务端
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //((ByteBuf) msg).release();

            ctx.writeAndFlush(msg); // 为啥会写两次。
            //  ReferenceCountUtil.release(msg);
        }
        @Override
        public void channelActive(final ChannelHandlerContext ctx) throws Exception {
            final ByteBuf time =ctx.alloc().buffer(4);
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
    class TimeEncode extends ChannelOutboundHandlerAdapter{
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            UnixTime m = (UnixTime) msg;
            ByteBuf encode =ctx.alloc().buffer(4);
            encode.writeInt((int)m.value());
            ctx.write(encode, promise);
        }
    }
    class TimeServerHandler extends ChannelInboundHandlerAdapter {
        //时间服务端

        @Override
        public void channelActive(final ChannelHandlerContext ctx) throws Exception {
            /*final ByteBuf time =ctx.alloc().buffer(4);
            time.writeInt((int)(System.currentTimeMillis()/1000L + 2208988800L));
            final ChannelFuture f=ctx.writeAndFlush(time);
            */
            ChannelFuture f =ctx.writeAndFlush(new UnixTime());
            f.addListener(ChannelFutureListener.CLOSE);
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
    public void runServer(){
        int port =2628;
        NioEventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(group);
        b.channel(NioServerSocketChannel.class);
        b.localAddress(port);
        b.option(ChannelOption.SO_BACKLOG,128);
        b.childOption(ChannelOption.SO_KEEPALIVE,true);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            protected  void initChannel(SocketChannel ch) throws Exception{
                //echo
                //ch.pipeline().addLast(new EchoServerHandler());
                //discard
                //ch.pipeline().addLast(new DiscardServerHandler());
                //time
                ch.pipeline().addLast(new TimeEncode(), new TimeServerHandler());

            }

        });
        try {
            ChannelFuture f =b.bind().sync();
            System.out.println(" started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
            group.shutdownGracefully().sync();
        }
        catch (InterruptedException e){
            e.printStackTrace();

        }
        finally {

        }



    }
    public static void main(String[] args) {
        nettyServer server = new nettyServer();
        server.runServer();
    }
}
