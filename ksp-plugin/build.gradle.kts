plugins {
    id("kotlinSetup")
}

dependencies {
    compileOnly(libs.ksp.api)
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
}
