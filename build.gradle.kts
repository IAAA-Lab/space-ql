plugins {
    kotlin("multiplatform") version Versions.kotlin
    application
}

group = "space-ql"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(Ktor.serverNetty)
                implementation(Ktor.htmlBuilder)
                implementation(KotlinxHtml.jvm)
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(project.dependencies.enforcedPlatform(kotlinw("wrappers-bom:${Versions.kotlinWrappers}")))
                implementation(kotlinw("react"))
                implementation(kotlinw("react-dom"))
                implementation(kotlinw("styled"))
                implementation(kotlinw("react-router-dom"))
                implementation(kotlinw("redux"))
                implementation(kotlinw("react-redux"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

application {
    mainClass.set("me.javier.application.ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}