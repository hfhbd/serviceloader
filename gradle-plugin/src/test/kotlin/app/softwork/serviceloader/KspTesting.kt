package app.softwork.serviceloader

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class KspTesting {
    @Test
    fun kotlinJvm() {
        val temp = File("src/testFixtures/resources/ksp-jvm")

        val build = GradleRunner.create()
            .withProjectDir(temp)
            .withArguments(":clean", ":assemble", ":kspBarKotlin", "--stacktrace", "--configuration-cache")
            .build()

        assertEquals(TaskOutcome.SUCCESS, build.task(":assemble")?.outcome)
        assertEquals(TaskOutcome.SUCCESS, build.task(":kspKotlin")?.outcome)

        assertEquals(
            setOf("Foo"),
            File(temp, "build/generated/ksp/main/resources/META-INF/services").listFiles()
                ?.map { it.name }?.toSet(),
        )
        assertEquals(
            "FooImpl\n",
            File(temp, "build/generated/ksp/main/resources/META-INF/services/Foo").readText()
        )
        assertEquals(
            setOf("Bar"),
            File(temp, "build/generated/ksp/bar/resources/META-INF/services").listFiles()
                ?.map { it.name }?.toSet(),
        )
        assertEquals(
            "BarImpl\n",
            File(temp, "build/generated/ksp/bar/resources/META-INF/services/Bar").readText()
        )
    }

    @Test
    fun kotlinMpp() {
        val temp = File("src/testFixtures/resources/ksp-mpp")

        val build = GradleRunner.create()
            .withProjectDir(temp)
            .withArguments(":clean", ":assemble", "--stacktrace", "--configuration-cache")
            .build()

        assertEquals(TaskOutcome.SUCCESS, build.task(":assemble")?.outcome)
        assertEquals(TaskOutcome.SUCCESS, build.task(":kspKotlinJvm")?.outcome)

        assertEquals(
            setOf("Foo", "CommonFoo"),
            File(temp, "build/generated/ksp/jvm/jvmMain/resources/META-INF/services").listFiles()
                ?.map { it.name }?.toSet(),
        )
        assertEquals(
            "FooImpl\n",
            File(temp, "build/generated/ksp/jvm/jvmMain/resources/META-INF/services/Foo").readText()
        )
        assertEquals(
            "CommonFooImpl\n",
            File(temp, "build/generated/ksp/jvm/jvmMain/resources/META-INF/services/CommonFoo").readText()
        )
    }

    @Test
    fun java() {
        val temp = File("src/testFixtures/resources/ksp-java")

        val build = GradleRunner.create()
            .withProjectDir(temp)
            .withArguments(":clean", ":assemble", "--stacktrace", "--configuration-cache")
            .build()

        assertEquals(TaskOutcome.SUCCESS, build.task(":assemble")?.outcome)
        assertEquals(TaskOutcome.SUCCESS, build.task(":kspKotlin")?.outcome)

        assertEquals(
            setOf("Foo"),
            File(temp, "build/generated/ksp/main/resources/META-INF/services").listFiles()
                ?.map { it.name }?.toSet(),
        )
        assertEquals(
            "FooImpl\n",
            File(temp, "build/generated/ksp/main/resources/META-INF/services/Foo").readText()
        )
    }
}
