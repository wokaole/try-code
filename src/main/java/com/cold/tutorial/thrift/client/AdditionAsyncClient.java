package com.cold.tutorial.thrift.client;


import com.cold.tutorial.thrift.MethodCallback;
import com.cold.tutorial.thrift.source.AdditionService;
import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.io.IOException;

/**
 * Created by hui.liao on 2015/12/1.
 */
public class AdditionAsyncClient {

    public static void main(String[] args) throws IOException, TException {
        TAsyncClientManager clientManager = new TAsyncClientManager();
        TNonblockingTransport transport = new TNonblockingSocket("localhost", 10005);
        TProtocolFactory protocol = new TBinaryProtocol.Factory();

        AdditionService.AsyncClient asyncClient = new AdditionService.AsyncClient(protocol, clientManager, transport);

        MethodCallback<AdditionService.AsyncClient.add_call> callback = new MethodCallback<>();
        asyncClient.add(20,30, callback);
        AdditionService.AsyncClient.add_call result = callback.getResult();

        System.out.println("result  " + result);
        while (result == null) {
            result = callback.getResult();
        }

        System.out.println(result.getResult());
    }
}
