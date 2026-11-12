import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplataformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

kotlin {
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    android {
        namespace = "com.grimco.gymapp.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
        androidResources {
            enable = true
        }
    }

    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ui.tooling)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.engine.android)
            implementation(libs.androidx.connect.client)
            implementation(libs.kotlinx.coroutines.play.services)

        }
        commonMain.dependencies {
            implementation(libs.runtime)
            implementation(libs.foundation)
            implementation(libs.material3)
            implementation(libs.ui)
            implementation(libs.components.resources)
            implementation(libs.ui.tooling.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.material.icons.core)
            implementation(libs.material.icons.extended)

            implementation(libs.jetbrains.nav3.runtime)
            implementation(libs.jetbrains.nav3.ui)
            implementation(libs.jetbrains.lifecycle.viewmodel.nav3)
            implementation(libs.jetbrains.lifecycle.viewmodel)
            implementation(libs.kotlinx.serialization.json)

            //coil
            implementation(libs.coil.compose)
            implementation(libs.coil.network)

            //koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)

            //room
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundle)

            implementation(libs.calf.file.picker)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        appleMain.dependencies {
            implementation(libs.ktor.engine.darwin)
        }
    }
}
room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid",libs.androidx.room.compiler)
    androidRuntimeClasspath(libs.ui.tooling.preview)
}

