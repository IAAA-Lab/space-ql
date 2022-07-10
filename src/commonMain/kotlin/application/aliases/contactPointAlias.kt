package application.aliases

import application.model.Organization

// Parents
const val gobNavarra = "Gobierno de Navarra"
const val dirCadastre = "Directorate General for Cadastre. Spain"
const val gipProvCouncil = "Gipuzkoa Provincial Council"
const val SITNA = "SITNA"
const val dipBcn = "Diputación de Barcelona"
const val ICGC = "ICGC"
const val ACA = "ACA"
const val DTES = "DTES"
const val puertosEstado = "Puertos del Estado"
const val DARP = "DARP"
const val institutVlc = "Institut Cartogràfic Valencià"
const val institutoNacional = "Instituto Geográfico Nacional"
const val IDEE = "IDEE"
const val ministerioTransicion = "Ministerio para la Transición Ecológica y el Reto Demográfico"
const val MAPAMA = "MAPAMA"
const val institutoTecnoloxico = "Instituto Tecnolóxico para o Control do Medio Mariño de Galicia"
const val gobRioja = "Gobierno de La Rioja"
const val CSIC = "CSIC"
const val instNacEstadistica = "Instituto Nacional de Estadística"
const val adif = "Adif"
const val SEAT = "SEAT"
const val instMarina = "Instituto Hidrográfico de la Marina"
const val depHacienda = "Departamento de Hacienda, Finanzas y Presupuestos"
const val instOceanografia = "Instituto Español de Oceanografía"
const val aytACorunya = "Ayuntamiento de A Coruña"
const val IGME = "IGME"
const val gobVasco = "Gobierno Vasco"
const val juntAndalucia = "Junta de Andalucía"
const val ministerioFomento = "Ministerio de Fomento"
const val xuntaGalicia = "Xunta de Galicia"
const val depMedioAmbiente = "Departamento de Medio Ambiente y Urbanismo"
const val AEMET = "AEMET"
const val ministerioAgricultura = "Ministerio de Agricultura, Pesca y Alimentación"
const val juntaExtremadura = "Junta de Extremadura"
const val centroInfExtremadura = "Centro de Información Cartográfica y Territorial de Extremadura"
const val gobAragon = "Gobierno de Aragón"



// Name - main organization
// subOrganization - suborganization
// whole name - nombre completo o alias de la organizacion

// Se mostrará asi por pantalla.

