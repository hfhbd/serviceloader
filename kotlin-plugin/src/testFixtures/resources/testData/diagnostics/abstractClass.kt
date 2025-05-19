package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

<!ABSTRACT_CLASS!>@ServiceLoader(Bar::class)
abstract class BarImpl : Bar<!>
