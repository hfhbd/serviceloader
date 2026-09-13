package foo.bar

import io.github.hfhbd.serviceloader.ServiceLoader

interface Bar

@Suppress("SERVICELOADER_ABSTRACT_CLASS", "SERVICELOADER_NO_PUBLIC_CONSTRUCTOR")
@ServiceLoader(Bar::class)
interface BarImpl : Bar

fun box() = "OK"
