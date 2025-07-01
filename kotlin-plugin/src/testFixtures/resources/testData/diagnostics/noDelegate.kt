// RUN_PIPELINE_TILL: FRONTEND

package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Foo

@ServiceLoader(Foo::class)
class Bar : Foo by <!UNRESOLVED_REFERENCE!>Baz<!>()
