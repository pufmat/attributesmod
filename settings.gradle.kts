pluginManagement {
    repositories {
        maven(url = "https://maven.fabricmc.net/")
        maven(url = "https://maven.neoforged.net/releases/")
        maven(url = "https://maven.architectury.dev/")
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("dev.architectury.loom") version "1.6.397" apply false
}

rootProject.name = "Pufferfish's Attributes"

include("Common", "Fabric", "NeoForge")
