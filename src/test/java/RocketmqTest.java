import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.cold.tutorial.rocketmq.MQProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author liaowenhui
 * @date 2016/7/19 14:32.
 */
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config-test.xml","classpath:applicationContext-dataSource.xml"})
public class RocketmqTest {

    public static final Logger log = LoggerFactory.getLogger(RocketmqTest.class);
    @Autowired
    private MQProducer rocketMqProducer;

    @Test
    public void test() throws InterruptedException {
        IntStream.range(0, 10).forEach(i -> {
            try {
                Message msg = new Message("PushTopic", "push", ""+System.currentTimeMillis(), "Just for test.".getBytes());
                SendResult result = rocketMqProducer.send(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            }
        });

        TimeUnit.MINUTES.sleep(3);
    }
}
