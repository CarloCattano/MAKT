import kotlin.collections.addAll

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinxSerialization)
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" && isArm64 -> macosArm64("native")
        hostOs == "Mac OS X" && !isArm64 -> macosX64("native")
        hostOs == "Linux" && isArm64 -> linuxArm64("native")
        hostOs == "Linux" && !isArm64 -> linuxX64("native") {
            compilations.getByName("main") {
                cinterops.create("miniaudio") {
                    defFile(project.file("src/nativeInterop/cinterop/miniaudio.def"))
                }
            }

        }
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
                val miniaudioDir = "${projectDir}/miniaudio/build"
                linkerOpts.addAll(listOf(
                    "-L$miniaudioDir",
                    "-lminiaudio",
                    "-lpthread",
                    "-lm",
                    "-ldl",
                    "-latomic"
                ))
            }
        }
    }

    sourceSets {
        nativeMain.dependencies {
            implementation(libs.kotlinxSerializationJson)
            implementation("com.squareup.okio:okio:3.9.0")
        }
    }


    val releaseExeDir = layout.buildDirectory.dir("bin/native/releaseExecutable")

    tasks.register<Copy>("copyReleaseResources") {
        from("src/resources")
        into(releaseExeDir)
    }

    tasks.named("linkReleaseExecutableNative") {
        finalizedBy("copyReleaseResources")
    }


}
