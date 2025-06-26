import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.6.0"
}

apply(plugin = "kotlin")
apply(plugin = "org.jetbrains.intellij.platform")

group = "io.github.bytebeats"
version = "1.4.0"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/google")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/central")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/jcenter")
    }
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    api(libs.bundles.jacksonCore)
    api(libs.bundles.graalJs)
    intellijPlatform {
        create("IC", "2024.2")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add necessary plugin dependencies for compilation here, example:
        // bundledPlugin("com.intellij.java")
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "242"
            untilBuild = "251.*"
        }

        changeNotes = """
      v1.3.6 project upgrade.<br>
      v1.4.0 regular upgrade with Java 21 and Idea 2025.<br>
    """.trimIndent()
    }

    pluginVerification {
        ides {
            ide(IntelliJPlatformType.IntellijIdeaCommunity, "2024.3.6")
            local(file(project.localProperty("plugin.verifier.ide.location")))
            recommended()
            select {
                types = listOf(IntelliJPlatformType.IntellijIdeaCommunity, IntelliJPlatformType.AndroidStudio)
                channels = listOf(ProductRelease.Channel.RELEASE)
                sinceBuild = "242"
                untilBuild = "251.*"
            }
        }
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_21.toString()
        targetCompatibility = JavaVersion.VERSION_21.toString()
    }
    withType<KotlinCompile> {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_21)
    }

    buildSearchableOptions {
        enabled = false
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    register<Copy>("MoveBuildArtifacts") {
        mustRunAfter("DeletePluginFiles")
        println("Moving Build Artifacts!")
        from(layout.buildDirectory.dir("distributions"))
        include("polyglot-$version.zip")
        into("plugins")
    }

    register<Delete>("DeletePluginFiles") {
        delete(files("plugins"))
    }
    named("signPlugin") {
        finalizedBy("MoveBuildArtifacts")
    }
}

fun Project.localProperty(key: String, from: String = "local.properties"): String {
    val properties = Properties()
    properties.load(FileInputStream(rootProject.file(from)))
    return properties.getProperty(key)
}
