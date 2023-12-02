plugins {
    id("java")
    id("io.freefair.lombok") version "6.6"
    kotlin("jvm") version "1.9.21"
}

group = "org.mzuri"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.+")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}