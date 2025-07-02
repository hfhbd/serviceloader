package app.softwork.serviceloader.plugin.kotlin.fir

import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderErrors.SERVICELOADER_ABSTRACT_CLASS
import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderErrors.SERVICELOADER_LOCAL_CLASS
import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderErrors.SERVICELOADER_NO_PUBLIC_CONSTRUCTOR
import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderErrors.SERVICELOADER_SUPERTYPE_OF_CLASS_DOES_NOT_MATCH
import org.jetbrains.kotlin.diagnostics.*
import org.jetbrains.kotlin.diagnostics.rendering.*
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers.DECLARATION_NAME

internal data object ServiceLoadersErrorMessages : BaseDiagnosticRendererFactory() {
    override val MAP = KtDiagnosticFactoryToRendererMap("ServiceLoader").apply {
        put(
            SERVICELOADER_SUPERTYPE_OF_CLASS_DOES_NOT_MATCH,
            "{0} does not implement or inherit {1}.",
            DECLARATION_NAME,
            DECLARATION_NAME,
        )

        put(
            SERVICELOADER_NO_PUBLIC_CONSTRUCTOR,
            "{0} does not have a public zero arg constructor.",
            DECLARATION_NAME,
        )

        put(
            SERVICELOADER_ABSTRACT_CLASS,
            "{0} is abstract.",
            DECLARATION_NAME,
        )
        
        put(
            SERVICELOADER_LOCAL_CLASS,
            "{0} is local.",
            DECLARATION_NAME,
        )
    }
}
