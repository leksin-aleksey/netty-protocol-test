import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class ReadableByteChannelImpl implements ReadableByteChannel {
    private final File file;
    private final long fileLength;
    private final byte[] meta;
    private final int metaLength;
    private RandomAccessFile raf;
    private boolean metaRead = false;

    public ReadableByteChannelImpl(File file, String meta) {
        if (file == null || !file.exists() || !file.isFile()){
            throw new IllegalArgumentException();
        }
        this.file = file;
        fileLength = file.length();
        try {
            raf = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
        this.meta = meta.getBytes();
        metaLength = meta.length();
    }

    @Override
    public int read(ByteBuffer byteBuffer) throws IOException {
        if (!metaRead) {
            byteBuffer.putInt(metaLength);
            byteBuffer.put(meta);
            byteBuffer.putLong(fileLength);
            metaRead = true;
            return 4 + metaLength + 8;
        }
        byte b = raf.readByte();
        if (b != -1){
            byteBuffer.put(b);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isOpen() {
        return raf == null;
    }

    @Override
    public void close() throws IOException {
        raf.close();
    }
}
