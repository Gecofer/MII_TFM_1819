library(raster)
library(leaflet)

llanos <- shapefile("data/Edificaciones.shp")
wrs2 <- shapefile("data/Edificaciones.shp")


leaflet(data = llanos) %>% addTiles() %>% addProviderTiles(providers$OpenStreetMap) %>% 
  addPolygons(fill = FALSE, stroke = TRUE, color = "#03F") %>% 
  addLegend("bottomright", colors = "#03F", labels = "Llanos ecoregion")

leaflet() %>% addTiles() %>% addProviderTiles(providers$OpenStreetMap) %>%   
  addPolygons(data = llanos, fill = FALSE, stroke = TRUE, color = "#03F", group = "Study area") %>% 
  addPolygons(data = wrs2, fill = TRUE, stroke = TRUE, color = "#f93", 
              popup = paste0("Scene: ", as.character(wrs2$PATH_ROW)), group = "Landsat scenes") %>% 
  # add a legend
  addLegend("bottomright", colors = c("#03F", "#f93"), labels = c("Study area", "Landsat scenes (path - row)")) %>%   
  # add layers control
  addLayersControl(
    overlayGroups = c("Study area", "Landsat scenes"),
    options = layersControlOptions(collapsed = FALSE)
  )  
