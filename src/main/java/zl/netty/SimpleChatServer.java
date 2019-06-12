package zl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;

/*
 * @Description: netty实现网络聊天功能-服务端
 * @Param:
 * @Author: zl
 * @Date: 2019/5/13 18:10
 */
public class SimpleChatServer {
    class SimpleChatServerHandle extends SimpleChannelInboundHandler<String> {
        public ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            // NOOP
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                channel.writeAndFlush("[SERVER] -" + incoming.remoteAddress() + " 加入\n");
            }
            channels.add(ctx.channel());
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            // NOOP
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                channel.writeAndFlush("[SERVER] -" + incoming.remoteAddress() + " 离开\n");
            }
            channels.add(ctx.channel());
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Channel incoming = ctx.channel();
            System.out.println("SimpleChatClient" + incoming.remoteAddress() + "在线");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            Channel incoming = ctx.channel();
            System.out.println("SimpleChatClient" + incoming.remoteAddress() + "离线");
        }


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                if(channel !=incoming)
                channel.writeAndFlush("[" + incoming.remoteAddress() +"]"+msg+ " \n");
                else {
                    channel.writeAndFlush("[you]"+ msg+"\n");
                }
            }
            channels.add(ctx.channel());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            Channel incoming = ctx.channel();
            System.out.println("SimpleChatClient" + incoming.remoteAddress() + "异常");
            cause.printStackTrace();
            ctx.close();
        }
    }
    class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel>{
        protected  void initChannel(SocketChannel ch) throws Exception{
            ChannelPipeline pipeline =ch.pipeline();
            ch.pipeline().addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            ch.pipeline().addLast("decoder",new StringDecoder());
            ch.pipeline().addLast("encode",new StringEncoder());
            ch.pipeline().addLast("handle",new SimpleChatServerHandle());
        }
    }
    public void run() {
        int port =2628;
        NioEventLoopGroup bossgroup = new NioEventLoopGroup();
        NioEventLoopGroup workgroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossgroup,workgroup);
        b.channel(NioServerSocketChannel.class);
        b.localAddress(port);
        b.option(ChannelOption.SO_BACKLOG,128);
        b.childOption(ChannelOption.SO_KEEPALIVE,true);
        b.childHandler(new SimpleChatServerInitializer());
        System.out.println("SimpleChatServer 启动了");
        try {
            ChannelFuture f =b.bind().sync();
            //System.out.println(" started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();
            bossgroup.shutdownGracefully().sync();
            workgroup.shutdownGracefully().sync();
            System.out.println("SimpleChatServer 关闭了");
        }
        catch (InterruptedException e){
            e.printStackTrace();

        }
        finally {

        }
    }

    public static void main(String[] args) {
        SimpleChatServer server = new SimpleChatServer();
        server.run();
    }
}
