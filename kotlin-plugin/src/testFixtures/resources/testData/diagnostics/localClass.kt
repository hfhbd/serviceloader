package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

fun main() {
    <!LOCAL_CLASS!>@ServiceLoader(Bar::class)
    class BarImpl : Bar<!>
}
