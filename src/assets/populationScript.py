from tokenize import String
import pandas as pd 
import os
import csv
import requests
import json
import xmltodict
from elasticsearch import Elasticsearch
from jsonpath_ng import jsonpath, parse

ALT_PATH = { 0 : '',
             1 : '"gmd:characterSet"."gmd:parentIdentifier".',
             2 : '"gmd:parentIdentifier".',
             3 : '"gmd:parentIdentifier"."gmd:hierarchyLevelName".'}
             
ALT_TYPE = { 0 : '"srv:SV_ServiceIdentification".',
             1 : '"gmd:MD_DataIdentification".'}

# JsonPath routes
JsonPathExpr_id = parse('$."gmd:MD_Metadata"."gmd:fileIdentifier"."gco:CharacterString"')
JsonPathExpr_language = parse('$."gmd:MD_Metadata"."gmd:language"."gmd:LanguageCode"')

# Replantear de forma que quede algo estilo "blablabla{0'}blablab" y al invocarlo se haga D[x].format(ALT1)
# Ventajas: Se puede usar un solo diccionario para todas las situaciones pero habrá que tener todas las situaciones
# bien delimitadas.
JsonPathExpr = {
'date' : '$."gmd:MD_Metadata".{0}"gmd:dateStamp"."gco:Date"',
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


def getPathValue(field, jsonObj):
    #1: buscar por defecto
    #2: mientras no encuentre y no se haya buscado en todas las opciones posibles...
    
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
        return foundInExpr[0].value    

# Find altrentative routes. There are some files that doesn't have the same routes so maybe something like "if(value) is null -> try alt routes"
# It can still get the available functionalities. They are a list of funcitons found as "containsOperations"
# I can maybe move the paths to a file or function that automatically does all the job instead of calling each "find" funciton inside the main body


metadataFile = pd.read_csv('./metadata.csv', encoding='ISO-8859-1', delimiter=',')

for index, row in metadataFile.iterrows():
    # fileID = str(row['id'])
    xmlRow = str(row['xml'])
    xmlObj = xmltodict.parse(xmlRow)
    jsonStr = json.dumps(xmlObj)
    jsonObj = json.loads(jsonStr)
    
    
    value_ID = JsonPathExpr_id.find(jsonObj)[0].value
    value_language = JsonPathExpr_language.find(jsonObj)[0].value
    
    value_date = getPathValue("date", jsonObj)
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
    
    print("################################################################################################")
    print(value_ID)
    print(value_language)
    print(value_date)
    print(value_scope)
    print(value_standard)
    print(value_standardVersion)

    print(value_file_name)
    print(value_file_description)
    print(value_contactPoint_individual)
    print(value_contactPoint_phone)


    print(value_contactPoint_name)
    print(value_contactPoint_mail)
    print(value_contactPoint_onlineSource)
    print(value_accessUrl)
    print("################################################################################################")


    # Load data to ElasticSearch