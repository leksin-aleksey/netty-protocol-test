import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.stream.ChunkedNioStream;

import java.io.File;

public class MyHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
//        File file = new File("test_img.png");
        File file = new File(msg);
        String str = "hi there";
        channel.writeAndFlush(new ChunkedNioStream(new ReadableByteChannelImpl(file, str), 8192));
        ctx.flush();
        ctx.fireChannelReadComplete();
    }
}
