plugins {
    id("kotlinSetup")
    id("io.github.hfhbd.kotlin-compiler-testing")
}

kotlin.compilerOptions {
    optIn.add("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
}

kotlinTesting {
    mainClass = "app.softwork.serviceloader.plugin.kotlin.GenerateTestsKt"
    dependencies {
        annotation(projects.runtime)
    }
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
}
