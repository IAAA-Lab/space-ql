plugins {
    kotlin("multiplatform") version Versions.kotlin
    kotlin("plugin.spring") version Versions.kotlin
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version Versions.springDependencyManagement
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
        browser {
            binaries.executable()
            commonWebpackConfig {
                cssSupport.enabled = true
                outputPath = File(buildDir, "processedResources/jvm/main/static")
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
                implementation(kotlin("reflect"))
                implementation(kotlin("stdlib-jdk8"))

                implementation(OrgJson.json)

                implementation(JsonPath.jsonPath)

                implementation(Kotlinx.htmlJvm)
                implementation(Kotlinx.coroutinesReactor)

                implementation(SpringBootStarter.webflux)

                implementation(SpringBootStarter.elasticsearch)

                implementation("org.springframework.boot:spring-boot-starter-web")

                implementation(Dgs.graphSpringBootStarter)
                implementation(project.dependencies.enforcedPlatform(Dgs.graphPlatformDependencies))

                implementation(SpringDependencies.jacksonModuleKotlin)
                implementation(SpringDependencies.reactorKotlinExtensions)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(SpringBootStarter.test)
                implementation(SpringDependencies.reactorTest)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(project.dependencies.enforcedPlatform(kotlinw("wrappers-bom:${Versions.kotlinWrappers}")))
                implementation(kotlinw("react"))
                implementation(kotlinw("react-dom"))
                implementation(kotlinw("styled"))
                implementation(kotlinw("react-router-dom"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
                implementation("io.ktor:ktor-client-core:1.6.7")
                implementation("io.ktor:ktor-client-js:1.6.7")
                implementation("io.ktor:ktor-client-json:1.6.7")
                implementation("io.ktor:ktor-client-serialization:1.6.7")
                //implementation(npm("@types/jquery","3.5.1", generateExternals = true))
                //implementation(npm("jquery","3.5.1"))
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
    mainClass.set("application.ServerApplicationKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}