package com.cold.tutorial.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author faker
 * @date 2017/7/18 16:55.
 */
public class FileChannel {

    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("D:/123.txt", "rw");
        java.nio.channels.FileChannel channel = accessFile.getChannel();

        Charset charset = Charset.forName("GBK");
        CharsetDecoder decoder  = charset.newDecoder();

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        CharBuffer charBuffer  = CharBuffer.allocate(36);

        int read = channel.read(byteBuffer);
        while (read != -1) {

            System.out.println(read);
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {

                decoder.decode(byteBuffer, charBuffer, false);
                charBuffer.flip();
                System.out.print(charBuffer);
                charBuffer.clear();
            }

            byteBuffer.clear();
            charBuffer.clear();

            read = channel.read(byteBuffer);
        }
        channel.close();
    }
}
