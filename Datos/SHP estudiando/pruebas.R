

library(rgdal)
dsn <- system.file("Documents/Github/TFM/Datos/SHP\ estudiando/PuntoCota.shp")
foo <- readOGR(dsn="/Users/gema/Documents/Github/TFM/Datos/SHP\ estudiando/PuntoCota.shp")
cities <- readOGR(dsn=dsn, )

foo <- readOGR(dsn="/Users/gema/Documents/Github/TFM/Datos/SHP\ estudiando/", layer="PuntoCota.shp")

foo.df <- as(foo, "data.frame")