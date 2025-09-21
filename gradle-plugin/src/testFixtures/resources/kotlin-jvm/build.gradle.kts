plugins {
    kotlin("jvm")
    id("app.softwork.serviceloader-compiler")
}

kotlin.jvmToolchain(8)

sourceSets.register("bar")
