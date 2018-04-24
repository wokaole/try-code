package com.cold.tutorial.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author hui.liao
 *         2016/2/16 14:50
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;

        try(Socket socket = new Socket("127.0.0.1", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            TimeUnit.SECONDS.sleep(3);
            out.println("QT");
            System.out.println("receive result: " + in.readLine());
            out.println("any");
            System.out.println("receive result: " + in.readLine());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
