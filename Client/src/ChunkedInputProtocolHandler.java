import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedInput;

public class ChunkedInputProtocolHandler implements ChunkedInput<ByteBuf> {
    @Override
    public boolean isEndOfInput() throws Exception {
        return false;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public ByteBuf readChunk(ChannelHandlerContext ctx) throws Exception {
        return null;
    }

    @Override
    public ByteBuf readChunk(ByteBufAllocator allocator) throws Exception {
        return null;
    }

    @Override
    public long length() {
        return 0;
    }

    @Override
    public long progress() {
        return 0;
    }
}
