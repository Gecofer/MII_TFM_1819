library(shiny)
library(leaflet)
library(rgdal)
library(mapview)
library(DT)

#r_colors <- rgb(t(col2rgb(colors()) / 255))
#names(r_colors) <- colors()

#shapeData1 <- readOGR(dsn="data/", layer="Edificaciones")
#shapeData1 <- spTransform(shapeData1, CRS("+proj=longlat +datum=WGS84"))


#shapeData2 <- readOGR(dsn="data/", layer="CurvaNivel")

data <- read.csv("data/edficaciones.csv")

datos1 <- readOGR(dsn="data/", layer="Edificaciones")
datos2 <- readOGR(dsn="data/", layer="CurvaNivel")



ui <- fluidPage(
  
  titlePanel(p("Spatial app", style = "color:#3474A7")),
  
  sidebarLayout(
    sidebarPanel(
      selectInput(
        inputId = "variableselected",
        label = "Select variable",
        choices = c("ID_HOJA", "GID")
      ),
      selectInput(
        inputId = "yearselected",
        label = "Select value",
        choices = 459750:459759
      ),
      actionButton("do", "Click Me")
      # https://shiny.rstudio.com/articles/action-buttons.html
      
      
      
    ),
    
    mainPanel(
      mapviewOutput("map", width = "100%", height = 400)
    )
  )
  
  
  # mapviewOutput("map", width = "100%", height = 400),
  #p()
  
  
)

server <- function(input, output) {
  
  output$table <- renderDT(data)
  
  
  
  #output$value <- renderPrint({ input$num })
  
  observeEvent(input$do, {
    
    shape <- readOGR(dsn="data/", layer="CurvaNivel") # Leemos la capa
    shape1 <-shape[shape$GID == input$yearselected,] # Nos quedamos con el valor que nos interesa - 469896 - 459757
    
    output$map <- renderMapview({
      
      mapview(datos1) + mapview(datos2) + mapview(shape1)
    })
  })
  
 
} 

shinyApp(ui, server)
