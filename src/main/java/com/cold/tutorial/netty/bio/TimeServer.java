package com.cold.tutorial.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步阻塞IO
 * @author hui.liao
 *         2016/2/16 14:27
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;

        if (args != null && args.length > 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {

            }
        }

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("the serverSocket start the server in port : " + port);

            Socket socket;
            while(true) {
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                System.out.println("close the server...");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
