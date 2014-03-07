import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by julia
 */
public class Main {
    public static void main(String[] argv) throws Exception {

        for (;;) {

            ClassLoader loader= new DynamicClassOverloader(new String[] {"."});

            // текущий каталог "." будет единственным каталогом поиска

            Class clazz= Class.forName("TestModule",true,loader);

            Object object= clazz.newInstance();

            System.out.println(object);

            new BufferedReader(new InputStreamReader(System.in)).readLine();

        }

    }
}
