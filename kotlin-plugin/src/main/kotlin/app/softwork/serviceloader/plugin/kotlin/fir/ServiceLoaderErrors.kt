package app.softwork.serviceloader.plugin.kotlin.fir

import org.jetbrains.kotlin.diagnostics.KtDiagnosticsContainer
import org.jetbrains.kotlin.diagnostics.error1
import org.jetbrains.kotlin.diagnostics.error2
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol
import org.jetbrains.kotlin.psi.KtElement

internal data object ServiceLoaderErrors : KtDiagnosticsContainer() {
    val SERVICELOADER_SUPERTYPE_OF_CLASS_DOES_NOT_MATCH by error2<KtElement, FirRegularClassSymbol, FirRegularClassSymbol>()
    val SERVICELOADER_NO_PUBLIC_CONSTRUCTOR by error1<KtElement, FirRegularClassSymbol>()
    val SERVICELOADER_ABSTRACT_CLASS by error1<KtElement, FirRegularClassSymbol>()
    val SERVICELOADER_LOCAL_CLASS by error1<KtElement, FirRegularClassSymbol>()

    override fun getRendererFactory() = ServiceLoadersErrorMessages
}
