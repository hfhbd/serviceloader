package io.github.hfhbd.serviceloader.plugin.kotlin

import io.github.hfhbd.serviceloader.plugin.kotlin.runners.AbstractJvmBoxTest
import io.github.hfhbd.serviceloader.plugin.kotlin.runners.AbstractJvmDiagnosticTest
import org.jetbrains.kotlin.generators.dsl.junit5.generateTestGroupSuiteWithJUnit5

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
