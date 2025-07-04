package app.softwork.serviceloader.plugin.kotlin.fir

import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderErrors.SERVICELOADER_ABSTRACT_CLASS
import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderErrors.SERVICELOADER_LOCAL_CLASS
import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderErrors.SERVICELOADER_NO_PUBLIC_CONSTRUCTOR
import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderErrors.SERVICELOADER_SUPERTYPE_OF_CLASS_DOES_NOT_MATCH
import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderPredicateMatchingService.Companion.forClass
import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderPredicateMatchingService.Companion.serviceLoaderClass
import app.softwork.serviceloader.plugin.kotlin.fir.ServiceLoaderPredicateMatchingService.Companion.serviceLoaderPredicateMatchingService
import org.jetbrains.kotlin.diagnostics.*
import org.jetbrains.kotlin.fir.analysis.checkers.*
import org.jetbrains.kotlin.fir.analysis.checkers.context.*
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.*
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.declarations.utils.*
import org.jetbrains.kotlin.fir.resolve.*
import org.jetbrains.kotlin.fir.types.*

internal data object ServiceLoaderClassChecker : FirRegularClassChecker(MppCheckerKind.Common) {
    context(context: CheckerContext, reporter: DiagnosticReporter)
    override fun check(
        declaration: FirRegularClass,
    ) {
        val matcher = context.session.serviceLoaderPredicateMatchingService
        if (matcher.isAnnotated(declaration.symbol)) {

            val forClassValueSymbol = declaration.annotations.getAnnotationByClassId(
                classId = serviceLoaderClass, session = context.session,
            )?.argumentMapping?.mapping[forClass]?.extractClassFromArgument(context.session) ?: return
            val forClassValue = forClassValueSymbol.classId

            val supertypes = lookupSuperTypes(
                declaration,
                lookupInterfaces = true,
                deep = true,
                context.session,
                substituteTypes = false
            )
            if (supertypes.none { it.classId == forClassValue }) {
                reporter.reportOn(
                    declaration.source,
                    SERVICELOADER_SUPERTYPE_OF_CLASS_DOES_NOT_MATCH,
                    declaration.symbol,
                    forClassValueSymbol,
                )
            }
            if (declaration.constructors(context.session).none { constructorSymbol ->
                    constructorSymbol.visibility.isPublicAPI && constructorSymbol.valueParameterSymbols.isEmpty()
                }
            ) {
                reporter.reportOn(
                    declaration.source,
                    SERVICELOADER_NO_PUBLIC_CONSTRUCTOR,
                    declaration.symbol,
                )
            }
            if (declaration.isAbstract) {
                reporter.reportOn(
                    declaration.source,
                    SERVICELOADER_ABSTRACT_CLASS,
                    declaration.symbol,
                )
            }
            if (declaration.isLocal) {
                reporter.reportOn(
                    declaration.source,
                    SERVICELOADER_LOCAL_CLASS,
                    declaration.symbol,
                )
            }
        }
    }
}
