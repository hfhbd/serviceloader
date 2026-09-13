package foo.bar

import io.github.hfhbd.serviceloader.ServiceLoader

interface Bar {
    interface A

    @ServiceLoader(A::class)
    class BarImpl : A
}

fun box() = "OK"
