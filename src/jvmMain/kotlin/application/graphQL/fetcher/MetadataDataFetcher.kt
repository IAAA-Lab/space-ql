package application.graphQL.fetcher

import application.BasicService
import application.model.MetaData
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class MetadataDataFetcher(private val basicService: BasicService) : DataFetcher<MetaData>{

    @Override
    override fun get(dataFetchingEnvironment: DataFetchingEnvironment?): MetaData {
        // val id = dataFetchingEnvironment!!.getArgument<String>("id")
        return basicService.getMetadata()
    }
}