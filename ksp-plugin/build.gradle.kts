plugins {
    id("kotlinSetup")
    id("maven-publish")
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
}

dependencies {
    compileOnly(libs.ksp.api)
}

testing.suites.named("test", JvmTestSuite::class) {
    useKotlinTest()

    dependencies {
        implementation(libs.kotlinCompilerTester.ksp)
        implementation(libs.ksp.api)
        implementation(projects.runtime)
    }
}
