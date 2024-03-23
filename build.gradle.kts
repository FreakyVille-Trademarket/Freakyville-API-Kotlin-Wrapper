plugins {
    kotlin("jvm") version "1.9.21"
    `maven-publish`
}

group = "dk.fvtrademarket"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.22")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dk.fvtrademarket"
            artifactId = "Freakyville-API-Kotlin-Wrapper"
            version = "0.0.1"

            from(components["java"])
        }
    }
}