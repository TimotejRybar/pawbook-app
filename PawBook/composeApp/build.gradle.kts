
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.ksp)
    id("de.jensklingenberg.ktorfit")
    kotlin("plugin.serialization") version "1.9.22"
    id("kotlinx-serialization")
    alias(libs.plugins.room)
    alias(libs.plugins.sqldelight)
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

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.logging)
            implementation(libs.ktor.okhttp)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.purchases)
            implementation(libs.purchases.ui)
            implementation(libs.androidx.room.paging)
            implementation(libs.sqldelight.android.driver)
            //implementation("com.facebook.android:facebook-login:17.0.0")
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
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.koin.core)
            implementation(libs.ktor.core)
            implementation(libs.koin.compose)
            implementation(libs.ktorfit.lib)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.no.arg)
            implementation(libs.androidx.room.runtime)
            implementation(libs.calf.file.picker)
            implementation(libs.kamel.image)
            implementation(libs.ktor.core)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
            implementation(libs.coil.network.ktor)
            implementation(libs.font.awesome)
            implementation("io.github.thechance101:chart:Beta-0.0.5")
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
        androidTestDebugImplementation(libs.androidx.ui.test.manifest)
        androidTestImplementation(libs.androidx.ui.test.junit4)
        androidTestImplementation(libs.androidx.test.junit)
        androidTestImplementation(libs.androidx.espresso.core)

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


