package application.graphQL.mutator

import application.BasicService
import application.model.MetadataRecord
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class RelatedMutation(
    private val basicService: BasicService
) {
    @DgsMutation
    fun removeRelated(
        @InputArgument recordId : String,
        @InputArgument relatedId : String
    ) : MetadataRecord = basicService.removeRelated(recordId, relatedId)
}