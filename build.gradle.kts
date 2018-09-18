plugins {
    java
    application
    jacoco
    id("com.adarshr.test-logger") version "1.5.0"
    id("com.github.ben-manes.versions") version "0.20.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.3"
}

group = "io.lucasvalenteds"
version = "0.1.0"

repositories {
    jcenter()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-api:2.11.1")
    implementation("org.apache.logging.log4j:log4j-core:2.11.1")

    implementation("org.javatuples:javatuples:1.2")

    testImplementation("org.mockito:mockito-core:2.22.0")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.1")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

configure<ApplicationPluginConvention> {
    mainClassName = "$group.batch.Main"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

configure<JacocoPluginExtension> {
    toolVersion = "0.8.3-SNAPSHOT"
}

tasks.withType<JavaCompile> {
    options.compilerArgs = listOf("-proc:none")
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Class-Path" to configurations.compile.all.map(Configuration::getName).joinToString(" "),
            "Main-Class" to "$group.Main"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    failFast = true
    finalizedBy(tasks.withType<JacocoReport>())
}

tasks.withType<JacocoReport> {
    reports {
        csv.isEnabled = false
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.withType<Wrapper> {
    distributionType = Wrapper.DistributionType.ALL
}