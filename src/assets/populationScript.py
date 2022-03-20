from tokenize import String
import pandas as pd 
import os
import csv
import requests
import json
import xmltodict
from elasticsearch import Elasticsearch
from jsonpath_ng import jsonpath, parse

ALT_PATH = '"gmd:characterSet"."gmd:parentIdentifier".'

# JsonPath routes
JsonPathExpr_id = parse('$."gmd:MD_Metadata"."gmd:fileIdentifier"."gco:CharacterString"')
JsonPathExpr_language = parse('$."gmd:MD_Metadata"."gmd:language"."gmd:LanguageCode"')

JsonPathExpr = {
'date' : parse('$."gmd:MD_Metadata"."gmd:dateStamp"."gco:Date"') ,
'scope' : parse('$."gmd:MD_Metadata"."gmd:hierarchyLevelName"."gco:CharacterString"') ,
'standard' : parse('$."gmd:MD_Metadata"."gmd:metadataStandardName"."gco:CharacterString"') ,
'standardVersion' : parse('$."gmd:MD_Metadata"."gmd:metadataStandardVersion"."gco:CharacterString"') ,
'contactPoint_name' : parse('$."gmd:MD_Metadata"."gmd:identificationInfo"."srv:SV_ServiceIdentification"."gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:organisationName"."gco:CharacterString"') ,
'contactPoint_mail' : parse('$."gmd:MD_Metadata"."gmd:identificationInfo"."srv:SV_ServiceIdentification"."gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:address"."gmd:CI_Address"."gmd:electronicMailAddress"."gco:CharacterString"') ,
'contactPoint_onlineSource' : parse('$."gmd:MD_Metadata"."gmd:identificationInfo"."srv:SV_ServiceIdentification"."gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:onlineResource"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"') ,
'accessUrl' : parse('$."gmd:MD_Metadata"."gmd:distributionInfo"."gmd:MD_Distribution"."gmd:transferOptions"."gmd:MD_DigitalTransferOptions"."gmd:onLine"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"') ,
}

Alt_JsonPathExpr = {
'date' : parse('$."gmd:MD_Metadata".{0}"gmd:dateStamp"."gco:Date"'.format(ALT_PATH)) ,
'scope' : parse('$."gmd:MD_Metadata".{0}"gmd:hierarchyLevelName"."gco:CharacterString"'.format(ALT_PATH)) ,
'standard' : parse('$."gmd:MD_Metadata".{0}"gmd:metadataStandardName"."gco:CharacterString"'.format(ALT_PATH)) ,
'standardVersion' : parse('$."gmd:MD_Metadata".{0}"gmd:metadataStandardVersion"."gco:CharacterString"'.format(ALT_PATH)) ,
'contactPoint_name' : parse('$."gmd:MD_Metadata".{0}"gmd:identificationInfo"."srv:SV_ServiceIdentification"."gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:organisationName"."gco:CharacterString"'.format(ALT_PATH)) ,
'contactPoint_mail' : parse('$."gmd:MD_Metadata".{0}"gmd:identificationInfo"."srv:SV_ServiceIdentification"."gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:address"."gmd:CI_Address"."gmd:electronicMailAddress"."gco:CharacterString"'.format(ALT_PATH)) ,
'contactPoint_onlineSource' : parse('$."gmd:MD_Metadata".{0}"gmd:identificationInfo"."srv:SV_ServiceIdentification"."gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:onlineResource"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"'.format(ALT_PATH)) ,
'accessUrl' : parse('$."gmd:MD_Metadata".{0}"gmd:distributionInfo"."gmd:MD_Distribution"."gmd:transferOptions"."gmd:MD_DigitalTransferOptions"."gmd:onLine"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"'.format(ALT_PATH)) ,
}


def getPathValue(field, jsonObj):
    foundInExpr = JsonPathExpr[field].find(jsonObj)
    if(len(foundInExpr) <= 0):
        foundInAlt = Alt_JsonPathExpr[field].find(jsonObj)
        if(len(foundInAlt) <= 0):
            return ""
        else:
            return foundInAlt[0].value    
    else:
        return foundInExpr[0].value


# Find altrentative routes. There are some files that doesn't have the same routes so maybe something like "if(value) is null -> try alt routes"
# It can still get the available functionalities. They are a list of funcitons found as "containsOperations"
# I can maybe move the paths to a file or function that automatically does all the job instead of calling each "find" funciton inside the main body


metadataFile = pd.read_csv('./metadata.csv', encoding='ISO-8859-1', delimiter=',')

for index, row in metadataFile.iterrows():
    fileID = str(row['id'])
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

    
    print("################################################################################################")
    print(value_ID)
    print(value_language)
    print(value_date)
    print(value_scope)
    print(value_standard)
    print(value_standardVersion)
    print(value_contactPoint_name)
    print(value_contactPoint_mail)
    print(value_contactPoint_onlineSource)
    print(value_accessUrl)
    print("################################################################################################")


    # Load data to ElasticSearch
