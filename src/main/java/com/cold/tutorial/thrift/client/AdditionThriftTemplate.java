package com.cold.tutorial.thrift.client;

import com.cold.tutorial.thrift.source.AdditionService;
import com.cold.tutorial.thrift.template.ThriftTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author hui.liao
 *         2015/12/19 10:13
 */
@Component
public class AdditionThriftTemplate extends ThriftTemplate{

    @Value("${thrift1.host}")
    public String host;

    @Value("${thrift1.port}")
    public int port;

    public void add() {
        Optional<Integer> optional = execute((protocol) -> {
            AdditionService.Client service = new AdditionService.Client(protocol);
            int add = service.add(2, 3);
            return add;
        });

        System.out.println(optional.get());
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }
}
