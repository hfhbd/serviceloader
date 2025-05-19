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
