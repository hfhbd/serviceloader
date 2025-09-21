plugins {
    id("java-gradle-plugin")
    id("kotlinSetup")
    id("java-test-fixtures")
}

kotlin.jvmToolchain(21)

dependencies {
    compileOnly(libs.plugins.kotlin.jvm.toDep())
}

fun Provider<PluginDependency>.toDep(): Provider<ExternalModuleDependency> = map {
    dependencyFactory.create(it.pluginId, "${it.pluginId}.gradle.plugin", it.version.toString())
}

tasks.validatePlugins {
    enableStricterValidation.set(true)
}

val version by tasks.registering(VersionTask::class)

sourceSets.main {
    kotlin.srcDir(version)
}

gradlePlugin {
    plugins.configureEach {
        displayName = "A Gradle plugin to generate and validate service loaders"
        description = "A Gradle plugin to generate and validate service loaders"
    }

    plugins.register("serviceloader") {
        id = "app.softwork.serviceloader-compiler"
        implementationClass = "app.softwork.serviceloader.ServiceLoaderPlugin"
    }

    plugins.register("serviceloader-ksp") {
        id = "app.softwork.serviceloader"
        implementationClass = "app.softwork.serviceloader.ServiceLoaderKspPlugin"
    }
}

testing.suites.named("test", JvmTestSuite::class) {
    targets.configureEach {
        testTask {
            environment("projectDir", layout.settingsDirectory.toString())
        }
    }
}
