import com.adarshr.gradle.testlogger.TestLoggerExtension
import io.lucasvalenteds.plugin.setupApplication
import io.lucasvalenteds.plugin.setupGradleWrapper
import io.lucasvalenteds.plugin.setupJacoco
import io.lucasvalenteds.plugin.setupJar
import io.lucasvalenteds.plugin.setupJava
import io.lucasvalenteds.plugin.setupJunit

group = "io.lucasvalenteds"
version = "0.2.1"

plugins {
    java
    id("com.github.ben-manes.versions") version "0.20.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.3"
}

setupApplication()
setupJava()
setupJacoco()
setupJunit()
setupJar()
setupGradleWrapper()

repositories {
    jcenter()
}

dependencies {
    implementation("org.apache.logging.log4j", "log4j-api", extra["version.log4j"].toString())
    implementation("org.apache.logging.log4j", "log4j-core", extra["version.log4j"].toString())
    implementation("org.javatuples", "javatuples", extra["version.javaTuples"].toString())
    implementation("com.nurkiewicz.asyncretry", "asyncretry", extra["version.asyncRetry"].toString())

    testImplementation("org.mockito", "mockito-core", extra["version.mockito"].toString())
    testImplementation("org.assertj", "assertj-core", extra["version.assertJ"].toString())
    testImplementation("org.junit.jupiter", "junit-jupiter-api", extra["version.junit"].toString())
    testImplementation("org.junit.jupiter", "junit-jupiter-params", extra["version.junit"].toString())
    testRuntime("org.junit.jupiter", "junit-jupiter-engine", extra["version.junit"].toString())
}
