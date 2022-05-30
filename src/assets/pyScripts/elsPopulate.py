from tokenize import String
from xml.sax.saxutils import unescape
import pandas as pd 
import json
import xmltodict
from elasticsearch import Elasticsearch, helpers
from jsonpath_ng import jsonpath, parse



#
# for k, v in dict.iteritems():
#   if v is xxx:
#       dict[k] = parser.unscape(v)
#   else: Otra cosa

# unscapet unscapes element by element

INDICES = {
    'records' : 'sql_records',
    'services' : 'sql_services',
    'datasets' : 'sql_datasets'
}


ALT_PATH = { 0 : '',
             1 : '"gmd:characterSet"."gmd:parentIdentifier".',
             2 : '"gmd:parentIdentifier".',
             3 : '"gmd:parentIdentifier"."gmd:hierarchyLevelName".'}
             
ALT_TYPE = { 0 : '"srv:SV_ServiceIdentification".',
             1 : '"gmd:MD_DataIdentification".'}
        
ALT_DATE = { 0 : '"gco:Date"',
             1 : '"gco:DateTime"'}

# JsonPath routes
JsonPathExpr_id = parse('$."gmd:MD_Metadata"."gmd:fileIdentifier"."gco:CharacterString"')
JsonPathExpr_language = parse('$."gmd:MD_Metadata"."gmd:language"."gmd:LanguageCode"')

# Replantear de forma que quede algo estilo "blablabla{0'}blablab" y al invocarlo se haga D[x].format(ALT1)
# Ventajas: Se puede usar un solo diccionario para todas las situaciones pero habrá que tener todas las situaciones
# bien delimitadas.
JsonPathExpr = {
'date' : '$."gmd:MD_Metadata".{0}"gmd:dateStamp".{1}',                                                
'scope' : '$."gmd:MD_Metadata".{0}"gmd:hierarchyLevel"."gmd:MD_ScopeCode"',

'file_name' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:citation"."gmd:CI_Citation"."gmd:title"."gco:CharacterString"',
'file_description' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:abstract"."gco:CharacterString"',
'contactPoint_individual' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:individualName"."gco:CharacterString"',
'contactPoint_phone' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:phone"."gmd:CI_Telephone"."gmd:voice"."gco:CharacterString"',

'contactPoint_name' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:organisationName"."gco:CharacterString"',
'contactPoint_mail' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:address"."gmd:CI_Address"."gmd:electronicMailAddress"."gco:CharacterString"',
'contactPoint_onlineSource' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:onlineResource"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"',
'accessUrl' : '$."gmd:MD_Metadata".{0}"gmd:distributionInfo"."gmd:MD_Distribution"."gmd:transferOptions"."gmd:MD_DigitalTransferOptions"."gmd:onLine"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"',

'distribution' : '$."gmd:MD_Metadata"."gmd:distributionInfo"."gmd:MD_Distribution"',
'format_Array' : '$."gmd:distributionFormat"[*]."gmd:MD_Format"',

'format_name_string' : '$."gmd:name"."gco:CharacterString"',
'format_anchor' : '$."gmd:name"."gmx:Anchor"',
'format_version' : '$."gmd:version"."gco:CharacterString"',
'transfer_Array' : '$."gmd:transferOptions"[*]."gmd:MD_DigitalTransferOptions"[*]."gmd:onLine"[*]."gmd:CI_OnlineResource"',
'transfer_URL' : '$."gmd:linkage"."gmd:URL"'
}

# Añadir excepción para '2021-04-207'
def getDatePathValue(jsonObj):
    expr = JsonPathExpr['date']
    dateInd = pathInd = 0

    formated_expr = expr.format(ALT_PATH[pathInd], ALT_DATE[dateInd])
    parsed_expr = parse(formated_expr)
    foundInExpr = parsed_expr.find(jsonObj)

    pathInd = pathInd + 1

    while len(foundInExpr) <= 0 and dateInd < len(ALT_DATE):
        while len(foundInExpr) <= 0 and pathInd < len(ALT_PATH):
            formated_expr = expr.format(ALT_PATH[pathInd], ALT_DATE[dateInd])
            parsed_expr = parse(formated_expr)

            foundInExpr = parsed_expr.find(jsonObj)
            pathInd = pathInd + 1
        pathInd = 0
        dateInd = dateInd + 1

    if(len(foundInExpr) <= 0):
        return ""
    else:
        ret_value = str(foundInExpr[0].value)
        if ret_value == "2021-04-207":
            return "2021-04-20"
        else: 
            return ret_value 

def getPathValue(field, jsonObj):
    expr = JsonPathExpr[field]
    typeInd = pathInd = 0

    formated_expr = expr.format(ALT_PATH[pathInd], ALT_TYPE[typeInd])
    parsed_expr = parse(formated_expr)
    foundInExpr = parsed_expr.find(jsonObj)

    pathInd = pathInd + 1

    while len(foundInExpr) <= 0 and typeInd < len(ALT_TYPE):
        while len(foundInExpr) <= 0 and pathInd < len(ALT_PATH):
            formated_expr = expr.format(ALT_PATH[pathInd], ALT_TYPE[typeInd])
            parsed_expr = parse(formated_expr)

            foundInExpr = parsed_expr.find(jsonObj)
            pathInd = pathInd + 1
        pathInd = 0
        typeInd = typeInd + 1

    if(len(foundInExpr) <= 0):
        return ""
    else:
        if field == 'scope':
            return foundInExpr[0].value.get('@codeListValue')
        else:
            return str(foundInExpr[0].value)    


