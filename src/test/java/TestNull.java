import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by hui.liao on 2015/9/9.
 */
public class TestNull {

    static int age;

    public static void main(String[] args) {

//        System.out.println(age);
//        System.out.println(NumberUtils.isNumber("-1"));
//
//        List<Object> list = Collections.emptyList();
//        for (Object o : list) {
//            System.out.println(o);
//        }

        String id = "1111";
        String userId = "123";
        String matchName = "fff";
        String rank = "3";
        String defeatRate = "3";
        String content = "我在比赛 <a class='h5' href='/v1/home/match_" + id + "/other/" + userId + "'>$"
                + matchName + "$</a>中斩获第" + rank +"名，战胜了" + defeatRate + "的参赛用户哦";

        System.out.println(content);
    }
}
