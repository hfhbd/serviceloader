package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

@ServiceLoader(Bar::class)
class BarImpl : Bar

fun box() = "OK"
