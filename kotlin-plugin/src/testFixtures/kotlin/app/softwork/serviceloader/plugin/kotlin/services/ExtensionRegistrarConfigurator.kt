package app.softwork.serviceloader.plugin.kotlin.services

import app.softwork.serviceloader.plugin.kotlin.ServiceLoaderCompilerPluginRegistrar.Companion.registerServiceLoaderFIR
import app.softwork.serviceloader.plugin.kotlin.ServiceLoaderCompilerPluginRegistrar.Companion.registerServiceLoaderIR
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.EnvironmentConfigurator
import org.jetbrains.kotlin.test.services.TestServices

class ExtensionRegistrarConfigurator(
    testServices: TestServices,
    private val writeFile: (fileName: String, fileContent: String) -> Unit,
) : EnvironmentConfigurator(testServices) {
    override fun CompilerPluginRegistrar.ExtensionStorage.registerCompilerExtensions(
        module: TestModule,
        configuration: CompilerConfiguration
    ) {
        registerServiceLoaderFIR()
        registerServiceLoaderIR(writeFile)
    }
}
