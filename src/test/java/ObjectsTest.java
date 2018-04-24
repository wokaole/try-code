import com.cold.tutorial.concurrent.entity.BankCard;
import com.google.common.base.Objects;
import org.junit.Test;

/**
 * Created by hui.liao on 2015/9/15.
 */
public class ObjectsTest {

    @Test
    public void testToString() {
        System.out.println(Objects.toStringHelper(BankCard.class).toString());
    }

    public static void main(String[] args) {

        Dog myDog = new Dog("Rover");
        foo(myDog);

        System.out.println(myDog.getName());
    }

    public static void foo(Dog someDog) {
        someDog.setName("Max");     // AAA
        someDog = new Dog("Fifi");  // BBB
        someDog.setName("Rowlf");   // CCC
    }

    private static class Dog {

        private String name;
        public Dog(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
