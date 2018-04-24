package com.cold.tutorial.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步阻塞IO
 * @author hui.liao
 *         2016/2/16 15:34
 */
public class FakeNioTimeServer {

    public static void main(String[] args) {
        int port = 8080;

        if (args != null && args.length > 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {

            }
        }

        try(ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("the serverSocket start the server in port : " + port);
            TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50, 10000);

            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                pool.execute(new TimeServerHandler(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
