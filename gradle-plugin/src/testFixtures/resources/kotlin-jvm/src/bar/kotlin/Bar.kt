import io.github.hfhbd.serviceloader.ServiceLoader

interface Bar

@ServiceLoader(Bar::class)
class BarImpl : Bar
