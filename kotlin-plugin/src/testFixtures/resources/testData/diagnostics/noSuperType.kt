// RUN_PIPELINE_TILL: FRONTEND

package foo.bar

import io.github.hfhbd.serviceloader.ServiceLoader

interface Bar

<!SERVICELOADER_SUPERTYPE_OF_CLASS_DOES_NOT_MATCH!>@ServiceLoader(Bar::class)
class BarImpl<!>

/* GENERATED_FIR_TAGS: classDeclaration, classReference, interfaceDeclaration */
