// RUN_PIPELINE_TILL: FRONTEND

package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

<!SERVICELOADER_NO_PUBLIC_CONSTRUCTOR!>@ServiceLoader(Bar::class)
class BarImpl private constructor() : Bar<!>
