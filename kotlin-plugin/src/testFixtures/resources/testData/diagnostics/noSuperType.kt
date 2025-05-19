// RUN_PIPELINE_TILL: FRONTEND

package foo.bar

import app.softwork.serviceloader.ServiceLoader

interface Bar

<!SUPERTYPE_OF_CLASS_DOES_NOT_MATCH!>@ServiceLoader(Bar::class)
class BarImpl<!>
