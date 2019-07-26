# Codigo para pruebas del TFM

library(rgdal)
library(sf)

# ---------------------------------------------------------------------------------------------------------------
# Para leer los ficheros

# Voy a usar library(rgdal), sin embargo, con esta librería no me deja leer MULTIPUNTOS, solo LINEAS  y POLIGONOS

# POLIGONOS - Ver si queremos cambiar su transformación con spTransform
datos1 <- readOGR(dsn="data/", layer="Edificaciones")
plot(datos1)

# LINEAS - Ver si queremos cambiar su transformación con spTransform
datos2 <- readOGR(dsn="data/", layer="CurvaNivel")
plot(datos2)

# MULTIPUNTOS - Ver si queremos cambiar su transformación con spTransform
datos3 <- readOGR(dsn="data/", layer="Toponimos") # EEROR
# Error in readOGR(dsn = "data/", layer = "Toponimos") : Incompatible geometry: 4
# https://github.com/yutannihilation/kokudosuuchi/issues/3
# Parece que es un error de RGDAL, entonces he encontrado otra librería (library(sf)) que parece que lo hace

filename <- system.file("data/Toponimos.shp", package="sf")
nc <- st_read(dsn="data/", layer="Toponimos")
nc <- read_sf(dsn="data/", layer="Toponimos")
plot(nc)
u <- st_union(nc)
plot(u)

# Estoy abriendo un archivo donde son puntos (transformado con QGIS)
datos4 <- readOGR(dsn="data/", layer="puntos")
plot(datos4)

mapview(datos2)

# ---------------------------------------------------------------------------------------------------------------

# Para seleccionar solo una capa o pintarla o reslatarla (como resultado de la consulta), no encuentro 
# ninguna manera con MAPVIEW, por eso el profesor me aconseja crear una capa nueva con dicha información 
# relevante. Funciona tanto para puntos, poligonos y lineas

shape <- readOGR(dsn="data/", layer="CurvaNivel") # Leemos la capa
shape1 <-shape[shape$GID == 469896,] # Nos quedamos con el valor que nos interesa
writeOGR(shape1, dsn = 'data/', layer ='newstuff', driver = 'ESRI Shapefile') # Guardamos la capa
plot(shape1)
mapview(shape1)

# ---------------------------------------------------------------------------------------------------------------

