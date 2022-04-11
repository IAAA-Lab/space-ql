
fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

fun kotlinx(target: String, version: String = Versions.kotlin): String =
    "org.jetbrains.kotlinx:kotlinx-$target:$version"

object Kotlinx {
    val coroutinesReactor = kotlinx("coroutines-reactor", Versions.kotlinxCoroutines)
    val htmlJvm = kotlinx("html-jvm", Versions.kotlinxHtml)
}

object Kotlin {
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
}

fun springBootStarter(target: String): String = "org.springframework.boot:spring-boot-starter-$target"

object SpringBootStarter {
    val webflux = springBootStarter("webflux")
    var test = springBootStarter("test")
    val elasticsearch = springBootStarter("data-elasticsearch")
}

object OrgJson {
    const val json = "org.json:json:${Versions.orgJson}"
}

object JsonPath {
    const val jsonPath = "com.jayway.jsonpath:json-path:${Versions.jsonPath}"
}

object SpringDependencies {
    const val jacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val reactorKotlinExtensions = "io.projectreactor.kotlin:reactor-kotlin-extensions"
    const val reactorTest = "io.projectreactor:reactor-test"
}

object GraphQL {
    const val graphSpringStarter = "com.graphql-java:graphql-spring-boot-starter:${Versions.graphSpringStarter}"
    const val graphJavaTool = "com.graphql-java:graphql-java-tools:${Versions.graphJTool}"
}

object Dgs {
    const val graphPlatformDependencies = "com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"
    const val graphSpringBootStarter = "com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter"
}
