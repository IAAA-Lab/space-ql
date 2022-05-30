from tokenize import String
from xml.sax.saxutils import unescape
import pandas as pd 
import json
import xmltodict
from elasticsearch import Elasticsearch, helpers
from jsonpath_ng import jsonpath, parse


INDICES = {
    'records' : 'sql_records',
    'services' : 'sql_services',
    'datasets' : 'sql_datasets'
}

es = Elasticsearch(hosts='http://localhost:9200')

# leer todos los datasets
# leer todos los services
# para cada dataset:
#    ver todos los services que contienen su nombre en la cadena o uno similar quitando la ultima parte separada por . _ o -
#    anyadirlos a un array y guardar/actualizar el elemento en el indice
servicesRaw = es.search(index=INDICES['services'], query={
        "match_all" : {}
}, size=500, from_=0)

datasetsRaw = es.search(index=INDICES['datasets'], query={
        "match_all" : {}
}, size=500, from_=0)

# dict_keys(['_index', '_type', '_id', '_score', '_source'])

for serviceRaw in servicesRaw['hits']['hits']:
    relatedDatasets = []
    serviceId = serviceRaw['_id']
    substrings = {
        0 : serviceId.rpartition('-')[0],
        1 : serviceId.rpartition('_')[0],
        2 : serviceId.rpartition('.')[0],
    }

    for datasetRaw in datasetsRaw['hits']['hits']:
        dsId = datasetRaw['_id']
        dsSubstrings = {
            0 : dsId.rpartition('-')[0],
            1 : dsId.rpartition('_')[0],
            2 : dsId.rpartition('.')[0],
        }
        if (serviceId in dsId) or (dsId in serviceId) or (len(substrings[0]) > 0 and substrings[0] in dsId) or (len(substrings[1]) > 0 and substrings[1] in dsId) or (len(substrings[2]) > 0 and substrings[2] in dsId) :
            relatedDatasets.append(dsId)
        elif (len(dsSubstrings[0]) > 0 and dsSubstrings[0] in serviceId) or (len(dsSubstrings[1]) > 0 and dsSubstrings[1] in serviceId) or (len(dsSubstrings[2]) > 0 and dsSubstrings[2] in serviceId):
            relatedDatasets.append(dsId)
    # actualizar el servicio
    serviceDoc = {
            'id' : serviceId,
            'title' : serviceRaw['_source']['title'],
            'coupledDatasets' : relatedDatasets
        }
    es.index(index=INDICES['services'], id=serviceId, document=serviceDoc)

# ---------------------------------------------------------
# Coupled services for each dataset
for datasetRaw in datasetsRaw['hits']['hits']:
    relatedServices = []
    datasetId = datasetRaw['_id']
    substrings = {
        0 : datasetId.rpartition('-')[0],
        1 : datasetId.rpartition('_')[0],
        2 : datasetId.rpartition('.')[0],
    }

    for serviceRaw in servicesRaw['hits']['hits']:
        srvId = serviceRaw['_id']
        srvSubstrings = {
            0 : srvId.rpartition('-')[0],
            1 : srvId.rpartition('_')[0],
            2 : srvId.rpartition('.')[0],
        }
        if (datasetId in srvId) or (srvId in datasetId) or (len(substrings[0]) > 0 and substrings[0] in srvId) or (len(substrings[1]) > 0 and substrings[1] in srvId) or (len(substrings[2]) > 0 and substrings[2] in srvId) :
            relatedServices.append(srvId)
        elif (len(srvSubstrings[0]) > 0 and srvSubstrings[0] in datasetId) or (len(srvSubstrings[1]) > 0 and srvSubstrings[1] in datasetId) or (len(srvSubstrings[2]) > 0 and srvSubstrings[2] in datasetId):
            relatedServices.append(srvId)
    # actualizar el dataset
    datasetDoc = {
            'id' : datasetId,
            'title' : datasetRaw['_source']['title'],
            'coupledServices' : relatedServices
        }
    es.index(index=INDICES['datasets'], id=datasetId, document=datasetDoc)



es.indices.refresh(index=INDICES['services'])
es.indices.refresh(index=INDICES['datasets'])