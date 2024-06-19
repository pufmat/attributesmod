plugins {
	id("dev.architectury.loom")
	id("checkstyle")
}

repositories {
	maven(url = "https://maven.neoforged.net/releases/")
}

base.archivesName.set("${project.properties["archives_base_name"]}")
version = "${project.properties["mod_version"]}-${project.properties["minecraft_version"]}-neoforge"
group = "${project.properties["maven_group"]}"

evaluationDependsOn(":Common")

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
	minecraft("com.mojang:minecraft:${project.properties["minecraft_version"]}")
	mappings(loom.layered {
		mappings("net.fabricmc:yarn:${project.properties["yarn_mappings"]}:v2")
		mappings("dev.architectury:yarn-mappings-patch-neoforge:${project.properties["yarn_mappings_patch"]}")
	})

	neoForge("net.neoforged:neoforge:${project.properties["neoforge_version"]}")

	implementation(project(path = ":Common", configuration = "namedElements"))
}

loom {
	mixin.useLegacyMixinAp = false
}

tasks.check {
	dependsOn(project(":Common").tasks.check)
}

tasks.processResources {
	from(project(":Common").sourceSets.main.get().resources)

	inputs.property("version", project.properties["mod_version"])
	filesMatching("META-INF/neoforge.mods.toml") {
		expand(mapOf("version" to project.properties["mod_version"]))
	}
}

tasks.compileJava {
	source(project(":Common").sourceSets.main.get().java)
}