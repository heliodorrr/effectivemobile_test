// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }

}

plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    kotlin("kapt") version "1.7.20"
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}