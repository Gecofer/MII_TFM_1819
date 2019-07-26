library(shiny)
library(rgdal)
library(DT)
library(dygraphs)
library(xts)
library(leaflet)

data <- read.csv("data/edficaciones.csv")
map <- readOGR("data/Edificaciones.shp")

# ui object
ui <- fluidPage(
  titlePanel(p("Spatial app", style = "color:#3474A7")),
  sidebarLayout(
    sidebarPanel(
      p("Made with", a("Shiny",
                       href = "http://shiny.rstudio.com"
      ), "."),
      img(
        src = "imageShiny.png",
        width = "70px", height = "70px"
      )
    ),
    mainPanel(
      leafletOutput(outputId = "map"),
      dygraphOutput(outputId = "timetrend"),
      DTOutput(outputId = "table")
    )
  )
)

# server()
server <- function(input, output) {
  output$table <- renderDT(data)
  
  output$timetrend <- renderDygraph({
    dataxts <- NULL
    counties <- unique(data$county)
    for (l in 1:length(counties)) {
      datacounty <- data[data$county == counties[l], ]
      dd <- xts(
        datacounty[, "cases"],
        as.Date(paste0(datacounty$year, "-01-01"))
      )
      dataxts <- cbind(dataxts, dd)
    }
    colnames(dataxts) <- counties
    
    dygraph(dataxts) %>%
      dyHighlight(highlightSeriesBackgroundAlpha = 0.2) -> d1
    
    d1$x$css <- "
 .dygraph-legend > span {display:none;}
 .dygraph-legend > span.highlight { display: inline; }
 "
    d1
  })
  
  output$map <- renderLeaflet({
    
    # Add data to map
    datafiltered <- data[which(data$year == 1980), ]
    ordercounties <- match(map@data$NAME, datafiltered$county)
    map@data <- datafiltered[ordercounties, ]
    
    # Create leaflet
    pal <- colorBin("YlOrRd", domain = map$cases, bins = 7)
    
    labels <- sprintf("%s: %g", map$county, map$cases) %>%
      lapply(htmltools::HTML)
    
    l <- leaflet(map) %>%
      addTiles() %>%
      addPolygons(
        fillColor = ~ pal(cases),
        color = "white",
        dashArray = "3",
        fillOpacity = 0.7,
        label = labels
      ) %>%
      leaflet::addLegend(
        pal = pal, values = ~cases,
        opacity = 0.7, title = NULL
      )
  })
}

# shinyApp()
shinyApp(ui = ui, server = server)
