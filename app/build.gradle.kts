plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotest)
    jacoco
}

android {
    namespace = "com.example.cupcake"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.cupcake"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    testOptions{
        unitTests {
            isReturnDefaultValues = true
            all {
                it.useJUnitPlatform()
            }
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.material)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.compose.material.icons.extended)

    // Kotest
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.coroutine.test)

    androidTestImplementation("androidx.navigation:navigation-testing:2.9.6")
}

jacoco {
    toolVersion = "0.8.14"
}
tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*"
    )
    val buildDirPath = buildDir.absolutePath
    val classes = fileTree(buildDirPath) {
        include(
            "intermediates/javac/debug/classes/**/*.class",
            "intermediates/kotlin/classes/debug/**/*.class",
            "tmp/kotlin-classes/debug/**/*.class",
            "classes/kotlin/debug/**/*.class"
        )
        exclude(excludes)
    }
//    val kotlinClasses = fileTree("${layout.buildDirectory}/tmp/kotlin-classes/debug") {
//        exclude(fileFilter)
//    }
//    val javaClasses = fileTree("${layout.buildDirectory}/intermediates/javac/debug/classes") {
//        exclude(fileFilter)
//    }
    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))
    classDirectories.setFrom(files( classes))
    executionData.setFrom(fileTree(buildDir) {
        include(
            "jacoco/testDebugUnitTest.exec",
            "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
        )
    })
}


