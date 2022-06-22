package application.graphQL.mutator

import application.BasicService
import application.model.MetadataRecord
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument

// tag::mutator[]
@DgsComponent
class RelatedMutation(
    private val basicService: BasicService
) {
    @DgsMutation   // <1>
    fun removeRelated(
        @InputArgument recordId : String,
        @InputArgument relatedId : String
    ) : MetadataRecord = basicService.removeRelated(recordId, relatedId)
}
// end::mutator[]