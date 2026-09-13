import io.github.hfhbd.serviceloader.ServiceLoader

interface CommonFoo

@ServiceLoader(CommonFoo::class)
class CommonFooImpl : CommonFoo
