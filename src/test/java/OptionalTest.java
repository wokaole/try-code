import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by hui.liao on 2015/9/9.
 */
public class OptionalTest {

    public static void main(String[] args) {
        /*Optional<Integer> optional = Optional.of(null);
        System.out.println("optional isPresent:" + optional.isPresent());
        if (optional.isPresent()) {
            System.out.println("optional value : " + optional.get());
        }*/

        List<String> list = Lists.newArrayList("test1", null, "test3", "test4");
        for (String s : list) {
            System.out.println(Optional.fromNullable(s).orNull());
            System.out.println(Optional.fromNullable(s).or("null"));
        }
    }
}
