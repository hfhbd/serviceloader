plugins {
    id("app.softwork.serviceloader")
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
}

kotlin {
    jvmToolchain(8)

    jvm()
    linuxX64()
}
