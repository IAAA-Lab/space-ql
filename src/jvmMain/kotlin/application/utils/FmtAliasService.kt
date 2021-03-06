package application.utils

import org.springframework.stereotype.Service

@Service
class FmtAliasService{
    val formatAliases = mapOf(
        "gml+xml" to "gml",
        "WMS" to "wms",
        "GML" to "gml",
        "png" to "PNG",
        "x-ecw" to "ECW",
        "Shapefile (.shp)" to "shapefile",
        "gml+xml a través de los servicios WFS." to "gml",
        "x-shapefile" to "shapefile",
        "ASCII matriz ESRI (.asc)" to "ascii",
        "File Geodatabase ESRI" to "Geodatabase",
        "GeoPackage" to "GeoPackage",
        "gml+xml a través de servicio WFS." to "gml",
        "Geodatabase (.gdb)" to "Geodatabase",
        "MDB" to "MDB",
        "gml+xml a través de servicio WFS y ATOM" to "gml",
        "GML a través de servicio WFS y ATOM" to "gml",
        "gml+xml a través de los servicios de descarga WFS." to "gml",
        "SHP-ArcView ShapeFile" to "shapefile",
        "OGC to OWS-C" to "OGC",
        "NETCDF" to "NETCDF",
        "Geodatabase Corporativa" to "Geodatabase",
        "PNG" to "PNG",
        "GIF" to "GIF",
        "JPEG" to "JPEG",
        "BMP" to "BMP",
        "TIFF" to "TIFF",
        "SVG" to "SVG",
        "TXT" to "TXT",
        "ASCII" to "ascii",
        "unknown" to "Unknown",
        "application/gml+xml" to "gml",
        "kml" to "kml",
        "ascii-grid" to "ascii",
        "Geotiff" to "TIFF",
        "Pdf" to "PDF",
        "vnd.google-earth.kml+xml" to "kml",
        "Mapa digital to  SHP - ArcView ShapeFile" to "shapefile",
        "Mapa digital to  ARCE - ARC/INFO Export format" to "ARCE",
        "Mapa digital to  ARCC - Coverage of Arc-Info" to "ARCC",
        "Servicio web to  WMS - Web Map Service" to "wms",
        "GRID-ARC/INFO Binary Format" to "ARC",
        "RPF-Ráster Product Format (National Imagery and Mapping Agency);" to "RPF",
        "ESRI Shapefile" to "shapefile",
        "csv" to "CSV",
        "xls" to "XLS",
        "TIFF - Tagged Image File Format" to "TIFF",
        "ECW - ERMapper Compress Wavelets" to "ECW",
        "PDF - Portable Document Format (Adobe Systems)" to "PDF",
        "DGN" to "DGN",
        "DWG" to "DWG",
        "DXF" to "DXF",
        "Tiff" to "TIFF",
        "GeoTIFF" to "TIFF",
        "Multi-part MIME (MEDIATYPE=multipart/related)" to "MIME",
        "PDF-Portable Document Format" to "PDF",
        "Mapa digital to  JPEG -Joint Photographic Group Format" to "JPEG",
        "Mapa en papel"  to  "Mapa en papel"
    )

    fun getFormatAlias(fmt : String) : String {
        return formatAliases[fmt] ?: "Other/Unknown"
    }
}
