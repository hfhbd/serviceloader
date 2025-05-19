package app.softwork.serviceloader.plugin.kotlin

import app.softwork.serviceloader.plugin.kotlin.runners.AbstractJvmBoxTest
import app.softwork.serviceloader.plugin.kotlin.runners.AbstractJvmDiagnosticTest
import org.jetbrains.kotlin.generators.generateTestGroupSuiteWithJUnit5

fun main() {
    val generatedTests: String = System.getProperty("generatedTests")
    val testData: String = System.getProperty("testData")

    generateTestGroupSuiteWithJUnit5 {
        testGroup(testDataRoot = testData, testsRoot = generatedTests) {
            testClass<AbstractJvmDiagnosticTest> {
                model("diagnostics")
            }

            testClass<AbstractJvmBoxTest> {
                model("box")
            }
        }
    }
}
