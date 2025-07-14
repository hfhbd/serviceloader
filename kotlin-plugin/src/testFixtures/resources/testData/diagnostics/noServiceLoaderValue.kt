// RUN_PIPELINE_TILL: FRONTEND

package foo.bar

import app.softwork.serviceloader.ServiceLoader

@ServiceLoader(<!ANNOTATION_ARGUMENT_MUST_BE_CONST!><!UNRESOLVED_REFERENCE!>Bar<!>::class<!>)
class BarImpl : <!UNRESOLVED_REFERENCE!>Bar<!>

/* GENERATED_FIR_TAGS: classDeclaration, classReference */
