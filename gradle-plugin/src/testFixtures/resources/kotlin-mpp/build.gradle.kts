plugins {
    kotlin("multiplatform")
    id("app.softwork.serviceloader-compiler")
}

kotlin {
    jvmToolchain(8)

    jvm()
    linuxX64()
}
