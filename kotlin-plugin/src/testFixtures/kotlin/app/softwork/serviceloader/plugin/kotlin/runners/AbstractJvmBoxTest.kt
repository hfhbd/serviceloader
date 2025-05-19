package app.softwork.serviceloader.plugin.kotlin.runners

import app.softwork.serviceloader.plugin.kotlin.services.ExtensionRegistrarConfigurator
import app.softwork.serviceloader.plugin.kotlin.services.PluginAnnotationsProvider
import org.jetbrains.kotlin.test.FirParser
import org.jetbrains.kotlin.test.backend.handlers.AbstractIrHandler
import org.jetbrains.kotlin.test.backend.ir.IrBackendInput
import org.jetbrains.kotlin.test.builders.TestConfigurationBuilder
import org.jetbrains.kotlin.test.builders.irHandlersStep
import org.jetbrains.kotlin.test.directives.CodegenTestDirectives
import org.jetbrains.kotlin.test.directives.FirDiagnosticsDirectives
import org.jetbrains.kotlin.test.directives.JvmEnvironmentConfigurationDirectives
import org.jetbrains.kotlin.test.model.BackendKind
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.model.nameWithoutExtension
import org.jetbrains.kotlin.test.runners.codegen.AbstractFirBlackBoxCodegenTestBase
import org.jetbrains.kotlin.test.services.EnvironmentBasedStandardLibrariesPathProvider
import org.jetbrains.kotlin.test.services.KotlinStandardLibrariesPathProvider
import org.jetbrains.kotlin.test.services.TestServices
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.fail

/*
 * Containers of different directives, which can be used in tests:
 * - ModuleStructureDirectives
 * - LanguageSettingsDirectives
 * - DiagnosticsDirectives
 * - CodegenTestDirectives
 *
 * All of them are located in `org.jetbrains.kotlin.test.directives` package
 */
open class AbstractJvmBoxTest : AbstractFirBlackBoxCodegenTestBase(FirParser.LightTree) {
    override fun createKotlinStandardLibrariesPathProvider(): KotlinStandardLibrariesPathProvider {
        return EnvironmentBasedStandardLibrariesPathProvider
    }

    override fun configure(builder: TestConfigurationBuilder) {
        super.configure(builder)

        with(builder) {
            /*
             * Containers of different directives, which can be used in tests:
             * - ModuleStructureDirectives
             * - LanguageSettingsDirectives
             * - DiagnosticsDirectives
             * - FirDiagnosticsDirectives
             * - CodegenTestDirectives
             * - JvmEnvironmentConfigurationDirectives
             *
             * All of them are located in `org.jetbrains.kotlin.test.directives` package
             */

            defaultDirectives {
                +CodegenTestDirectives.DUMP_IR
                +FirDiagnosticsDirectives.FIR_DUMP
                +JvmEnvironmentConfigurationDirectives.FULL_JDK
                +CodegenTestDirectives.IGNORE_DEXING // Avoids loading R8 from the classpath.
            }
            val created = mutableMapOf<String, String>()
            useConfigurators(
                ::PluginAnnotationsProvider,
                {
                    ExtensionRegistrarConfigurator(it) { fileName, fileContent ->
                        created[fileName] = fileContent
                    }
                }
            )
            irHandlersStep {
                useHandlers(
                    { testServices, artifactKind -> A(testServices, artifactKind, created) },
                )
            }
        }
    }
}

private class A(
    testServices: TestServices,
    artifactKind: BackendKind<IrBackendInput>,
    private val created: Map<String, String>,
) : AbstractIrHandler(testServices, artifactKind) {
    override fun processModule(
        module: TestModule,
        info: IrBackendInput,
    ) {
        val testFile = module.files.single()
        val loadersFile = File(testFile.originalFile.parentFile, testFile.nameWithoutExtension + ".loaders")
        if (!loadersFile.exists()) {
            loadersFile.createNewFile()
            loadersFile.writeText(
                created.toList().joinToString("\n") { (service, impls) ->
                    "${service}=${impls.replace("\n", ",")}"
                }
            )
            fail("File $loadersFile does not exist.")
        }

        val expectedLoaders = loadersFile.readLines().associate {
            val (service, impls) = it.split("=")
            service to impls.replace(",", "\n")
        }

        assertEquals(expectedLoaders, created)
    }

    override fun processAfterAllModules(someAssertionWasFailed: Boolean) {}
}
