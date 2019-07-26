library(leaflet)
m <- leaflet() %>%
  addTiles() %>%  # Añade por defecto los Tiles de  OpenStreetMap
  addMarkers(lng=-5.664112384, lat=40.96500844, popup="Plaza Mayor de Salamanca")
m  # Imprime el mapa

m <- leaflet() %>% setView(lng = -5.6641, lat = 40.9650, zoom = 12)
m %>% addTiles()

m <- leaflet(options = leafletOptions(minZoom = 0, maxZoom = 14)) %>% setView(lng = -5.6641, lat = 40.9650, zoom = 12)%>%
  setMaxBounds(lng1 = -5.780, lat1 = 40.890,lng2 = -5.580, lat2 = 41.020)
m %>% addTiles()


library(sf)
filename <- system.file("data/Edificaciones.shp", package="sf")
nc <- st_read(dsn="data/", layer="Edificaciones")
nc <- read_sf(dsn="data/", layer="Edificaciones")
plot(nc)
u <- st_union(nc)
plot(u)

filename <- system.file("data/PuntoCota.shp", package="sf")
nc <- st_read(dsn="data/", layer="PuntoCota")
nc <- read_sf(dsn="data/", layer="PuntoCota")
plot(nc)
u <- st_union(nc)
plot(u)


class(u)



salamanca <- data.frame(
  lat = c(40.96500844,40.96440736,40.96381613,40.96395408,40.96423656,40.9638227,40.96279789,40.96311321,40.9617862,40.96142488,40.95849481,40.95944086,40.96063653,40.96047886,40.96158254,40.96301467,40.96279132,40.96016352,40.9605577,40.96114896,40.96193729,40.96515625,40.96477523,40.96526792,40.96553725,40.96604307,40.96670818,40.96681986,40.96534182,40.96966415),
  lng = c(-5.664112384,-5.664499515,-5.665769654,-5.665743555,-5.666100238,-5.666117637,-5.66646562,-5.666413423,-5.667953249,-5.667170286,-5.669658367,-5.666848402,-5.665978444,-5.666657011,-5.665552164,-5.664342923,-5.663316372,-5.660201922,-5.662811796,-5.663333771,-5.66045421,-5.666735307,-5.666822303,-5.668057644,-5.669832358,-5.667205085,-5.664788776,-5.664980167,-5.659203645,-5.663814423))

salamanca_popup = popup = c("Plaza Mayor","Iglesia de San Martín","Palacio de Maldonado de Morille","Casa de los Solis","Convento de la Madre de Dios","Iglesia de San benito","La Clerecía","Universidad Pontificia","Palacio de los Abarca Maldonado - Museo Provincial de Bellas Artes","Universidad","Puente Romano","Casa Lis","Catedral Nueva","Catedral Vieja","Palacio de Anaya","Palacio de Fonseca (La Salina)","Torre del Clavero","Iglesia de Santo Tomas Cantuariense","Convento de San Esteban","Convento de las Dueñas","Convento de Santa Clara","Palacio de Monterrey","Convento de las Agustinas","Convento de los Capuchinos","Colegio Mayor Arzobispo Fonseca","Convento de las Ursulas","Iglesia del Carmen","Casa de Doña Maria La Brava","Iglesia de Sancti Spiritus","Iglesia de San Marcos")

salamanca %>% 
  leaflet() %>%
  addTiles() %>%
  addMarkers(popup = salamanca_popup, clusterOptions = markerClusterOptions())


outline <- quakes[chull(quakes$long, quakes$lat),]

map <- leaflet(quakes) %>%
  # Base groups
  addTiles(group = "OSM (default)") %>%
  addProviderTiles(providers$Stamen.Toner, group = "Toner") %>%
  addProviderTiles(providers$Stamen.TonerLite, group = "Toner Lite") %>%
  # Overlay groups
  addCircles(~long, ~lat, ~10^mag/5, stroke = F, group = "Quakes") %>%
  
  # Layers control
  addLayersControl(
    baseGroups = c("OSM (default)", "Toner", "Toner Lite"),
    overlayGroups = c("Quakes", "Outline"),
    options = layersControlOptions(collapsed = FALSE)
  )
map


library("rgdal")
shapeData1 <- readOGR(dsn="data/", layer="Edificaciones")
summary(shapeData1)
shapeData2 <- readOGR(dsn="data/", layer="CurvaNivel")
shapeData3 <- readOGR(dsn="data/", layer="PuntoCota")

read.asciigrid("data/Edificaciones.shp")
x <- readShapePoly(system.file("data/Edificaciones.shp", package="maptools"))


library(maptools)
xx <- readShapePoints(system.file("data/PuntoCota.shp", package="maptools")[1])


shapeData1 <- spTransform(shapeData, CRS("+proj=longlat +ellps=GRS80"))
shapeData2 <- readOGR(dsn="data/", layer="PuntoCota")

shapeData1 <- st_transform(nc, CRS("+proj=longlat +datum=WGS84"))

SpatialPolygons("data/Edificaciones.shp")




library("leaflet")

# this is assuming your projections are matched
library(sf)

poly_file1 <- st_read("data/Edificaciones.shp")
poly_file2 <- st_read("path_to_second_file.shp")

#get the intersection

poly_intersect <- st_intersection(poly_file1, poly_file2)

#write to file
st_write(poly_file1, "data/Edificaciones-nuevo.shp")



poly_file1 <- st_read(dsn="data/", layer="Edificaciones")
poly_file1 <- read_sf(dsn="data/", layer="Edificaciones")
u <- st_union(poly_file1)

st_write(u, "data/Edificaciones-nuevo.shp")




shapeData1 <- readOGR(dsn="data/", layer="Edificaciones")
shapeData1 <- spTransform(shapeData1, CRS("+proj=longlat +datum=WGS84"))

shapeData2 <- readOGR(dsn="data/", layer="CurvaNivel")
shapeData2 <- spTransform(shapeData2, CRS("+proj=longlat +datum=WGS84"))

leaflet()  %>% addTiles() %>% 
  setView(lng = -3.629684, lat=37.112636,zoom=13) %>% 
  addPolygons(data=shapeData2) 



library('sf')
shapename <- read_sf("data/Edificaciones.shp")
plot(shapename)
plot(shapename$GID)

SpatialPolygons

