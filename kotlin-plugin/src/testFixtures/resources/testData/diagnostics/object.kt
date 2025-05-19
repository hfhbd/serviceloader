package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

@ServiceLoader(Bar::class)
<!NO_PUBLIC_CONSTRUCTOR!>object BarImpl<!> : Bar
