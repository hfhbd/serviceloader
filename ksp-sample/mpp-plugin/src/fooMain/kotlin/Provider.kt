import app.softwork.serviceloader.ServiceLoader

import sample.Provider

@ServiceLoader(Provider::class)
class FooImpl : Provider
