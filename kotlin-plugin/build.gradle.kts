plugins {
    id("kotlinSetup")
    id("maven-publish")
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
}

kotlin.compilerOptions {
    optIn.add("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
}

dependencies {
    compileOnly(libs.kotlin.compiler)
}

testing.suites.named("test", JvmTestSuite::class) {
    dependencies {
        implementation(libs.kotlinCompilerTester)
        implementation(libs.kotlin.compiler.embeddable)
        implementation(projects.runtime)
    }
}
