package application

import application.model.MetaData
import com.jayway.jsonpath.JsonPath
import org.json.XML
import org.springframework.stereotype.Service
import java.io.File

@Service
class BasicService {

    // github.com/codeniko/JsonPathKt
    fun getTitle() : String {
        val text = BasicService::class.java.getResource("/data/rayos.xml").readText()
        val json = XML.toJSONObject(text)
        val jContext = JsonPath.parse(json.toString())
        val nombre : String = jContext.read("[\"gmd:MD_Metadata\"][\"gmd:identificationInfo\"][\"gmd:MD_DataIdentification\"][\"gmd:citation\"][\"gmd:CI_Citation\"][\"gmd:title\"][\"gco:CharacterString\"]")
        return nombre
    }

    fun getMetadata(): MetaData {
        // THIS IS A TEST IMPLEMENTATION
        val title = getTitle()
        return MetaData(title)

    }

    fun getAllMetadata(): List<MetaData> {
        val title = getTitle()
        return listOf(MetaData(title))
    }
}