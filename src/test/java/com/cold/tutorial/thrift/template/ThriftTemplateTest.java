package com.cold.tutorial.thrift.template;

import com.cold.tutorial.thrift.client.AdditionThriftTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * @author hui.liao
 *         2015/12/18 17:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext-thrift.xml")
public class ThriftTemplateTest {

    @Autowired
    private AdditionThriftTemplate thriftTemplate;

    @Test
    public void testExecute() throws Exception {
        thriftTemplate.add();
    }
}