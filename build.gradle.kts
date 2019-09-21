import org.gradle.api.tasks.testing.logging.TestLogEvent

group = "io.lucasvalenteds"
version = "0.2.1"

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

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

configure<ApplicationPluginConvention> {
    mainClassName = "io.lucasvalenteds.batch.Main"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    }
}

