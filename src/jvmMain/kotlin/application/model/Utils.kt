package application.model

fun getMDRecord(elsRecord : ElsMetadataRecord) : MetadataRecord {
    return MetadataRecord(
        ID = elsRecord.ID,
        title = elsRecord.title,
        description = elsRecord.description,
        primaryTopic = getMDPrimaryTopic(elsRecord.primaryTopic, elsRecord.type),
        type = elsRecord.type,
        details = getMDContent(elsRecord.details)
    )
}

fun getMDContent(elsDetails: ElsContentMetadata): ContentMetadata {
    return ContentMetadata(
        language = elsDetails.language,
        uploadDate = elsDetails.uploadDate,
        contactPoint = getMDContactPoint(elsDetails.contactPoint),
        accessUrl = elsDetails.accessUrl,
        distributionFormats = elsDetails.distributionFormats?.map{ Format(it.name, it.version) },
        distributionTransfers = elsDetails.distributionTransfers?.map{ Transfer(it.URL) }

    )
}

fun getMDContactPoint(elsContactPoint: ElsContactPoint): ContactPoint {
    return ContactPoint(
        individual = elsContactPoint.individual,
        phone = elsContactPoint.phone,
        name = elsContactPoint.name,
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
