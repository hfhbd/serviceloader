// RUN_PIPELINE_TILL: FRONTEND

package foo.bar

import io.github.hfhbd.serviceloader.ServiceLoader

interface Bar

fun main() {
    <!SERVICELOADER_LOCAL_CLASS!>@ServiceLoader(Bar::class)
    class BarImpl : Bar<!>
}

/* GENERATED_FIR_TAGS: classDeclaration, classReference, functionDeclaration, interfaceDeclaration, localClass */
