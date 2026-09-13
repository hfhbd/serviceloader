import io.github.hfhbd.serviceloader.ServiceLoader

interface Foo

@ServiceLoader(Foo::class)
class FooImpl : Foo
