from errno import EIDRM, ENOANO
import pandas as pd 
import os
import csv
import requests
import json
import xmltodict
from elasticsearch import Elasticsearch
from jsonpath_ng import jsonpath, parse


# JsonPath routes
JsonPathExpr_id = parse('$."gmd:MD_Metadata"."gmd:fileIdentifier"."gco:CharacterString"')
JsonPathExpr_language = parse('$."gmd:MD_Metadata"."gmd:language"."gmd:LanguageCode"')
JsonPathExpr_date = parse('$."gmd:MD_Metadata"."gmd:dateStamp"."gco:Date"')

JsonPathExpr_scope = parse('$."gmd:MD_Metadata"."gmd:hierarchyLevelName"."gco:CharacterString"')
JsonPathExpr_standard = parse('$."gmd:MD_Metadata"."gmd:metadataStandardName"."gco:CharacterString"')
JsonPathExpr_standardVersion = parse('$."gmd:MD_Metadata"."gmd:metadataStandardVersion"."gco:CharacterString"')

JsonPathExpr_contactPoint_name = parse('$."gmd:MD_Metadata"."gmd:identificationInfo"."srv:SV_ServiceIdentification"."gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:organisationName"."gco:CharacterString"')
JsonPathExpr_contactPoint_mail = parse('$."gmd:MD_Metadata"."gmd:identificationInfo"."srv:SV_ServiceIdentification"."gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:address"."gmd:CI_Address"."gmd:electronicMailAddress"."gco:CharacterString"')
JsonPathExpr_contactPoint_onlineSource = parse('$."gmd:MD_Metadata"."gmd:identificationInfo"."srv:SV_ServiceIdentification"."gmd:pointOfContact"."gmd:CI_ResponsibleParty"."gmd:contactInfo"."gmd:CI_Contact"."gmd:onlineResource"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"')

JsonPathExpr_accessUrl = parse('$."gmd:MD_Metadata"."gmd:distributionInfo"."gmd:MD_Distribution"."gmd:transferOptions"."gmd:MD_DigitalTransferOptions"."gmd:onLine"."gmd:CI_OnlineResource"."gmd:linkage"."gmd:URL"')

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
    value_date = JsonPathExpr_date.find(jsonObj)[0].value
    value_scope = JsonPathExpr_scope.find(jsonObj)[0].value
    value_standard = JsonPathExpr_standard.find(jsonObj)[0].value
    value_standardVersion = JsonPathExpr_standardVersion.find(jsonObj)[0].value
    value_contactPoint_name = JsonPathExpr_contactPoint_name.find(jsonObj)[0].value
    value_contactPoint_mail = JsonPathExpr_contactPoint_mail.find(jsonObj)[0].value
    value_contactPoint_onlineSource = JsonPathExpr_contactPoint_onlineSource.find(jsonObj)[0].value
    value_accessUrl = JsonPathExpr_accessUrl.find(jsonObj)[0].value
    
    print("################################################")
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


    # Load data to ElasticSearch
