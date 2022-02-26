package application.graphQL.provider

import application.graphQL.fetcher.AllMetadataDataFetcher
import graphql.GraphQL
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeRuntimeWiring
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class GraphQLProvider(
    val allMetadataDataFetcher: AllMetadataDataFetcher,
    val metadataDataFetcher: AllMetadataDataFetcher,
    @Value("classpath:schema.graphql")
    val resource: Resource
) {

    @Bean
    fun graphQL(): GraphQL {
        val file = resource.file
        val typeDefinitionRegistry = SchemaParser().parse(file)
        val runtimeWiring = buildRuntimeWiring()
        val graphQLSchema = SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring)
        val graphQL = GraphQL.newGraphQL(graphQLSchema).build()
        return graphQL
    }

    private fun buildRuntimeWiring(): RuntimeWiring {
        val queryType = TypeRuntimeWiring.newTypeWiring("Query")
        queryType.dataFetcher("allMetadata", allMetadataDataFetcher)
            .dataFetcher("metadata", metadataDataFetcher)
        return RuntimeWiring.newRuntimeWiring().type(queryType).build()
    }
}