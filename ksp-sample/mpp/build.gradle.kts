plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
}

kotlin {
    linuxX64()

    jvm("foo") {
        attributes {
            // https://youtrack.jetbrains.com/issue/KT-55751
            val KT_55751 = Attribute.of("KT_55751", String::class.java)
            attribute(KT_55751, "KT_55751")
        }
        withJava()
    }
    jvm("bar")

    sourceSets {
        commonMain {
            dependencies {
                compileOnly("app.softwork.serviceloader:ksp-annotation")
            }
        }
        named("fooMain") {
            dependencies {
                implementation(projects.lib)
            }
        }
        named("barMain") {
            dependencies {
                implementation(projects.lib)
            }
        }
    }
}

val kspFoo by configurations.existing
val kspBar by configurations.existing

dependencies {
    operator fun NamedDomainObjectProvider<Configuration>.invoke(dependencyNotation: Any): Dependency? = name.invoke(dependencyNotation)
    
    kspFoo("app.softwork.serviceloader:ksp-plugin")
    kspBar("app.softwork.serviceloader:ksp-plugin")
}
