plugins {
    kotlin("multiplatform")
    myPublish
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(8)
    explicitApi()
    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                allWarningsAsErrors.set(true)
            }
        }
    }

    jvm()

    js(IR) {
        browser()
        nodejs()
    }

    // tier 1
    linuxX64()
    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()

    // tier 2
    linuxArm64()
    watchosSimulatorArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    tvosSimulatorArm64()
    tvosX64()
    tvosArm64()
    iosArm64()

    // tier 3
    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()
    mingwX64()
    watchosDeviceArm64()
}

val emptyJar by tasks.registering(Jar::class) { }

publishing {
    publications.configureEach {
        this as MavenPublication
        artifact(emptyJar) {
            classifier = "javadoc"
        }
    }
}
