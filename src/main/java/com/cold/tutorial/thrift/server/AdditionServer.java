package com.cold.tutorial.thrift.server;

import com.cold.tutorial.thrift.source.AdditionService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.*;

import java.net.InetSocketAddress;

/**
 * Created by hui.liao on 2015/11/30.
 */
public class AdditionServer {

    public static void startSimpleServer(AdditionService.Processor<AdditionServiceImpl> processor) {
        try {
            //堵塞传输 和客户端需要对应
            TServerTransport serverTransport = new TServerSocket(new InetSocketAddress("localhost", 9090));
            //非阻塞传输
            //TNonblockingServerTransport tserverTransport = new TNonblockingServerSocket(9090);

            //设置协议工程，默认为二进制,客户端的协议与服务端要对应
            TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();
            //高效率的、密集的二进制编码协议
            //TCompactProtocol.Factory factory = new TCompactProtocol.Factory();
            //使用 JSON 的数据编码协议进行数据传输
            //TJSONProtocol.Factory factory = new TJSONProtocol.Factory();

            // 单线程服务器端使用标准的阻塞式 I/O
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor).protocolFactory(factory));
            //多线程服务器端使用标准的阻塞式 I/O
            //TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            //非堵塞server
            //TNonblockingServer bserver = new TNonblockingServer(new TNonblockingServer.Args(tserverTransport).processor(processor).protocolFactory(factory));

            System.out.println("start the simple server...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startSimpleServer(new AdditionService.Processor<>(new AdditionServiceImpl()));
    }
}
