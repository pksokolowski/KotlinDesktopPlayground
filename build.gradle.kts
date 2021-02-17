import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.20"
    id("org.jetbrains.compose") version "0.2.0-build132"
}

group = "com.github.pksokolowski"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    val koinVersion = "2.2.2"
    val coroutinesVersion = "1.4.2"

    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.koin:koin-core:$koinVersion")
    implementation("org.jetbrains.exposed", "exposed-core", "0.29.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.29.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.29.1")
    implementation("org.xerial", "sqlite-jdbc", "3.34.0")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Desk"
        }
    }
}