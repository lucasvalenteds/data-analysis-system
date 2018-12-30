package io.lucasvalenteds.plugin

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.tasks.JacocoReport

fun Project.setupJunit() {
    apply(plugin = "com.adarshr.test-logger")

    tasks.withType<Test> {
        failFast = true
        useJUnitPlatform()
        finalizedBy(tasks.withType<JacocoReport>())
    }
}
