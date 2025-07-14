// RUN_PIPELINE_TILL: FRONTEND

package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

@ServiceLoader(Bar::class)
<!SERVICELOADER_NO_PUBLIC_CONSTRUCTOR!>object BarImpl<!> : Bar

/* GENERATED_FIR_TAGS: classReference, interfaceDeclaration, objectDeclaration */
