package application.graphQL.fetcher

import application.BasicService
import application.model.MetaData
import application.model.MetaDataPage
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class AllMetadataDataFetcher(
    private val basicService: BasicService
){

    @DgsQuery
    fun allMetadata(@InputArgument limit: Int, @InputArgument offset: Int): MetaDataPage = basicService.getMetadata(limit, offset)

    @DgsQuery
    fun search(@InputArgument text: String?, @InputArgument limit: Int, @InputArgument offset: Int): MetaDataPage = text?.let {
        basicService.search(it, limit, offset)
    } ?: basicService.getMetadata(limit, offset)

}
