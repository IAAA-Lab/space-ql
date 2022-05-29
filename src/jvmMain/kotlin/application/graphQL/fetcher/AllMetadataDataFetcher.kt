package application.graphQL.fetcher

import application.BasicService
import application.model.MetadataPage
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
    ): MetadataPage = basicService.search(text, limit, offset, order)

}
