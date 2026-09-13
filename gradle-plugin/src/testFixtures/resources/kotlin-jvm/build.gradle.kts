plugins {
    kotlin("jvm")
    id("io.github.hfhbd.serviceloader")
}

kotlin.jvmToolchain(8)

sourceSets.register("bar")
