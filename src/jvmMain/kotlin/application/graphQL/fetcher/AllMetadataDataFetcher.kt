package application.graphQL.fetcher

import application.BasicService
import application.model.MetadataPage
import application.model.MetadataRecord
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class AllMetadataDataFetcher(
    private val basicService: BasicService
){

    @DgsQuery
    fun search(
        @InputArgument text: String?,
        @InputArgument limit: Int,
        @InputArgument offset: Int,
        @InputArgument order: String,
        @InputArgument language: List<String>?,
        @InputArgument resType: List<String>?,
        @InputArgument related: List<String>?,
        @InputArgument contactPoints: List<String>?
    ): MetadataPage = basicService.search(text, limit, offset, order, language, resType, related, contactPoints)


    @DgsQuery
    fun getRecord(
        @InputArgument id : String
    ) : MetadataRecord = basicService.getRecord(id)

}
