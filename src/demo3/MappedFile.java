package demo3;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedFile {

    private static MappedByteBuffer createMapping(String fileName, long size) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
        try {
            raf.setLength(size);
            return raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, size);
        } finally {
            raf.close();
        }
    }

    private static void saveBlock(byte[] data, String fileName) throws IOException {
        MappedByteBuffer buffer = createMapping(fileName, data.length);
        buffer.put(data);
        buffer.force();
    }

    public static void main(String[] args) throws IOException {
        byte[] data = new byte[256 * 1024 * 1024];

        for (int i = 0; i < 10; i++) {
            saveBlock(data, "/dev/shm/cache" + i + ".mem");
        }
    }
}
