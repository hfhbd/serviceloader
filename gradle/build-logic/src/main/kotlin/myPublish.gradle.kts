plugins {
    id("maven-publish")
    id("signing")
}

publishing {
    repositories {
        maven(url = "https://maven.pkg.github.com/hfhbd/serviceloader") {
            name = "GitHubPackages"
            credentials(PasswordCredentials::class)
        }
    }

    publications.withType<MavenPublication>().configureEach {
        pom {
            name = "io.github.hfhbd.serviceloader ServiceLoader Gradle Plugin"
            description = "A Gradle plugin to generate and validate service loaders"
            url = "https://github.com/hfhbd/serviceloader-gradle-plugin"
            licenses {
                license {
                    name = "Apache-2.0"
                    url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                }
            }
            developers {
                developer {
                    id = "hfhbd"
                    name = "Philip Wedemann"
                    email = "mybztg+mavencentral@icloud.com"
                }
            }
            scm {
                connection = "https://github.com/hfhbd/serviceloader-gradle-plugin.git"
                developerConnection = "scm:git://github.com/hfhbd/serviceloader-gradle-plugin.git"
                url = "https://github.com/hfhbd/serviceloader-gradle-plugin"
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        providers.gradleProperty("signingKey").orNull,
        providers.gradleProperty("signingPassword").orNull,
    )
    isRequired = providers.gradleProperty("signingKey").isPresent
    sign(publishing.publications)
}
