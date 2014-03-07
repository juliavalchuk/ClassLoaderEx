package ua.classloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by julia
 */
public class Main {
    public static void main(String[] argv) throws Exception {

        for (;;) {

            ClassLoader loader= new DynamicClassOverloader(new String[] {"."});
            Class clazz= Class.forName("ua.classloader.TestModule",true,loader);
            TrueStaticModule trueStaticModule=(TrueStaticModule) clazz.newInstance();

            System.out.println(trueStaticModule.getCounter());
            System.out.println(trueStaticModule);

       //     Object object= clazz.newInstance();
       //     System.out.println(object);
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        }

    }
}
