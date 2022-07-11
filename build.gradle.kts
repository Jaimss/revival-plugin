import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.jaims.revivalplugin"
version = "0.1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    testImplementation(kotlin("test"))
}

tasks.shadowJar {
    archiveClassifier.set("")
}

tasks.processResources {
    with(copySpec {
        from("src/main/resources")
        expand("version" to project.version)
    })
    duplicatesStrategy = DuplicatesStrategy.WARN
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.build {
    dependsOn(tasks.shadowJar)
}