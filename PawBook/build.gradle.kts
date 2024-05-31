plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ksp) apply false
    id("de.jensklingenberg.ktorfit") version "1.13.0"
    kotlin("plugin.serialization") version "1.9.22"
}

buildscript {

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()

    }

    dependencies {
        //classpath("dev.icerock.moko:resources-generator:0.24.0-beta-1")

    }
}
