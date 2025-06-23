plugins {
    id("kotlinMPPSetup")
}

kotlin.jvm {
    val main = compilations.named("main")
    val jvm9 = compilations.register("9Main") {
        associateWith(main.get())
    }
    tasks.named(artifactsTaskName, Jar::class) {
        from(jvm9.map { it.output.allOutputs}) {
            into("META-INF/versions/9")
        }
        manifest {
            manifest.attributes("Multi-Release" to true)
        }
    }
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
