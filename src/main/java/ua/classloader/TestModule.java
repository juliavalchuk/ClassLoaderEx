package ua.classloader;

/**
 * Created by julia
 */
public class TestModule extends TrueStaticModule {
    @Override
    public String toString() {
        return "ua.classloader.TestModule, version 2!" + (counter++);
    }
}

