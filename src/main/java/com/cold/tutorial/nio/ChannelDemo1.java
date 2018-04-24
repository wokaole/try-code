package com.cold.tutorial.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by faker on 2015/8/17.
 */
public class ChannelDemo1 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("d:/data/niodata.txt", "rw");
        FileChannel channel = accessFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(36);

        int byteRead = channel.read(buf);
        while (byteRead != -1) {

            System.out.println("read :" + byteRead);
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.println(buf.get());
            }

            buf.clear();
            byteRead = channel.read(buf);
        }
        accessFile.close();
    }
}
