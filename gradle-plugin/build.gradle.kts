plugins {
    `kotlin-dsl`
    id("setup")
    id("com.android.lint")
}

kotlin.jvmToolchain(21)

dependencies {
    compileOnly(libs.plugins.ksp.toDep())
    // https://github.com/gradle/gradle/issues/23576
    runtimeOnly(libs.plugins.ksp.toDep())

    implementation(libs.plugins.kotlin.jvm.toDep())

    lintChecks(libs.gradle.lint)
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

gradlePlugin.plugins.configureEach {
    displayName = "A Gradle plugin to generate and validate service loaders"
    description = "A Gradle plugin to generate and validate service loaders"
}

gradlePlugin.plugins.register("serviceloader") {
    id = "app.softwork.serviceloader-compiler"
    implementationClass = "app.softwork.serviceloader.ServiceLoaderPlugin"
}

testing.suites.named("test", JvmTestSuite::class) {
    useKotlinTest()

    dependencies {
        implementation(libs.plugins.ksp.toDep())
    }

    targets.configureEach {
        testTask {
            environment("projectDir", layout.settingsDirectory.toString())
        }
    }
}
