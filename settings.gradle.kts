pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    id("myRepos")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
    id("com.gradle.develocity") version "4.0"
}

develocity {
    buildScan {
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")
        val isCI = providers.environmentVariable("CI").isPresent
        publishing {
            onlyIf { isCI }
        }
        tag("CI")
    }
}

rootProject.name = "serviceloader"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

include(":gradle-plugin")

include(":runtime")

include(":ksp-plugin")
include(":kotlin-plugin")
