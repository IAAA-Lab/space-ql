package application.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Dataset")
data class cliDataset(
    override var id: String? = null,
    override var title: String? = null,
    var coupledServices: List<RelatedElements>? = null,
    val __typename: String = "Dataset"
) : Resource()

@Serializable
@SerialName("Dataset")
data class Dataset(
    override var id: String? = null,
    override var title: String? = null,
    var coupledServices: List<RelatedElements>? = null,
    val __typename: String = "Dataset",
    val type: String? = "Dataset"
) : Resource()