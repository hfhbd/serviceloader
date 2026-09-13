package io.github.hfhbd.serviceloader

import kotlin.test.Test

interface CommonProvider

@ServiceLoader(CommonProvider::class)
class CommonImpl : CommonProvider {
    @Test
    fun compiles() {}
}
