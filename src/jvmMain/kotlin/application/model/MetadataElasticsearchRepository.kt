package application.model

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import java.util.*

interface MetadataElasticsearchRepository: ElasticsearchRepository<MetaData, UUID>