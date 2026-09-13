plugins {
    kotlin("multiplatform")
    id("io.github.hfhbd.serviceloader")
}

kotlin {
    jvmToolchain(8)

    jvm()
    linuxX64()
}
