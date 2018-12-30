package io.lucasvalenteds.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.tasks.JacocoReport

fun Project.setupJacoco() {
    apply(plugin = "jacoco")

    tasks.withType<JacocoReport> {
        reports.apply {
            csv.isEnabled = false
            xml.isEnabled = true
            html.isEnabled = true
        }
    }
}
