package application.aliases

val langAliases = mapOf(
    "English" to "English",
    "eng" to "English",
    "spa" to "Spanish",
    "Español" to "Spanish",
    "None" to "Other/Unknown",
    "Spanish; Castilian" to "Spanish"
)

fun getLangAlias(lang : String?) : String {
    return langAliases[lang] ?: "Other/Unknown"
}