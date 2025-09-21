import app.softwork.serviceloader.ServiceLoader

interface Foo

@ServiceLoader(Foo::class)
class FooImpl : Foo
