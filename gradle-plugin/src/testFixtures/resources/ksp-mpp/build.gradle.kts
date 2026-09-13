plugins {
    id("io.github.hfhbd.serviceloader.ksp")
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
}

kotlin {
    jvmToolchain(8)

    jvm()
    linuxX64()
}
