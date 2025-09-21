plugins {
    kotlin("jvm")
    id("myPublish")
}

kotlin {
    jvmToolchain(8)
    explicitApi()
    compilerOptions {
        allWarningsAsErrors.set(true)
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

testing.suites.withType(JvmTestSuite::class).configureEach {
    useKotlinTest()
}
