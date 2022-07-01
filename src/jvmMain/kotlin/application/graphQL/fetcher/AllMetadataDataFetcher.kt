package application.graphQL.fetcher

import application.BasicService
import application.model.MetadataPage
import application.model.MetadataRecord
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

// tag::fetcher[]
@DgsComponent // <1>
class AllMetadataDataFetcher(
    private val basicService: BasicService // <4>
){

    @DgsQuery // <2>
    fun search(
        @InputArgument text: String?, // <3>
        @InputArgument limit: Int,
        @InputArgument offset: Int,
        @InputArgument order: String,
        @InputArgument language: List<String>?,
        @InputArgument resType: List<String>?,
        @InputArgument related: List<String>?,
        @InputArgument contactPoints: List<String>?
    ): MetadataPage = basicService.search(text, limit, offset, order, language, resType, related, contactPoints)


    @DgsQuery
    fun getSuggestions(
        @InputArgument text: String
    ): List<String?> = basicService.suggest(text)

    @DgsQuery
    fun getRecord(
        @InputArgument id : String
    ) : MetadataRecord = basicService.getRecord(id)

}
// end::fetcher[]