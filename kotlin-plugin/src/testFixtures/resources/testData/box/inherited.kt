package foo.bar

import io.github.hfhbd.serviceloader.ServiceLoader

interface Bar
interface Baz : Bar

@ServiceLoader(Bar::class)
class BarImpl : Baz

fun box() = "OK"
