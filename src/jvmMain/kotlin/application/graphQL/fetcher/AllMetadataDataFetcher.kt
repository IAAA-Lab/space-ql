package application.graphQL.fetcher

import application.BasicService
import application.model.MetaData
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class AllMetadataDataFetcher(
    private val basicService: BasicService
) : DataFetcher<List<MetaData>>{

    @Override
    override fun get(dataFetchingEnvironment: DataFetchingEnvironment?): List<MetaData> {
        return basicService.getAllMetadata()
    }
}