val contactPointAliases = mapOf(
    "Gobierno de Navarra." to 
            Organization(name = gobNavarra, subOrganization = null, wholeName = null),
    "Directorate General for Cadastre. Spain" to 
            Organization(name = dirCadastre, subOrganization = null, wholeName = null),
    "Gipuzkoa Provincial Council" to 
 			Organization(name = gipProvCouncil, subOrganization = null, wholeName = null),
    "SITNA – Sistema de Información Territorial de Navarra." to 
 			Organization(name = SITNA, subOrganization = null, wholeName = "Sistema de Información Territorial de Navarra"),
    "Diputación de Barcelona - Oficina Técnica de Cartografía y SIG Local - Área de Territorio y Sostenibilidad" to 
 			Organization(name = dipBcn, subOrganization = "Oficina Técnica de Cartografía y SIG Local - Área de Territorio y Sostenibilidad", wholeName = null),
    "Gobierno de Navarra. Departamento de Economía y Hacienda. Servicio de Riqueza Territorial y Tributos Patrimoniales." to 
 			Organization(name = gobNavarra, subOrganization = "Departamento de Economía y Hacienda. Servicio de Riqueza Territorial y Tributos Patrimoniales", wholeName = null),
    "Institut Cartogràfic i Geològic de Catalunya (ICGC)" to 
 			Organization(name = ICGC, subOrganization = null, wholeName = "Institut Cartogràfic i Geològic de Catalunya"),
    "Agencia Catalana del Agua (ACA)" to 
 			Organization(name = ACA, subOrganization = null, wholeName = "Agencia Catalana del Agua"),
    "Departamento de Territorio y Sostenibilidad (DTES)" to
 			Organization(name = DTES, subOrganization = null, wholeName = "Departamento de Territorio y Sostenibilidad"),
    "Puertos del Estado" to 
 			Organization(name = puertosEstado, subOrganization = null, wholeName = null),
    "Departamento de Agricultura, Ganadería, Pesca y Alimentación (DARP)" to 
 			Organization(name = DARP, subOrganization = null, wholeName = "Departamento de Agricultura, Ganadería, Pesca y Alimentación"),
    "Institut Cartogràfic Valencià" to 
 			Organization(name = institutVlc, subOrganization = null, wholeName = null),
    "Instituto Geográfico Nacional" to 
 			Organization(name = institutoNacional, subOrganization = null, wholeName = null),
    "Infraestructura de Datos Espaciales de España (IDEE)" to 
 			Organization(name = IDEE, subOrganization = null, wholeName = "Infraestructura de Datos Espaciales de España"),
    "Instituto Cartográfico i Geológico de Catalunya (ICGC)" to 
 			Organization(name = ICGC, subOrganization = null, wholeName = "Instituto Cartográfico i Geológico de Catalunya"),
    "Instituto Cartográfico y Geológico de Cataluña (ICGC))" to 
 			Organization(name = ICGC, subOrganization = null, wholeName = "Instituto Cartográfico y Geológico de Cataluña"),
    "Ministerio para la Transición Ecológica y el Reto Demográfico" to 
 			Organization(name = ministerioTransicion, subOrganization = null, wholeName = null),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA)" to 
 			Organization(name = MAPAMA, subOrganization = null, wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Instituto Tecnolóxico para o Control do Medio Mariño de Galicia" to 
 			Organization(name = institutoTecnoloxico, subOrganization = null, wholeName = null),
    "Subdirección General de Protección de las Aguas y Gestión de Riesgos. Ministerio para la Transición Ecológica y el Reto Demográfico" to 
 			Organization(name = ministerioTransicion, subOrganization = "Subdirección General de Protección de las Aguas y Gestión de Riesgos", wholeName = null),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Subdirección General de Protección de las Aguas y Gestión de Riesgos" to
            Organization(name = ministerioTransicion, subOrganization = "Subdirección General de Protección de las Aguas y Gestión de Riesgos", wholeName = null),
    "Subdirección General para la Protección de la Costa. Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA)" to 
 			Organization(name = MAPAMA, subOrganization = "Subdirección General para la Protección de la Costa", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Centro de Coordinación Operativa, SOS-Rioja. Dirección General de Justicia e Interior. Consejería de Presidencia y Justicia. Gobierno de La Rioja." to 
 			Organization(name = gobRioja, subOrganization = "Centro de Coordinación Operativa, SOS-Rioja. Dirección General de Justicia e Interior. Consejería de Presidencia y Justicia", wholeName = null),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Subdirección General de Aire Limpio y Sostenibilidad Industrial" to 
 			Organization(name = ministerioTransicion, subOrganization = "Subdirección General de Aire Limpio y Sostenibilidad Industrial", wholeName = null),
    "Gobierno de La Rioja" to 
 			Organization(name = gobRioja, subOrganization = null, wholeName = null),
    "Dirección General de la Costa y el Mar. Ministerio para la Transición Ecológica y el Reto Demográfico" to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General de la Costa y el Mar.", wholeName = null),
    "Consejo Superior de Investigaciones Científicas (CSIC) - Instituto de Mayores y Servicios Sociales (IMSERSO)" to 
 			Organization(name = CSIC, subOrganization = "Instituto de Mayores y Servicios Sociales (IMSERSO)", wholeName = "Consejo Superior de Investigaciones Científicas"),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Subdirección General de Dominio Público Hidráulico e Infraestructuras" to 
 			Organization(name = ministerioTransicion, subOrganization = "Subdirección General de Dominio Público Hidráulico e Infraestructuras", wholeName = null),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). División para la protección del Mar" to 
 			Organization(name = MAPAMA, subOrganization = "División para la protección del Mar", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Sección de Sistemas de Información Geográfica y Cartografía. Gobierno de La Rioja (España)" to 
 			Organization(name = gobRioja, subOrganization = "Sección de Sistemas de Información Geográfica y Cartografía", wholeName = null),
    "Instituto Nacional de Estadística" to 
 			Organization(name = instNacEstadistica, subOrganization = null, wholeName = null),
    "Adif" to 
 			Organization(name = adif, subOrganization = null, wholeName = null),
    "Secretaría de Estado de Política Territorial (SEAT)" to 
 			Organization(name = SEAT, subOrganization = null, wholeName = "Secretaría de Estado de Política Territorial"),
    "Instituto Hidrográfico de la Marina" to 
 			Organization(name = instMarina, subOrganization = null, wholeName = null),
    "Departamento de Hacienda, Finanzas y Presupuestos" to 
 			Organization(name = depHacienda, subOrganization = null, wholeName = null),
    "Instituto Español de Oceanografía" to 
 			Organization(name = instOceanografia, subOrganization = null, wholeName = null),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA)." to 
 			Organization(name = MAPAMA, subOrganization = null, wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente "),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Subdirección General de Planificación y Usos Sostenible del Agua (SGPUSA). Dirección General del Agua (DGA)" to
 			Organization(name = ministerioTransicion, subOrganization = "Subdirección General de Planificación y Usos Sostenible del Agua (SGPUSA). Dirección General del Agua (DGA)", wholeName = ""),
    "Subdirección General de Planificación y Usos Sostenible del Agua (SGPYUSA). Dirección General del Agua (DGA) - Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA)" to 
 			Organization(name = MAPAMA, subOrganization = "Subdirección General de Planificación y Usos Sostenible del Agua (SGPYUSA). Dirección General del Agua (DGA)", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Organismo Autónomo Parques Nacionales. Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA)" to 
 			Organization(name = MAPAMA, subOrganization = "Organismo Autónomo Parques Nacionales", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Dirección General del Agua (DGA). Subdirección General de Planificación y Usos Sostenible del Agua (SGPUSA)." to 
 			Organization(name = MAPAMA, subOrganization = "Dirección General del Agua (DGA). Subdirección General de Planificación y Usos Sostenible del Agua (SGPUSA)", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ayuntamiento de A Coruña / Concello da Coruña" to 
 			Organization(name = aytACorunya, subOrganization = null, wholeName = "Ayuntamiento de A Coruña / Concello da Coruña"),
    "Instituto Geológico y Minero de España (IGME)" to 
 			Organization(name = IGME, subOrganization = null, wholeName = "Instituto Geológico y Minero de España"),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Dirección General de Biodiversidad, Bosques y Desertificación. Subdirección General de Biodiversidad Terrestre y Marina." to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General de Biodiversidad, Bosques y Desertificación. Subdirección General de Biodiversidad Terrestre y Marina", wholeName = null),
    "Gobierno Vasco. Dirección de Planificación Territorial y Urbanismo. Servicio Información Territorial" to 
 			Organization(name = gobVasco, subOrganization = "Dirección de Planificación Territorial y Urbanismo. Servicio Información Territorial", wholeName = null),
    "Dirección General de Sostenibilidad de la Costa y del Mar. Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA)" to 
 			Organization(name = MAPAMA, subOrganization = "Dirección General de Sostenibilidad de la Costa y del Mar", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Instituto de Estadística y Cartografía de Andalucía. Consejería de Economía, Hacienda y Administración Pública. Junta de Andalucía" to
 			Organization(name = juntAndalucia, subOrganization = "Instituto de Estadística y Cartografía de Andalucía. Consejería de Economía, Hacienda y Administración Pública", wholeName = null),
    "Ministerio de Fomento. Dirección General de Arquitectura, Vivienda y Suelo" to 
 			Organization(name = ministerioFomento, subOrganization = "Dirección General de Arquitectura, Vivienda y Suelo", wholeName = null),
    "Organismo Autónomo Parques Nacionales.  Fuente: Ministerio para la Transición Ecológica y el Reto Demográfico" to 
 			Organization(name = ministerioTransicion, subOrganization = "Organismo Autónomo Parques Nacionales", wholeName = null),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Subdirección General de Dominio Público Hidráulico e Infraestructuras." to 
 			Organization(name = ministerioTransicion, subOrganization = "Subdirección General de Dominio Público Hidráulico e Infraestructuras", wholeName = null),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Subdirección General de Planificación y Usos Sostenible del Agua (SGPYUSA). Dirección General del Agua (DGA)" to 
 			Organization(name = MAPAMA, subOrganization = "Subdirección General de Planificación y Usos Sostenible del Agua (SGPYUSA). Dirección General del Agua (DGA)", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Consellería de Medio Ambiente, Territorio e Infraestruturas - Xunta de Galicia" to 
 			Organization(name = xuntaGalicia, subOrganization = "Consellería de Medio Ambiente, Territorio e Infraestruturas", wholeName = null),
    "Ministerio de Agricultura, Pesca y Alimentación (MAPA). Dirección General de Desarrollo Rural, Innovación y Política Forestal" to 
 			Organization(name = MAPAMA, subOrganization = "Dirección General de Desarrollo Rural, Innovación y Política Forestal", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Subdirección General de Aire Limpio y Sostenibilidad Industrial." to 
 			Organization(name = ministerioTransicion, subOrganization = "Subdirección General de Aire Limpio y Sostenibilidad Industrial", wholeName = null),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Dirección General de Biodiversidad, Bosques y Desertificaciónl" to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General de Biodiversidad, Bosques y Desertificaciónl", wholeName = null),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Dirección General del Agua (DGA). Subdirección General de Planificación y Usos Sostenible del Agua (SGPUSA)." to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General del Agua (DGA). Subdirección General de Planificación y Usos Sostenible del Agua (SGPUSA)", wholeName = null),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Dirección General de Biodiversidad, Bosques y Desertificación." to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General de Biodiversidad, Bosques y Desertificación", wholeName = null),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Dirección General de Calidad y Evaluación Ambiental y Medio Natural (DGCEAMN). Subdirección General de Residuos (SGRES)." to 
 			Organization(name = MAPAMA, subOrganization = "Dirección General de Calidad y Evaluación Ambiental y Medio Natural (DGCEAMN). Subdirección General de Residuos (SGRES)", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Subdirección General de Regadíos y Economía del Agua" to 
 			Organization(name = MAPAMA, subOrganization = "Subdirección General de Regadíos y Economía del Agua", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Dirección General de Calidad y Evaluación Ambiental y Medio Natural (Subdirección General de Medio Natural)" to 
 			Organization(name = MAPAMA, subOrganization = "Dirección General de Calidad y Evaluación Ambiental y Medio Natural (Subdirección General de Medio Natural)", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Departamento de Medio Ambiente y Urbanismo" to 
 			Organization(name = depMedioAmbiente, subOrganization = null, wholeName = null),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Dirección General de Sostenibilidad de la Costa y del Mar (DGSCM). Subdirección General para la Protección del Mar (SGPM)." to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General de Sostenibilidad de la Costa y del Mar (DGSCM). Subdirección General para la Protección del Mar (SGPM)", wholeName = null),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Subdirección General Silvicultura y Montes" to 
 			Organization(name = MAPAMA, subOrganization = "Subdirección General Silvicultura y Montes", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Subdirección General de Calidad del Aire y Medio Ambiente Industrial (SGCAMAI)" to 
 			Organization(name = MAPAMA, subOrganization = "Subdirección General de Calidad del Aire y Medio Ambiente Industrial (SGCAMAI)", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA) - Subdirección General de Planificación y Usos Sostenible del Agua (SGPYUSA). Dirección General del Agua (DGA)" to 
 			Organization(name = MAPAMA, subOrganization = "Subdirección General de Planificación y Usos Sostenible del Agua (SGPYUSA). Dirección General del Agua (DGA)", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Dirección General de Biodiversidad, Bosques y Desertificación" to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General de Biodiversidad, Bosques y Desertificación", wholeName = null),
    "Agencia Estatal de Meteorología (AEMET)" to 
 			Organization(name = AEMET, subOrganization = null, wholeName = "Agencia Estatal de Meteorología"),
    "Centro de Coordinación Operativa, SOS-Rioja. Dirección General de Justicia e Interior. Consejería de Presidencia y Justicia. Gobierno de La Rioja" to
 			Organization(name = gobRioja, subOrganization = "Centro de Coordinación Operativa, SOS-Rioja. Dirección General de Justicia e Interior. Consejería de Presidencia y Justicia", wholeName = null),
    "Dirección General de Innovación, Industria y Comercio. Gobierno de La Rioja" to 
 			Organization(name = gobRioja, subOrganization = "Dirección General de Innovación, Industria y Comercio", wholeName = null),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Dirección General de Desarrollo Rural y Política Forestal" to 
 			Organization(name = MAPAMA, subOrganization = "Dirección General de Desarrollo Rural y Política Forestal", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Dirección General de Sostenibilidad de la Costa y el Mar (DGSCM). Subdirección General de Protección del Mar (SGPM)." to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General de Sostenibilidad de la Costa y el Mar (DGSCM). Subdirección General de Protección del Mar (SGPM)", wholeName = null),
    "Ministerio para la Transición Ecológica" to 
 			Organization(name = ministerioTransicion, subOrganization = null, wholeName = null),
    "Fondo Español de Garantía Agraria (FEGA), Subdirección General de Ayudas Directas - Ministerio de Agricultura, Pesca y Alimentación" to
 			Organization(name = ministerioAgricultura, subOrganization = "Fondo Español de Garantía Agraria (FEGA), Subdirección General de Ayudas Directas", wholeName = null),
    "Junta de Extremadura. Centro de Información Cartográfica y Territorial de Extremadura (CICTEX)" to 
 			Organization(name = juntaExtremadura, subOrganization = "Centro de Información Cartográfica y Territorial de Extremadura (CICTEX)", wholeName = null),
    "Centro de Información Cartográfica y Territorial de Extremadura" to 
 			Organization(name = centroInfExtremadura, subOrganization = null, wholeName = null),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Subdirección General de Protección de las Aguas y Gestión de Riesgos" to 
 			Organization(name = MAPAMA, subOrganization = "Subdirección General de Protección de las Aguas y Gestión de Riesgos", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente "),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA). Dirección General de Sostenibilidad de la Costa y el Mar (DGSCM). Subdirección General de Protección del Mar (SGPM)." to
 			Organization(name = MAPAMA, subOrganization = "Dirección General de Sostenibilidad de la Costa y el Mar (DGSCM). Subdirección General de Protección del Mar (SGPM)", wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Dirección General de Salud Pública y Consumo. Gobierno de La Rioja" to 
 			Organization(name = gobRioja, subOrganization = "Dirección General de Salud Pública y Consumo", wholeName = null),
    "Gobierno de Aragón. Instituto Geográfico de Aragón." to 
 			Organization(name = gobAragon, subOrganization = "Instituto Geográfico de Aragón", wholeName = null),
    "Ministerio Para la Transición Ecológica. Dirección General Sostenibilidad de la Costa y del Mar." to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General Sostenibilidad de la Costa y del Mar", wholeName = null),
    "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente (MAPAMA)USA)" to 
 			Organization(name = MAPAMA, subOrganization = null, wholeName = "Ministerio de Agricultura y Pesca, Alimentación y Medio Ambiente"),
    "Ministerio para la Transición Ecológica y el Reto Demográfico. Dirección General de Biodiversidad y Calidad Ambiental" to 
 			Organization(name = ministerioTransicion, subOrganization = "Dirección General de Biodiversidad y Calidad Ambiental", wholeName = null)
)