#install.packages("rgdal")
library(rgdal)
install.packages("mapview")
#library(sf)
library(mapview)    # mapa interactivo en R

#datos1 <- readOGR(dsn="data/", layer="Edificaciones")
#plot(datos1)
#datos2 <- readOGR(dsn="data/", layer="CurvaNivel")
#plot(datos2)
#datos3 <- readOGR(dsn="data/", layer="Toponimos")


#datos2 <- shapefile("data/PuntoCota.shp")
#plot(datos)
#plot(datos2)

mapview(list(datos1,datos2))
m <- mapview(datos1) + mapview(datos2)

selectMap(m)

# ----------------------------

library(mapview)
library(mapedit)
library(sf)

border <- st_as_sfc(
  "LINESTRING(-109.050197582692 31.3535554844322, -109.050197582692 31.3535554844322, -111.071681957692 31.3723176640684, -111.071681957692 31.3723176640684, -114.807033520192 32.509681296831, -114.807033520192 32.509681296831, -114.741115551442 32.750242384668, -114.741115551442 32.750242384668, -117.158107738942 32.5652527715121, -117.158107738942 32.5652527715121)"
) %>%
  st_set_crs(4326)

new_borders <- mapview(border) %>%
  editMap("border")


m <- mapview(datos1) + mapview(datos2)
m

# ----------------------------


library(sf)
filename <- system.file("data/Toponimos.shp", package="sf")
nc <- st_read(dsn="data/", layer="Toponimos")
nc <- read_sf(dsn="data/", layer="Toponimos")
plot(nc)
u <- st_union(nc)
plot(u)

mapview(u)

# ----------------------------

mapview(breweries, zcol = "zipcode", at = c(97453), legend = TRUE)

mapview(datos2, zcol = "GID", at = c(459757), legend = TRUE)

# ----------------------------

world_coffee = left_join(world, coffee_data, by = "name_long")
facets = c("coffee_production_2016", "coffee_production_2017")
tm_shape(world_coffee) + tm_polygons(facets) + 
  tm_facets(nrow = 1, sync = TRUE)