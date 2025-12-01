import com.google.gson.Gson

plugins {
    id("dev.architectury.loom")
    id("checkstyle")
}

base.archivesName.set("${project.properties["archives_base_name"]}")
version = "${project.properties["mod_version"]}-${project.properties["minecraft_version"]}-common"
group = "${project.properties["maven_group"]}"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    withSourcesJar()
}

dependencies {
    minecraft("com.mojang:minecraft:${project.properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${project.properties["yarn_mappings"]}:v2")

    compileOnly("net.fabricmc:sponge-mixin:${project.properties["mixin_version"]}")
    compileOnly("io.github.llamalad7:mixinextras-common:${project.properties["mixinextras_version"]}")
}

sourceSets.main {
	java.srcDir(layout.buildDirectory.dir("generated/attributes"))
}

tasks.jar {
    from(project.rootDir.resolve("LICENSE.txt"))

    manifest {
        attributes["Fabric-Loom-Remap"] = "true"
    }
}

tasks.register<Sync>("generateAttributes") {
    data class Tag(val values: List<String>)

	val json = "src/main/resources/data/puffish_attributes/tags/attribute/dynamic.json"

    var tag = Gson().fromJson(
	    layout.projectDirectory.file(json).asFile.reader(),
	    Tag::class.java
	)

	val map = mapOf<String, (String) -> String>(
		"attribute" to { attribute -> attribute.lowercase() },
		"ATTRIBUTE" to { attribute -> attribute.uppercase() }
	)

	inputs.file(json)
	includeEmptyDirs = false

    from(layout.projectDirectory.dir("src/main/java/"))
    into(layout.buildDirectory.dir("generated/attributes/"))
    include("**/*.template")
    rename("(.*).template", "$1")
    filter { line ->
        if (map.keys.none { line.contains("\${$it}") }) {
            line
        } else {
            tag.values
                .map { it.substringAfter(":") }
                .joinToString("\n") {
                    map.entries.fold(line) { state, entry ->
                        state.replace("\${${entry.key}}", entry.value(it))
                    }
                }
        }
    }
}

tasks.compileJava {
	dependsOn(tasks.named("generateAttributes"))
}

tasks.named("sourcesJar") {
	dependsOn(tasks.named("generateAttributes"))
}