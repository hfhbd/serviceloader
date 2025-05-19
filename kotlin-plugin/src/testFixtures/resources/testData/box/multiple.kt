package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

@ServiceLoader(Bar::class)
class BarImpl : Bar

interface Foo : Bar

@ServiceLoader(Bar::class)
class FooImpl : Foo

fun box() = "OK"
