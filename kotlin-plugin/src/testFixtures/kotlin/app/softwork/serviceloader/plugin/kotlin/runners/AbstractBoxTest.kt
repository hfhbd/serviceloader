package app.softwork.serviceloader.plugin.kotlin.runners

import org.jetbrains.kotlin.platform.jvm.JvmPlatforms
import org.jetbrains.kotlin.test.FirParser
import org.jetbrains.kotlin.test.TargetBackend
import org.jetbrains.kotlin.test.backend.BlackBoxCodegenSuppressor
import org.jetbrains.kotlin.test.backend.handlers.AbstractIrHandler
import org.jetbrains.kotlin.test.backend.handlers.IrTextDumpHandler
import org.jetbrains.kotlin.test.backend.handlers.IrTreeVerifierHandler
import org.jetbrains.kotlin.test.backend.handlers.JvmBoxRunner
import org.jetbrains.kotlin.test.backend.ir.IrBackendInput
import org.jetbrains.kotlin.test.backend.ir.JvmIrBackendFacade
import org.jetbrains.kotlin.test.builders.TestConfigurationBuilder
import org.jetbrains.kotlin.test.builders.irHandlersStep
import org.jetbrains.kotlin.test.builders.jvmArtifactsHandlersStep
import org.jetbrains.kotlin.test.directives.CodegenTestDirectives.DUMP_IR
import org.jetbrains.kotlin.test.directives.configureFirParser
import org.jetbrains.kotlin.test.frontend.fir.Fir2IrResultsConverter
import org.jetbrains.kotlin.test.model.BackendKind
import org.jetbrains.kotlin.test.model.DependencyKind
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.model.nameWithoutExtension
import org.jetbrains.kotlin.test.runners.RunnerWithTargetBackendForTestGeneratorMarker
import org.jetbrains.kotlin.test.services.TestServices
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue
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
open class AbstractBoxTest : BaseTestRunner(), RunnerWithTargetBackendForTestGeneratorMarker {
    override val targetBackend: TargetBackend
        get() = TargetBackend.JVM_IR

    override fun configure(builder: TestConfigurationBuilder) {
        with(builder) {
            globalDefaults {
                targetBackend = TargetBackend.JVM_IR
                targetPlatform = JvmPlatforms.defaultJvmPlatform
                dependencyKind = DependencyKind.Binary
            }

            configureFirParser(FirParser.Psi)

            defaultDirectives {
                +DUMP_IR
            }

            val created = mutableMapOf<String, String>()
            commonFirWithPluginFrontendConfiguration { fileName, fileContent ->
                created[fileName] = fileContent
            }
            facadeStep(::Fir2IrResultsConverter)
            irHandlersStep {
                useHandlers(
                    ::IrTextDumpHandler,
                    ::IrTreeVerifierHandler,
                    { testServices, artifactKind -> A(testServices, artifactKind, created) },
                )
            }
            facadeStep(::JvmIrBackendFacade)
            jvmArtifactsHandlersStep {
                useHandlers(::JvmBoxRunner)
            }

            useAfterAnalysisCheckers(::BlackBoxCodegenSuppressor)
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
