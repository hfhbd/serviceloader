package app.softwork.serviceloader

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class KotlinTesting {
    @Test
    fun kotlinJvm() {
        val temp = File("src/testFixtures/resources/kotlin-jvm")

        val build = GradleRunner.create()
            .withProjectDir(temp)
            .withArguments(":clean", ":build", ":compileBarKotlin", "--stacktrace", "--configuration-cache")
            .build()

        assertEquals(TaskOutcome.SUCCESS, build.task(":assemble")?.outcome)

        assertEquals(
            setOf("Foo"),
            File(temp, "build/generated/serviceloader/main/resources/META-INF/services").listFiles()
                ?.map { it.name }?.toSet(),
        )
        assertEquals(
            "FooImpl\n",
            File(temp, "build/generated/serviceloader/main/resources/META-INF/services/Foo").readText()
        )
        assertEquals(
            setOf("Bar"),
            File(temp, "build/generated/serviceloader/bar/resources/META-INF/services").listFiles()
                ?.map { it.name }?.toSet(),
        )
        assertEquals(
            "BarImpl\n",
            File(temp, "build/generated/serviceloader/bar/resources/META-INF/services/Bar").readText()
        )
        assertEquals(
            setOf("Foo"),
            File(temp, "build/resources/main/META-INF/services").listFiles()
                ?.map { it.name }?.toSet(),
        )
    }

    @Test
    fun kotlinMpp() {
        val temp = File("src/testFixtures/resources/kotlin-mpp")

        val build = GradleRunner.create()
            .withProjectDir(temp)
            .withArguments(":clean", ":assemble", "--stacktrace", "--configuration-cache")
            .build()

        assertEquals(TaskOutcome.SUCCESS, build.task(":assemble")?.outcome)

        assertEquals(
            setOf("Foo", "CommonFoo"),
            File(temp, "build/generated/serviceloader/jvmMain/resources/META-INF/services").listFiles()
                ?.map { it.name }?.toSet(),
        )
        assertEquals(
            "FooImpl\n",
            File(temp, "build/generated/serviceloader/jvmMain/resources/META-INF/services/Foo").readText()
        )
        assertEquals(
            "CommonFooImpl\n",
            File(temp, "build/generated/serviceloader/jvmMain/resources/META-INF/services/CommonFoo").readText()
        )

        assertEquals(
            setOf("Foo", "CommonFoo"),
            File(temp, "build/processedResources/jvm/main/META-INF/services").listFiles()
                ?.map { it.name }?.toSet(),
        )
    }
}
