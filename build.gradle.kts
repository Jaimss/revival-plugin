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
    implementation("net.kyori:adventure-api:4.11.0")
    implementation("net.kyori:adventure-platform-bukkit:4.1.1")

    testImplementation(kotlin("test"))
}

tasks.shadowJar {
    archiveClassifier.set("")

    // Relocations
    listOf(
        "net.kyori"
    ).forEach { libName -> relocate(libName, "${group}.libs.${libName}") }
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