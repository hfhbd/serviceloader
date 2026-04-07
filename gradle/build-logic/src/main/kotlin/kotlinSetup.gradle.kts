plugins {
    kotlin("jvm")
    id("myPublish")
}

kotlin {
    jvmToolchain(8)
    explicitApi()
}

java {
    withJavadocJar()
    withSourcesJar()
}

testing.suites.withType(JvmTestSuite::class).configureEach {
    useKotlinTest()
}
