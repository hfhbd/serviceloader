// RUN_PIPELINE_TILL: FRONTEND

package foo.bar

import io.github.hfhbd.serviceloader.ServiceLoader

interface Foo

@ServiceLoader(Foo::class)
class Bar : Foo by <!UNRESOLVED_REFERENCE!>Baz<!>()

/* GENERATED_FIR_TAGS: classDeclaration, classReference, inheritanceDelegation, interfaceDeclaration */
