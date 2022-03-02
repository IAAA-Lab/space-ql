package application.graphQL.fetcher

import application.BasicService
import application.model.MetaData
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class AllMetadataDataFetcher(
    private val basicService: BasicService
){

    @DgsQuery
    fun allMetadata(): List<MetaData> {
        return basicService.getMetadata()
    }
}