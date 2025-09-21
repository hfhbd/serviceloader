import app.softwork.serviceloader.ServiceLoader

interface CommonFoo

@ServiceLoader(CommonFoo::class)
class CommonFooImpl : CommonFoo
