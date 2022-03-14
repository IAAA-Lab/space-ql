package application

import application.model.MetaData
import application.model.MetadataElasticsearchRepository
import com.jayway.jsonpath.JsonPath
import org.json.XML
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.util.*

@Service
class BasicService(
    private val metadataRepository : MetadataElasticsearchRepository
) {

    // github.com/codeniko/JsonPathKt
    fun loadMD() {
        val text = ClassPathResource("/data/rayos.xml").file.readText()
        val json = XML.toJSONObject(text)
        val jContext = JsonPath.parse(json.toString())

        val title = jContext.read<String>("""["gmd:MD_Metadata"]["gmd:identificationInfo"]["gmd:MD_DataIdentification"]["gmd:citation"]["gmd:CI_Citation"]["gmd:title"]["gco:CharacterString"]""")

        val metadata = MetaData(UUID.randomUUID(), title, text, " a " )
        metadataRepository.save(metadata)
    }

    fun getMetadata(): List<MetaData> {
        // THIS IS A TEST IMPLEMENTATION
        val found = metadataRepository.findAll()

        return found.toList()
    }
}