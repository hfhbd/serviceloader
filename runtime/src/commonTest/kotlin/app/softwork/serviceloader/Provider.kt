package app.softwork.serviceloader

import kotlin.test.Test

interface CommonProvider

@ServiceLoader(CommonProvider::class)
class CommonImpl : CommonProvider {
    @Test
    fun compiles() {}
}
