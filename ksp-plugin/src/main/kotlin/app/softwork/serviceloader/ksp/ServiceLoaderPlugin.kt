package app.softwork.serviceloader.ksp

import com.google.devtools.ksp.*
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*

public class ServiceLoaderPlugin(private val codeGenerator: CodeGenerator) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val providers = mutableMapOf<String, MutableList<KSClassDeclaration>>()

        for (annotatedClass in resolver.getSymbolsWithAnnotation("app.softwork.serviceloader.ServiceLoader")) {
            if (annotatedClass is KSClassDeclaration) {
                for (anno in annotatedClass.annotations) {
                    if (anno.shortName.getShortName() == "ServiceLoader") {
                        val provider = anno.arguments.single().value as KSType
                        
                        val providerDec = provider.declaration

                        require(annotatedClass.getAllSuperTypes().any {
                            it.declaration == providerDec
                        }) {
                            "$annotatedClass does not implement or inherit $provider."
                        }
                        require(annotatedClass.getConstructors().any {
                            it.isPublic() && it.parameters.isEmpty()
                        }) {
                            "$annotatedClass does not have a public zero arg constructor."
                        }
                        require(!annotatedClass.isAbstract()) {
                            "$annotatedClass is abstract."
                        }
                        requireNotNull(annotatedClass.qualifiedName) {
                            "$annotatedClass is local."
                        }
                        val providerName = providerDec.qualifiedName!!.asString()
                        val found = providers[provider.toString()]

                        providers[providerName] = if (found == null) {
                            mutableListOf(annotatedClass)
                        } else {
                            found.add(annotatedClass)
                            found
                        }
                    }
                }
            }
        }
        for ((provider, classes) in providers) {
            codeGenerator.createNewFileByPath(
                Dependencies(false, sources = classes.map { it.containingFile!! }.toTypedArray()),
                "META-INF/services/$provider",
                extensionName = ""
            ).bufferedWriter().use {
                for (impl in classes) {
                    it.appendLine(impl.qualifiedName!!.asString())
                }
            }
        }
        return emptyList()
    }
}
