plugins {
    id("kotlinSetup")
    id("io.github.hfhbd.kotlin-compiler-testing")
}

kotlin.compilerOptions {
    optIn.add("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
    freeCompilerArgs.add("-Xcontext-parameters")
}

dependencies {
    annotationsRuntime(projects.runtime)
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
}

tasks.generateTests {
    mainClass.set("app.softwork.serviceloader.plugin.kotlin.GenerateTestsKt")
}
