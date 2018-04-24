package com.cold.tutorial.thrift.client;

import com.cold.tutorial.thrift.source.AdditionService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.*;

/**
 * Created by hui.liao on 2015/11/30.
 */
public class AdditionClient {
    public static void main(String[] args) {
        TTransport transport = null;
        try {
            //使用阻塞式 I/O 进行传输，是最常见的模式
            transport = new TSocket("localhost", 9090);
            //使用非阻塞方式，按块的大小进行传输，类似于 Java 中的 NIO，服务端需要设置成 TNonblockingServerTransport
            transport = new TFramedTransport(new TSocket("localhost", 9090));

            transport.open();

            //TBinaryProtocol 二进制编码格式进行传输
            TProtocol protocol = new TBinaryProtocol(transport);
            //TCompactProtocol 使用高效率的、密集的二进制编码格式进行数据传输
            //TProtocol protocol = new TCompactProtocol(transport);
            //TJSONProtocol —— 使用 JSON 的数据编码协议进行数据传输
            //TProtocol protocol = new TJSONProtocol(transport);

            AdditionService.Client client = new AdditionService.Client(protocol);

            System.out.println(client.add(100, 200));
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (transport.isOpen()) {
                transport.close();
            }
        }

    }
}