def getDist(jsonObj):
    dist_expr = JsonPathExpr['distribution']
    fmt_expr = JsonPathExpr['format_Array']
    transfer_expr = JsonPathExpr['transfer_Array']

    parsed_dist_expr = parse(dist_expr)
    parsed_fmt_expr = parse(fmt_expr)
    parsed_transfer_expr = parse(transfer_expr)

    foundInExpr = parsed_dist_expr.find(jsonObj)

    jsonStrFound = json.dumps(foundInExpr[0].value)
    jsonObjFound = json.loads(jsonStrFound)
    
    formats = []
    transfers = []

    format_name_string = JsonPathExpr['format_name_string']
    format_anchor = JsonPathExpr['format_anchor']
    format_version = JsonPathExpr['format_version']
    

    parsed_format_name_string = parse(format_name_string)
    parsed_format_anchor = parse(format_anchor)
    parsed_format_version = parse(format_version)
    

    for match in parsed_fmt_expr.find(jsonObjFound):
        match_version = parsed_format_version.find(match.value)
        match_name = parsed_format_name_string.find(match.value)
        if(len(match_name) <= 0):
            match_name = parsed_format_anchor.find(match.value)
            match_name = match_name[0].value.get('#text')
        else:
            match_name = match_name[0].value

        if len(match_version) <= 0:
            match_version = '-'
        else:
            match_version = match_version[0].value

        formats.append(
            {
                    'name' : str(match_name),
                    'version' : str(match_version)
                }
        )
    

    transfer_URL = JsonPathExpr['transfer_URL']
    
    parsed_transfer_URL = parse(transfer_URL)

    for match in parsed_transfer_expr.find(jsonObjFound):
        match_url = parsed_transfer_URL.find(match.value)
        
        transfers.append(
            {
                    'URL' : str(match_url[0].value) 
                }
            
        )
    

    return {
        'formats' : formats,
        'transfers' : transfers
    }


# Find altrentative routes. There are some files that doesn't have the same routes so maybe something like "if(value) is null -> try alt routes"
# It can still get the available functionalities. They are a list of funcitons found as "containsOperations"
# I can maybe move the paths to a file or function that automatically does all the job instead of calling each "find" funciton inside the main body
metadataFile = pd.read_csv('../metadata.csv', encoding='ISO-8859-1', delimiter=',')
es = Elasticsearch(hosts='http://localhost:9200')

for index, row in metadataFile.iterrows():
    # fileID = str(row['id'])
    xmlRow = str(row['xml'])
    xmlObj = xmltodict.parse(xmlRow)
    jsonStr = json.dumps(xmlObj)
    jsonObj = json.loads(jsonStr)    
    
    value_ID = JsonPathExpr_id.find(jsonObj)[0].value
    value_language = JsonPathExpr_language.find(jsonObj)[0].value.get('#text')
    value_date = getDatePathValue(jsonObj)
    value_scope = getPathValue("scope", jsonObj)
    value_contactPoint_name = getPathValue("contactPoint_name", jsonObj)
    value_contactPoint_mail = getPathValue("contactPoint_mail", jsonObj)
    value_contactPoint_onlineSource = getPathValue("contactPoint_onlineSource", jsonObj)
    value_accessUrl = getPathValue("accessUrl", jsonObj)
    value_file_name = getPathValue("file_name", jsonObj)
    value_file_description = getPathValue("file_description", jsonObj)
    value_contactPoint_individual = getPathValue("contactPoint_individual", jsonObj)
    value_contactPoint_phone = getPathValue("contactPoint_phone", jsonObj)
    
    value_distribution = getDist(jsonObj)
    recordDoc = {
       'ID' : value_ID,
       'title' : str(value_file_name),
       'description' : str(value_file_description),
       'type' : str(value_scope),
       'details' : {
           'languages' : str(value_language),
           'uploadDate' : str(value_date),
           'contactPoint' : {
                'individual' : str(value_contactPoint_individual),
                'phone' : str(value_contactPoint_phone),
                'name' : str(value_contactPoint_name),
                'mail' : str(value_contactPoint_mail),
                'onlineSource' : str(value_contactPoint_onlineSource),
           },
           'distributionFormats' : value_distribution['formats'],
           'distributionTransfers' : value_distribution['transfers']
       },
       # Si se quiere añadir el documento original... pero son campos muy grandes
       # que no se van a utilizar ahora mismo, por lo que de momento se omite
       # 'document' : str(jsonStr)
    }


    # Load data to ElasticSearch
    es.index(index=INDICES['records'], id=recordDoc['ID'], document=recordDoc)
    
    print('RECORD ' + recordDoc['type'])
    print( recordDoc['type'] == 'service')
    if(recordDoc['type'] == 'service'):
        serviceDoc = {
            'title' : recordDoc['title'],
            'coupledDatasets' : []
        }
        es.index(index=INDICES['services'], id=recordDoc['ID'], document=serviceDoc)
    elif(recordDoc['type'] == 'dataset'):
        datasetDoc = {
            'title' : recordDoc['title'],
            'coupledServices' : []
        }
        es.index(index=INDICES['datasets'], id=recordDoc['ID'], document=datasetDoc)

es.indices.refresh(index=INDICES['records'])
es.indices.refresh(index=INDICES['services'])
es.indices.refresh(index=INDICES['datasets'])