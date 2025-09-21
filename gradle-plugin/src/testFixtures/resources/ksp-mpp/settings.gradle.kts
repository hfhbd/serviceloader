pluginManagement {
    includeBuild("../../../../../gradle/build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("myRepos")
}

dependencyResolutionManagement.versionCatalogs.register("libs") {
    from(files("../../../../../gradle/libs.versions.toml"))
}

rootProject.name = "testing"

includeBuild("../../../../../")
