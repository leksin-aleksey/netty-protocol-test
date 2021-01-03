import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.stream.ChunkedNioStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8585;

    public static void main(String[] args) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new ChunkedWriteHandler());
//                            p.addLast(new MyHandler());
                        }
                    });

            ChannelFuture f = b.connect(HOST, PORT).sync();
            System.out.println("**Client connected");
            Channel channel = f.sync().channel();
            File file = new File("test_img.png");
            String str = "hi there";
            channel.write(new ChunkedNioStream(new ReadableByteChannelImpl(file, str), 8192));
            channel.flush();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
