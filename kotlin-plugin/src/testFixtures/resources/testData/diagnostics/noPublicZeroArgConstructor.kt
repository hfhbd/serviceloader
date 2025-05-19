package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

<!NO_PUBLIC_CONSTRUCTOR!>@ServiceLoader(Bar::class)
class BarImpl(val s: String) : Bar<!>
