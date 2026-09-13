plugins {
    id("kotlinMPPRuntime")
}

tasks.compileJvm9MainJava {
    javaCompiler = javaToolchains.compilerFor {}
    options.release = 9

    options.compilerArgumentProviders += object : CommandLineArgumentProvider {

        @get:InputFiles
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val kotlinClasses = tasks.compileKotlinJvm.flatMap { it.destinationDirectory }

        override fun asArguments() = listOf(
            "--patch-module",
            "io.github.hfhbd.serviceloader.runtime=${kotlinClasses.get().asFile.absolutePath}"
        )
    }
}
