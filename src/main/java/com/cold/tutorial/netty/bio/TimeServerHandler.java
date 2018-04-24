package com.cold.tutorial.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @author hui.liao
 *         2016/2/16 14:38
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
             PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true)) {

            String currentTime;
            String line;
            while((line = in.readLine()) != null) {
                System.out.println("The time server receive order : " + line);
                currentTime = "QT".equalsIgnoreCase(line)? new Date().toString(): "BAD ORDER";
                out.println(currentTime);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
