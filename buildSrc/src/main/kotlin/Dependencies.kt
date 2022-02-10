object Ktor {
    const val serverNetty = "io.ktor:ktor-server-netty:${Versions.ktor}"
    const val htmlBuilder = "io.ktor:ktor-html-builder:${Versions.ktor}"
}
object KotlinxHtml {
    const val jvm = "org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2"
}

fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

