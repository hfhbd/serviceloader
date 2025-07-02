package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

@Suppress("SERVICELOADER_ABSTRACT_CLASS", "SERVICELOADER_NO_PUBLIC_CONSTRUCTOR")
@ServiceLoader(Bar::class)
interface BarImpl : Bar

fun box() = "OK"
