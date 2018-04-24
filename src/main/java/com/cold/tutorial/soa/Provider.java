package com.cold.tutorial.soa;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hui.liao
 *         2016/1/18 16:45
 */
public class Provider {

    private static Map<String, SayHelloServiceImpl> services = new HashMap<>();

    static {

        services.put("SayHelloService", new SayHelloServiceImpl());
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        while (true) {
            Socket socket = serverSocket.accept();

            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            String interfaceName = input.readUTF();
            String methodName = input.readUTF();
            Class<?> parameterTypes = (Class<?>) input.readObject();
            Object[] arg = (Object[]) input.readObject();

            Class<?> aClass = Class.forName(interfaceName);
            SayHelloServiceImpl sayHelloService = services.get(interfaceName);

            Method method = aClass.getMethod(methodName, parameterTypes);
            Object invoke = method.invoke(sayHelloService, arg);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(invoke);
        }
    }
}
