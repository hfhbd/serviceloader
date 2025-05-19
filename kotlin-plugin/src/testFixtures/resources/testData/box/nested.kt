package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar {
    interface A

    @ServiceLoader(A::class)
    class BarImpl : A
}

fun box() = "OK"
