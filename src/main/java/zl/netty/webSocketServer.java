package zl.netty;

import com.waylau.netty.demo.websocketchat.HttpRequestHandler;
import com.waylau.netty.demo.websocketchat.TextWebSocketFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import javax.net.ssl.SSLSocket;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/*
 * @Description: WebSocket网络聊天室-服务端
 * @Param:
 * @Author: zl
 * @Date: 2019/5/13 23:52
 */
public class webSocketServer {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> { //1
        private final String wsUri;
        private final File INDEX;

         {
            URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
            try {
                String path = location.toURI() + "WebsocketChatClient.html";
                path = !path.contains("file:") ? path : path.substring(5);
                INDEX = new File(path);
            } catch (URISyntaxException e) {
                throw new IllegalStateException("Unable to locate WebsocketChatClient.html", e);
            }
        }

        public HttpRequestHandler(String wsUri) {
            this.wsUri = wsUri;
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
            if (wsUri.equalsIgnoreCase(request.getUri())) {
                ctx.fireChannelRead(request.retain());                  //2
            } else {
                if (HttpHeaders.is100ContinueExpected(request)) {
                    send100Continue(ctx);                               //3
                }

                RandomAccessFile file = new RandomAccessFile(INDEX, "r");//4

                HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

                boolean keepAlive = HttpHeaders.isKeepAlive(request);

                if (keepAlive) {                                        //5
                    response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
                    response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                }
                ctx.write(response);                    //6

                if (ctx.pipeline().get(SslHandler.class) == null) {     //7
                    ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
                } else {
                    ctx.write(new ChunkedNioFile(file.getChannel()));
                }
                ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);           //8
                if (!keepAlive) {
                    future.addListener(ChannelFutureListener.CLOSE);        //9
                }

                file.close();
            }
        }

        private void send100Continue(ChannelHandlerContext ctx) {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
            ctx.writeAndFlush(response);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            Channel incoming = ctx.channel();
            System.out.println("Client:" + incoming.remoteAddress() + "异常");
            // 当出现异常就关闭连接
            cause.printStackTrace();
            ctx.close();
        }
    }

    class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {





        @Override
        protected void channelRead0(ChannelHandlerContext ctx,
                                    TextWebSocketFrame msg) throws Exception { // (1)
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                if (channel != incoming){
                    channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
                } else {
                    channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text() ));
                }
            }
        }

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
            Channel incoming = ctx.channel();

            // Broadcast a message to multiple Channels
            channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 加入"));

            channels.add(incoming);
            System.out.println("Client:"+incoming.remoteAddress() +"加入");
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
            Channel incoming = ctx.channel();

            // Broadcast a message to multiple Channels
            channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 离开"));

            System.out.println("Client:"+incoming.remoteAddress() +"离开");

            // A closed Channel is automatically removed from ChannelGroup,
            // so there is no need to do "channels.remove(ctx.channel());"
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
            Channel incoming = ctx.channel();
            System.out.println("Client:"+incoming.remoteAddress()+"在线");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
            Channel incoming = ctx.channel();
            System.out.println("Client:"+incoming.remoteAddress()+"掉线");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	// (7)
                throws Exception {
            Channel incoming = ctx.channel();
            System.out.println("Client:"+incoming.remoteAddress()+"异常");
            // 当出现异常就关闭连接
            cause.printStackTrace();
            ctx.close();
        }
    }


    class webChatServerInitializer extends ChannelInitializer<SocketChannel> {
        private  ChannelGroup group;
        public  void  ChatServerInitializer(ChannelGroup group) {
            this.group = group;
        }
        @Override
        public void initChannel(SocketChannel ch) throws Exception {//2
            ChannelPipeline pipeline = ch.pipeline();

            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new HttpObjectAggregator(64*1024));
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new HttpRequestHandler("/ws"));
            pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
            pipeline.addLast(new TextWebSocketFrameHandler());

        }
    }

    public void run() {
        int port = 8080;
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new webChatServerInitializer())  //(4)
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            System.out.println("WebsocketChatServer 启动了");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync(); // (7)

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();

        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            System.out.println("WebsocketChatServer 关闭了");
        }
    }

    public static void main(String[] args) {
        webSocketServer socketServer = new webSocketServer();
        socketServer.run();
    }
}
