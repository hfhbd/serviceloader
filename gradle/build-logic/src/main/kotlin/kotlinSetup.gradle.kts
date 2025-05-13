plugins {
    kotlin("jvm")
    id("setup")
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
