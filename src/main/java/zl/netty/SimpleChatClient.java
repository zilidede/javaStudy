package zl.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * @Description: 简易网络聊天-客户端
 * @Param:
 * @Author: zl
 * @Date: 2019/5/13 18:52
 */
public class SimpleChatClient {
    class SimpleChatClientHandle extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println(msg);
        }
    }
    class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel> {
        protected  void initChannel(SocketChannel ch) throws Exception{
            ChannelPipeline pipeline =ch.pipeline();
            ch.pipeline().addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
            ch.pipeline().addLast("decoder",new StringDecoder());
            ch.pipeline().addLast("encode",new StringEncoder());
            ch.pipeline().addLast("handle",new SimpleChatClientHandle());
        }
    }
    public void run() {
        String host ="192.168.9.13";
        int port =2628;
        EventLoopGroup  group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group);
        b.channel(NioSocketChannel.class);

        b.handler(new SimpleChatClientInitializer());
        try {
            Channel channel = b.connect(host,port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
            //System.out.println(" started and listen on " + f.channel().localAddress());

        }
        catch (InterruptedException e){
            e.printStackTrace();

        }
        catch (IOException e){
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
        SimpleChatClient client = new SimpleChatClient();
        client.run();
    }
}
