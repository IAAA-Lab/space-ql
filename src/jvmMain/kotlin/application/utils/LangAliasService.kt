package application.utils

import org.springframework.stereotype.Service

// tag::main[]

@Service
class LangAliasService{
    val langAliases: Map<String, String> = mapOf(
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

}
// end::main[]
