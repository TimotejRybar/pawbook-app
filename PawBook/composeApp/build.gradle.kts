import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    //id("dev.icerock.mobile.multiplatform-resources")
    alias(libs.plugins.ksp)
    id("de.jensklingenberg.ktorfit")
    kotlin("plugin.serialization") version "1.9.22"
    id("kotlinx-serialization")
    alias(libs.plugins.room)
    alias(libs.plugins.sqldelight) //Plugin of SQLDelight.
}



kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                verbose = true
                jvmTarget = "11"
                freeCompilerArgs += "-Xexpect-actual-classes"
            }
        }
    }
    androidTarget()

    task("testClasses")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
            binaryOption("bundleId", "sk.uplab.pawbook.shared")
        }
    }

    sourceSets {

        /*
        getByName("androidMain").dependsOn(commonMain.get())
        getByName("iosArm64Main").dependsOn(commonMain.get())
        getByName("iosX64Main").dependsOn(commonMain.get())
        getByName("iosSimulatorArm64Main").dependsOn(commonMain.get())
        */



        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation("io.ktor:ktor-client-android:2.3.10")
            implementation("io.ktor:ktor-client-json:2.3.10")
            implementation("io.ktor:ktor-client-serialization:2.3.10")
            implementation("io.ktor:ktor-client-logging:2.3.10")
            implementation("io.ktor:ktor-client-okhttp:2.3.10")
            implementation("io.insert-koin:koin-android:3.5.6")
            implementation("io.insert-koin:koin-androidx-compose:3.5.6")
            implementation(libs.compose.ui.tooling.preview)
            implementation("com.revenuecat.purchases:purchases:7.5.2")
            implementation("com.revenuecat.purchases:purchases-ui:7.5.2")
            implementation(libs.androidx.room.paging)
            implementation(libs.sqldelight.android.driver)
        }


        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.ktor.core)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.negotiation)
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha01")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")
            implementation("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
            implementation("io.insert-koin:koin-core:3.5.6")
            implementation(libs.ktor.core)
            implementation("io.insert-koin:koin-compose:1.1.5")
            implementation("de.jensklingenberg.ktorfit:ktorfit-lib:1.13.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            implementation("com.russhwolf:multiplatform-settings:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
            implementation(libs.androidx.room.runtime)
            implementation("androidx.sqlite:sqlite-framework:2.5.0-alpha02")
            implementation("androidx.sqlite:sqlite:2.5.0-alpha02")
            //implementation(libs.sqldelight.coroutines.extensions)
            implementation("com.mohamedrejeb.calf:calf-file-picker:0.4.0")

        }

        sqldelight {
            databases {
                create("Pawbook") {
                    packageName.set("sk.uplab.pawbook")
                }
            }
            linkSqlite.set(true)
        }


        iosMain.dependencies {
            implementation(libs.ktor.ios)
            implementation(libs.sqldelight.ios.driver)
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(libs.kotest.framework.engine)
                implementation(libs.kotest.assertions.core)
                implementation(libs.kotest.property)
                implementation(libs.ktor.mock)
                implementation(libs.ktor.negotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.coroutines.test)
                implementation(libs.turbine.turbine)

                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.uiTest)
                //implementation(libs.sqldelight.ios.driver)

            }
        }
    }
}



android {
    namespace = "sk.uplab.pawbook"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    //sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].res.srcDirs("src/commonMain/resources", "src/androidMain/resources")
    sourceSets["test"].java.srcDirs("src/commonTest/kotlin")

    defaultConfig {
        applicationId = "sk.uplab.pawbook"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    packaging {

        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    dependencies {

        add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:1.13.0")
        add("kspAndroid","de.jensklingenberg.ktorfit:ktorfit-ksp:1.13.0")
        add("kspIosSimulatorArm64", "de.jensklingenberg.ktorfit:ktorfit-ksp:1.13.0")
        add("kspIosX64", "de.jensklingenberg.ktorfit:ktorfit-ksp:1.13.0")
        add("kspIosArm64", "de.jensklingenberg.ktorfit:ktorfit-ksp:1.13.0")


        debugImplementation(libs.compose.ui.tooling)
        //commonMainApi("dev.icerock.moko:resources-compose:0.24.0-alpha-4")
        //commonTestImplementation("dev.icerock.moko:resources-test:0.24.0-alpha-4")
        androidTestDebugImplementation("androidx.compose.ui:ui-test-manifest:1.6.7")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.7")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    }

}
dependencies {
    testImplementation("junit:junit:4.13.2")
    implementation(libs.androidx.junit.ktx)
    testImplementation("org.testng:testng:6.9.6")
    commonTestImplementation("junit:junit:4.13.2")

    val ktorfitVersion = "1.13.0"
    add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspAndroid","de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)

}

room {
    schemaDirectory("$projectDir/schemas")
}

/*
multiplatformResources {
   resourcesPackage = "sk.uplab.pawbook" // required
   resourcesClassName = "MR" // optional, default MR
   resourcesVisibility = MRVisibility.Internal
}
*/


