package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar
interface Baz : Bar

@ServiceLoader(Bar::class)
class BarImpl : Baz

fun box() = "OK"
