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
    enableStricterValidation = true
}

val version = tasks.register("version", VersionTask::class)

sourceSets.main {
    kotlin.srcDir(version)
}

gradlePlugin {
    plugins.configureEach {
        displayName = "A Gradle plugin to generate and validate service loaders"
        description = "A Gradle plugin to generate and validate service loaders"
    }

    plugins.register("io.github.hfhbd.serviceloader") {
        id = name
        implementationClass = "io.github.hfhbd.serviceloader.ServiceLoaderPlugin"
    }

    plugins.register("io.github.hfhbd.serviceloader.ksp") {
        id = name
        implementationClass = "io.github.hfhbd.serviceloader.ServiceLoaderKspPlugin"
    }
}
