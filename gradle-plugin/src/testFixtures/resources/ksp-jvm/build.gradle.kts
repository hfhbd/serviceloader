plugins {
    id("app.softwork.serviceloader")
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

kotlin.jvmToolchain(8)

sourceSets.register("bar")
