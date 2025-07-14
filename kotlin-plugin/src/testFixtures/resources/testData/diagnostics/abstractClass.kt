// RUN_PIPELINE_TILL: FRONTEND

package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

<!SERVICELOADER_ABSTRACT_CLASS!>@ServiceLoader(Bar::class)
abstract class BarImpl : Bar<!>

/* GENERATED_FIR_TAGS: classDeclaration, classReference, interfaceDeclaration */
