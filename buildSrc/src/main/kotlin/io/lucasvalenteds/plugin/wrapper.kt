package io.lucasvalenteds.plugin

import org.gradle.api.Project
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.kotlin.dsl.withType

fun Project.setupGradleWrapper() {
    tasks.withType<Wrapper> {
        distributionType = Wrapper.DistributionType.ALL
    }
}
