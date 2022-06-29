package application.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Service")
data class cliService(
    override var id: String? = null,
    override var title: String? = null,
    var coupledDatasets: List<RelatedElements>? = null,
    var __typename: String = "Service"
) : Resource()

@Serializable
@SerialName("Service")
data class Service(
    override var id: String? = null,
    override var title: String? = null,
    var coupledDatasets: List<RelatedElements>? = null,
    var __typename: String = "Service",
    val type: String? = "Service"
) : Resource()