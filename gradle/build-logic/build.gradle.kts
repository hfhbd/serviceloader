plugins {
    `kotlin-dsl`
}

dependencies { 
    implementation(libs.plugins.kotlin.jvm.toDep())
    implementation(libs.plugins.kotlin.multiplatform.toDep())
    implementation(libs.plugins.ksp.toDep())
    implementation(libs.plugins.mavencentral.toDep())
    implementation(libs.plugins.kotlin.compiler.testing.toDep())

    implementation(libs.plugins.foojay.toDep())
    implementation(libs.plugins.develocity.toDep())
}

fun Provider<PluginDependency>.toDep(): Provider<String> = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}
