package application.model

import application.utils.CpAliasService
import application.utils.FmtAliasService
import application.utils.LangAliasService

@org.springframework.stereotype.Service
class ElsToModelService(
    private val langAliasService: LangAliasService,
    private val fmtAliasService: FmtAliasService,
    private val cpAliasService: CpAliasService
){

    // tag::functions[]
    fun getMDRecord(elsRecord : ElsMetadataRecord) : MetadataRecord {
        return MetadataRecord(
            ID = elsRecord.ID,
            title = elsRecord.title,
            description = elsRecord.description,
            primaryTopic = getMDPrimaryTopic(elsRecord.primaryTopic, elsRecord.type), // <1>
            type = elsRecord.type,
            details = getMDContent(elsRecord.details)  // <1>
        )
    }

    fun getMDContent(elsDetails: ElsContentMetadata): ContentMetadata {
        return ContentMetadata(
            language = langAliasService.getLangAlias(elsDetails.language),  // <2>
            uploadDate = elsDetails.uploadDate,
            contactPoint = getMDContactPoint(elsDetails.contactPoint), // <1>
            accessUrl = elsDetails.accessUrl,
            distributionFormats = elsDetails.distributionFormats?.map{ Format(fmtAliasService.getFormatAlias(it.name), it.version) },
            distributionTransfers = elsDetails.distributionTransfers?.map{ Transfer(it.URL) }
        )
    }

    fun getMDContactPoint(elsContactPoint: ElsContactPoint): ContactPoint {
        return ContactPoint(
            individual = elsContactPoint.individual,
            phone = elsContactPoint.phone,
            organization = cpAliasService.getCpAlias(elsContactPoint.name),  // <2>
            mail = elsContactPoint.mail,
            onlineSource = elsContactPoint.onlineSource
        )
    }

    fun getMDPrimaryTopic(primaryTopic: Any?, type: String): Resource? {
        if(primaryTopic != null) {
            return when (type) {
                "service" -> {
                    val servicePT = primaryTopic as ElsService
                    Service(
                        id = servicePT.id,
                        title = servicePT.title,
                        coupledDatasets = servicePT.coupledDatasets.map{ RelatedElements(it.related, getMDRecord(it.relatedRecord)) }
                    )
                }
                "dataset" -> {
                    val datasetPT = primaryTopic as ElsDataset
                    Dataset(
                        id = datasetPT.id,
                        title = datasetPT.title,
                        coupledServices = datasetPT.coupledServices.map{ RelatedElements(it.related, getMDRecord(it.relatedRecord)) }
                    )
                }
                else -> null
            }
        } else{
            return null
        }
    }
    // end::functions[]
}
