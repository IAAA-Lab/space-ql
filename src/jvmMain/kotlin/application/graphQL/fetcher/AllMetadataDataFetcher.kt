package application.graphQL.fetcher

import application.BasicService
import application.model.MetaData
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class AllMetadataDataFetcher(
    private val basicService: BasicService
){

    @DgsQuery
    fun allMetadata(): List<MetaData> {
        return basicService.getMetadata()
    }

    @DgsQuery
    fun search(@InputArgument text: String): List<MetaData> {
        return basicService.search(text)
    }


}