package io.lucasvalenteds.plugin

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import java.io.File

fun Project.setupJar() {
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<ShadowJar> {
        baseName = rootProject.name
        version = rootProject.version.toString()
        classifier = ""
    }
}

val Project.jarFilename: Lazy<File>
    get() = lazy {
        val shadowJar: ShadowJar = tasks.getByPath("shadowJar") as ShadowJar
        shadowJar.destinationDir
            .relativeTo(project.projectDir)
            .resolve(shadowJar.archiveName)
    }
