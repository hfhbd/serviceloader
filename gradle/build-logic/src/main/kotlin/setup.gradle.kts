plugins {
    id("jvm-test-suite")
    id("myPublish")
}

testing.suites.withType(JvmTestSuite::class).configureEach {
    useKotlinTest()
}
