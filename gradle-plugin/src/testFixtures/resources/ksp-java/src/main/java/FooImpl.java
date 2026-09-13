import io.github.hfhbd.serviceloader.ServiceLoader;

@ServiceLoader(forClass = Foo.class)
public class FooImpl implements Foo {
}
