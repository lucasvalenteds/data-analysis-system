package io.lucasvalenteds.plugin

import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPluginConvention
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

fun Project.setupApplication() {
    apply(plugin = "application")
    configure<ApplicationPluginConvention> {
        mainClassName = "$group.batch.Main"
    }
}
