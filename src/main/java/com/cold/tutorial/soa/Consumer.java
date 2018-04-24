package com.cold.tutorial.soa;

import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author hui.liao
 *         2016/1/18 16:38
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        String interfaceName = SayHelloService.class.getName();
        Method sayHello = SayHelloService.class.getMethod("sayHello", String.class);
        Object[] arg = {"hello"};

        Socket socket = new Socket("127.0.0.1", 1234);
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

        output.writeUTF(interfaceName);
        output.writeUTF(sayHello.getName());
        output.writeObject(sayHello.getParameterTypes());
        output.writeObject(arg);

    }
}
