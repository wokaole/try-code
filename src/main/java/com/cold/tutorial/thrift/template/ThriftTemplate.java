package com.cold.tutorial.thrift.template;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author hui.liao
 *         2015/12/18 16:28
 */
@Component
public class ThriftTemplate {

    private static final Logger log = LoggerFactory.getLogger(ThriftTemplate.class);

    @Value("${thrift.host}")
    public String host;

    @Value("${thrift.port}")
    public int port;

    public <T> Optional<T> execute(ThriftAction<T> thriftAction) {
        TTransport transport = null;

        try {
            transport = new TSocket(getHost(), getPort());
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            T t = thriftAction.action(protocol);
            return Optional.ofNullable(t);

        } catch (TTransportException e) {
            log.error(e.getMessage(), e);
        } catch (TException e) {
            log.error(e.getMessage(), e);
        }  finally {
            if (transport != null && transport.isOpen()) {
                transport.close();
            }
        }

        return Optional.empty();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @FunctionalInterface
    public interface ThriftAction<T> {
         T action(TProtocol protocol) throws TException;
    }
}
