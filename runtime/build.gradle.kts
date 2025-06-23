import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    id("kotlinMPPSetup")
}

kotlin {
    abiValidation {
        @OptIn(ExperimentalAbiValidation::class)
        enabled.set(true)
    }
    jvm {
        val main = compilations.getByName("main")
        val jvm9 = compilations.create("9Main") {
            associateWith(main)
        }
        tasks.named(artifactsTaskName, Jar::class) {
            from(jvm9.output.allOutputs) {
                into("META-INF/versions/9")
            }
            manifest {
                manifest.attributes("Multi-Release" to true)
            }
        }
    }
}

tasks.check {
   dependsOn(tasks.checkLegacyAbi)
}

tasks.named<JavaCompile>("compileJvm9MainJava") {
    javaCompiler.set(javaToolchains.compilerFor {})
    options.release.set(9)

    options.compilerArgumentProviders += object : CommandLineArgumentProvider {

        @get:InputFiles
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val kotlinClasses = tasks.compileKotlinJvm.flatMap { it.destinationDirectory }

        override fun asArguments(): List<String> = listOf(
            "--patch-module",
            "app.softwork.serviceloader.runtime=${kotlinClasses.get().asFile.absolutePath}"
        )
    }
}
