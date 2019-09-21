import org.gradle.api.tasks.testing.logging.TestLogEvent

group = "io.lucasvalenteds"
version = "0.3.0"

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

repositories {
    jcenter()
}

dependencies {
    implementation("io.reactivex.rxjava3", "rxjava", properties["version.rxjava"].toString())
    implementation("org.apache.logging.log4j", "log4j-api", properties["version.log4j"].toString())
    implementation("org.apache.logging.log4j", "log4j-core", properties["version.log4j"].toString())
    implementation("org.javatuples", "javatuples", properties["version.javatuples"].toString())
    testImplementation("org.junit.jupiter", "junit-jupiter", properties["version.junit"].toString())
}

configure<ApplicationPluginConvention> {
    mainClassName = "io.lucasvalenteds.das.Main"
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

