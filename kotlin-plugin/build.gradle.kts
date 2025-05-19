plugins {
    id("kotlinSetup")
    id("maven-publish")
    id("io.github.hfhbd.kotlin-compiler-testing")
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
}

kotlin.compilerOptions {
    optIn.add("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
    freeCompilerArgs.add("-Xcontext-parameters")
}

dependencies {
    annotationsRuntime(projects.runtime)
}

val generateTests by tasks.existing(JavaExec::class) {
    mainClass.set("app.softwork.serviceloader.plugin.kotlin.GenerateTestsKt")
}
