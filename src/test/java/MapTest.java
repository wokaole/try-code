import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by hui.liao on 2015/11/24.
 */
public class MapTest {

    @Test
    public void testTreeMap() {
        TreeMap<String, String> treeMap = Maps.newTreeMap();
        treeMap.put("b", "1234");
        treeMap.put("a", "123");
        treeMap.put("ab", "1233");
        treeMap.put("1", "123311");

        Set<Map.Entry<String, String>> set = treeMap.entrySet();
        for (Map.Entry<String, String> map : set) {
            System.out.println(map.getKey() + ":" + map.getValue());
        }

        SortedMap<String, String> map = treeMap.tailMap("b");
        System.out.println(map);
        System.out.println(treeMap.firstKey());
        System.out.println(map.firstKey());
    }
}
