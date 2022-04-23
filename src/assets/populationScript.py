from tokenize import String
import pandas as pd 
import os
import csv
import requests
import json
import xmltodict
from elasticsearch import Elasticsearch, helpers
from jsonpath_ng import jsonpath, parse



#
# for k, v in dict.iteritems():
#   if v is xxx:
#       dict[k] = parser.unscape(v)
#   else: Otra cosa


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
'scope' : '$."gmd:MD_Metadata".{0}"gmd:hierarchyLevelName"."gco:CharacterString"',
'standard' : '$."gmd:MD_Metadata".{0}"gmd:metadataStandardName"."gco:CharacterString"',
'standardVersion' : '$."gmd:MD_Metadata".{0}"gmd:metadataStandardVersion"."gco:CharacterString"',

'file_name' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:citation"."gmd:CI_Citation"."gmd:title"."gco:CharacterString"',
'file_description' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:abstract"."gco:CharacterString"',
'contactPoint_individual' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:individualName"."gco:CharacterString"',
'contactPoint_phone' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:phone"."gmd:CI_Telephone"."gmd:voice"."gco:CharacterString"',

'contactPoint_name' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:organisationName"."gco:CharacterString"',
'contactPoint_mail' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:address"."gmd:CI_Address"."gmd:electronicMailAddress"."gco:CharacterString"',
'contactPoint_onlineSource' : '$."gmd:MD_Metadata".{0}"gmd:identificationInfo".{1}"gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:onlineResource"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"',
'accessUrl' : '$."gmd:MD_Metadata".{0}"gmd:distributionInfo"."gmd:MD_Distribution"."gmd:transferOptions"."gmd:MD_DigitalTransferOptions"."gmd:onLine"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"',
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
        return str(foundInExpr[0].value)    

# Find altrentative routes. There are some files that doesn't have the same routes so maybe something like "if(value) is null -> try alt routes"
# It can still get the available functionalities. They are a list of funcitons found as "containsOperations"
# I can maybe move the paths to a file or function that automatically does all the job instead of calling each "find" funciton inside the main body


metadataFile = pd.read_csv('./metadata.csv', encoding='ISO-8859-1', delimiter=',')
es = Elasticsearch(hosts='http://localhost:9200')

for index, row in metadataFile.iterrows():
    # fileID = str(row['id'])
    xmlRow = str(row['xml'])
    xmlObj = xmltodict.parse(xmlRow)
    jsonStr = json.dumps(xmlObj)
    jsonObj = json.loads(jsonStr)
    
    
    value_ID = JsonPathExpr_id.find(jsonObj)[0].value
    value_language = JsonPathExpr_language.find(jsonObj)[0].value
    value_date = getDatePathValue(jsonObj)
    value_scope = getPathValue("scope", jsonObj)
    value_standard = getPathValue("standard", jsonObj)
    value_standardVersion = getPathValue("standardVersion", jsonObj)
    value_contactPoint_name = getPathValue("contactPoint_name", jsonObj)
    value_contactPoint_mail = getPathValue("contactPoint_mail", jsonObj)
    value_contactPoint_onlineSource = getPathValue("contactPoint_onlineSource", jsonObj)
    value_accessUrl = getPathValue("accessUrl", jsonObj)
    value_file_name = getPathValue("file_name", jsonObj)
    value_file_description = getPathValue("file_description", jsonObj)
    value_contactPoint_individual = getPathValue("contactPoint_individual", jsonObj)
    value_contactPoint_phone = getPathValue("contactPoint_phone", jsonObj)
    

    doc = {
        'ID' : value_ID,
        'data' : {
            'language' : str(value_language),
            'uploadDate' : str(value_date),
            'scope' : str(value_scope),
            'standard' : str(value_standard),
            'standardVersion' : str(value_standardVersion),
            'fileName' : str(value_file_name),
            'fileDescription' : str(value_file_description),
            'contactPointIndividual' : str(value_contactPoint_individual),
            'contactPointPhone' : str(value_contactPoint_phone),
            'contactPointName' : str(value_contactPoint_name),
            'contactPointMail' : str(value_contactPoint_mail),
            'contactPointOnlineSource' : str(value_contactPoint_onlineSource),
            'accessUrl' : str(value_accessUrl),
        },
        'document' : str(jsonStr)

    }

    # Load data to ElasticSearch
    es.index(index='test-values', id=doc['ID'], document=doc)

es.indices.refresh(index="test-values")