package app.softwork.serviceloader

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.listProperty
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.FilesSubpluginOption
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

public class ServiceLoaderPlugin : KotlinCompilerPluginSupportPlugin {
    override fun apply(target: Project) {
        super.apply(target)
        val kotlin = target.extensions.getByType(KotlinProjectExtension::class.java)
        kotlin.sourceSets.configureEach {
            dependencies {
                implementation("app.softwork.serviceloader:runtime:$VERSION")
            }
        }
    }

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean =
        kotlinCompilation.platformType == KotlinPlatformType.jvm

    override fun getCompilerPluginId(): String = "app.softwork.serviceloader"

    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        groupId = "app.softwork.serviceloader",
        artifactId = "kotlin-plugin",
        version = VERSION,
    )

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.project
        val kotlinSourceSet = kotlinCompilation.defaultSourceSet

        val outputDir = project.layout.buildDirectory.dir(
            "generated/serviceloader/${kotlinSourceSet.name}/resources"
        )

        val compileTaskProvider = kotlinCompilation.compileTaskProvider
        compileTaskProvider.configure {
            outputs.dir(outputDir)
        }

        val outputDirWithTask = project.files(outputDir) {
            builtBy(compileTaskProvider)
        }

        kotlinSourceSet.resources.srcDir(outputDirWithTask)

        val options = project.objects.listProperty<SubpluginOption>()
        options.add(outputDir.map {
            FilesSubpluginOption(
                key = "outputDir",
                files = listOf(it.asFile),
            )
        })
        return options
    }
}
