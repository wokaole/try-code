import com.cold.tutorial.event.DataRegisterEvent;
import com.cold.tutorial.event.RegisterEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hui.liao on 2015/11/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-event.xml"})
public class RegisterEventTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void testTwo() {


        applicationContext.publishEvent(new RegisterEvent("RegisterEvent"));
        applicationContext.publishEvent(new RegisterEvent("RegisterEvent"));
        /*ExecutorService service = Executors.newFixedThreadPool(5);
            service.execute(new Runnable() {
                @Override
                public void run() {
                    applicationContext.publishEvent(new RegisterEvent("RegisterEvent"));
                    applicationContext.publishEvent(new RegisterEvent("RegisterEvent"));
                }
            });*/

        System.out.println("----------------tttt");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
