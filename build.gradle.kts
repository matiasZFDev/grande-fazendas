import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow").version("6.0.0")
}

val projectName = "${project.name}.jar"
group = "dev.post.plugin"
version = ""

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://maven.citizensnpcs.co/repo") }
    maven { url = uri("https://maven.enginehub.org/repo/") }
}

dependencies {
    val postLibFiles = files("/home/aganac/BukkitDev/PostLib/PostLib.jar")
    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:3.+")
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly(files("/home/aganac/BukkitDev/spigot.jar"))
    compileOnly(files("/home/aganac/BukkitDev/GrandeCash.jar"))
    compileOnly(files("/home/aganac/BukkitDev/FAWE.jar"))
    compileOnly(files("/home/aganac/BukkitDev/WorldEdit.jar"))
    compileOnly("net.citizensnpcs:citizensapi:2.0.24-SNAPSHOT") {
        exclude(module = "bukkit")
        exclude(module = "libby-bukkit")
    }
    compileOnly(postLibFiles)
    testCompileOnly(postLibFiles)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.shadowJar {
    archiveFileName.set(projectName)
}

task("shadowAndCopy") {
    group = "Build"
    description = "Copies the jar into the location of the OUTPUT_PATH variable"
    dependsOn("shadowJar")

    doLast {
        val copyTask = tasks.create("copyTaskExec", Copy::class) {
            val dest = System.getenv("OUTPUT_PATH") ?: throw GradleException(
                "OUTPUT_PATH environment variable not set"
            )

            from(layout.buildDirectory.dir("libs"))
            into(dest)
        }
        val deleteTask = tasks.create("deleteTaskExec", Delete::class) {
            val fileDir = System.getenv("OUTPUT_PATH") ?: throw GradleException(
                "OUTPUT_PATH environment variable not set"
            )
            val filePath = "$fileDir/$projectName"

            delete(filePath)
        }
        deleteTask.actions[0].execute(deleteTask)
        copyTask.actions[0].execute(copyTask)
    }
}
