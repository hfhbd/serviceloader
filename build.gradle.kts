import java.util.Base64

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

dependencies {
    testImplementation(kotlin("test"))
}

gradlePlugin.plugins.register("ServiceLoaderPlugin") {
    id = "app.softwork.serviceloader"
    implementationClass = "app.softwork.serviceloader.ServiceLoaderGradlePlugin"
}

tasks.validatePlugins {
    enableStricterValidation.set(true)
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))

kotlin {
    explicitApi()
    target.compilations.configureEach {
        kotlinOptions.allWarningsAsErrors = true
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications.configureEach {
        this as MavenPublication
        artifact(tasks.javadoc)
        artifact(tasks.kotlinSourcesJar)
        artifact(tasks.jar)

        pom {
            name.set("app.softwork ServiceLoader Gradle Plugin")
            description.set("A Gradle plugin to generate and validate service loader")
            url.set("https://github.com/hfhbd/kotlinx-serialization-csv")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("hfhbd")
                    name.set("Philip Wedemann")
                    email.set("mybztg+mavencentral@icloud.com")
                }
            }
            scm {
                connection.set("scm:git://github.com/hfhbd/kotlinx-serialization-csv.git")
                developerConnection.set("scm:git://github.com/hfhbd/kotlinx-serialization-csv.git")
                url.set("https://github.com/hfhbd/kotlinx-serialization-csv")
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey?.let { String(Base64.getDecoder().decode(it)).trim() }, signingPassword)
    sign(publishing.publications)
}
