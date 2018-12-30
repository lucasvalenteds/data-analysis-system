plugins {
    java
    kotlin("jvm") version embeddedKotlinVersion
}

repositories {
    gradlePluginPortal()
    jcenter()
}

dependencies {
    compile(gradleKotlinDsl())
    compile("com.github.jengelman.gradle.plugins", "shadow", "4.0.3")
    compile("com.adarshr", "gradle-test-logger-plugin", "1.6.0")
}
