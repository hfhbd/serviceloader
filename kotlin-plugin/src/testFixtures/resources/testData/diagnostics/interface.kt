package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

<!ABSTRACT_CLASS, NO_PUBLIC_CONSTRUCTOR!>@ServiceLoader(Bar::class)
interface BarImpl : Bar<!>
