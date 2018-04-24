package com.cold.tutorial.thrift.server;

import com.cold.tutorial.thrift.source.AdditionService;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by hui.liao on 2015/12/1.
 */
public class AdditionAsyncServer {

    public static void main(String[] args) throws TTransportException {
        TNonblockingServerTransport tServerTransport = new TNonblockingServerSocket(10005);
        AdditionService.Processor<AdditionServiceImpl> processor = new AdditionService.Processor<>(new AdditionServiceImpl());

        TServer server = new TNonblockingServer(new TNonblockingServer.Args(tServerTransport).processor(processor));
        System.out.println("start server...");
        server.serve();
    }
}
