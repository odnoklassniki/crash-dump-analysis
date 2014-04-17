package demo4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;

import static java.nio.channels.FileChannel.MapMode.*;

public class MappedFile {

    private static void saveBlock(byte[] data, String fileName) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        try {
            raf.setLength(data.length);
            MappedByteBuffer buffer = raf.getChannel().map(READ_WRITE, 0, data.length);
            buffer.put(data);
            buffer.force();
        } finally {
            raf.close();
        }
    }

    public static void main(String[] args) throws IOException {
        byte[] data = new byte[256 * 1024 * 1024];

        for (int i = 0; i < 10; i++) {
            saveBlock(data, "/dev/shm/cache" + i + ".mem");
        }
    }
}
