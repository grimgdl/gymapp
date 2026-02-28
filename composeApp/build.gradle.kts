import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ui.tooling)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.engine.android)
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

android {
    namespace = "com.grimco.gymapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.grimco.gymapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 5
        versionName = "alpha-0.1.5"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {

        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
            ndk {
                debugSymbolLevel = "FULL"
            }

        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    ksp(libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

