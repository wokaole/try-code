package com.cold.tutorial.thrift.server;

import com.cold.tutorial.thrift.source.AdditionService;
import org.apache.thrift.TException;

/**
 * Created by hui.liao on 2015/11/30.
 */
public class AdditionServiceImpl implements AdditionService.Iface{
    @Override
    public int add(int n1, int n2) throws TException {
        return n1 + n2;
    }
}
