

library(rgdal)

foo <- readOGR(dsn="/Users/gema/Documents/Github/TFM/Datos/20180313_BCA_30102632/TM2_Toponimia", layer="Toponimos")
foo <- readOGR(dsn="/Users/gema/Documents/Github/TFM/Datos/20180313_BCA_30102632/TM3_Relieve", layer="CurvaNivel")
foo <- readOGR(dsn="/Users/gema/Documents/Github/TFM/Datos/20180313_BCA_30102632/TM4_Sistema_Urbano", layer="Edificaciones")

foo@data

plot(foo)

foo.df <- as(foo, "data.frame")
View(foo.df)

coordinates <- coordinates(foo)
length(coordinates)
class(coordinates)

c <- data.frame(Reduce(rbind, coordinates))
c
View(c)
df <- data.frame(matrix(unlist(coordinates), nrow=length(coordinates), byrow=T),stringsAsFactors=FALSE)

coordinates.foo.df <- data.frame(matrix(unlist(coordinates), nrow=length(coordinates), byrow=T))

coordinates.foo.df <- as(coordinates, "data.frame")
View(coordinates.foo.df)

proj4string(foo.df) 

library(raster)
s <- shapefile("PuntoCota.shp")

nrow(coordinates)
