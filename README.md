# serviceloader

A Kotlin compiler and ksp plugin that generate and validate your service providers.

The Kotlin compiler plugin does not support Java source code. In this case, you need to use the ksp plugin.

## Usage

The Gradle plugin is uploaded to mavenCentral, so you need to add `mavenCentral()` to your plugin repositories:

```kotlin
// settings.gradle.kts
pluginManagement {
    repositories {
        mavenCentral()
    }
}
```

### Use the Kotlin compiler plugin

Apply the Kotlin compiler plugin via Gradle:

```kotlin
// build.gradle.kts

plugins {
    id("app.softwork.serviceloader-compiler") version "LATEST"
}
```

You might also want to enable the compiler plugin in IntelliJ by setting `kotlin.k2.only.bundled.compiler.plugins.enabled` to `false` in the registry.

### Use with ksp

Apply the ksp plugin via Gradle:.

```kotlin
// build.gradle.kts

plugins {
    id("com.google.devtools.ksp")
    id("app.softwork.serviceloader") version "LATEST"
}
```

## Annotate the code

And use the `app.softwork.serviceloader.ServiceLoader` annotation:

```kotlin
import app.softwork.serviceloader.ServiceLoader

interface Service

@ServiceLoader(Service::class)
class Provider : Service
```

## License

Apache 2.0
