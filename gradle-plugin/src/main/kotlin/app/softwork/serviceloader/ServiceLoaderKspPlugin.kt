package app.softwork.serviceloader

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet.MAIN_SOURCE_SET_NAME
import org.gradle.api.tasks.SourceSetContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

public abstract class ServiceLoaderKspPlugin  : Plugin<Project> {
    override fun apply(target: Project) {
        val kspPluginDep = target.dependencies.create("app.softwork.serviceloader:ksp-plugin:$VERSION")
        val kspAnnotationDep = target.dependencies.create("app.softwork.serviceloader:runtime:$VERSION")

        target.pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
            val sourceSets = target.extensions.getByName("sourceSets") as SourceSetContainer
            sourceSets.configureEach {
                target.dependencies.add(it.compileOnlyConfigurationName, kspAnnotationDep)
                if (it.name == MAIN_SOURCE_SET_NAME) {
                    target.dependencies.add("ksp", kspPluginDep)
                } else {
                    target.dependencies.add("ksp" + it.name.replaceFirstChar { it.uppercaseChar() }, kspPluginDep)
                }
            }
        }

        target.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
            val kotlin = target.extensions.getByName("kotlin")  as KotlinMultiplatformExtension
            val jvmTargets = kotlin.targets.withType(KotlinJvmTarget::class.java)
            jvmTargets.configureEach {
                val kspName = "ksp" + it.name.replaceFirstChar { it.uppercaseChar() }
                target.dependencies.add(kspName, kspPluginDep)
            }

            kotlin.sourceSets.named("commonMain") {
                target.dependencies.add(it.compileOnlyConfigurationName, kspAnnotationDep)
            }
        }

    }
}
