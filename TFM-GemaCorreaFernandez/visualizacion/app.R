# ----------------------------------------------------- app.R ---------------------------------------------------

# Autora: Gema Correa Fernández


# Cargamos las librerías necesarias
library(shiny)
library(leaflet)
library(rgdal)
library(mapview)
library(DT)
library(sf)
library(shinyalert)


# Cargamos los datos a usar
# ---------------------------------------------------------------------------------------------------------------
# Voy a usar library(rgdal), sin embargo, con esta librería no me deja leer MULTIPUNTOS, solo LINEAS  y POLIGONOS

# POLIGONOS - Ver si queremos cambiar su transformación con spTransform
datos.edificaciones <- readOGR(dsn="datos/", layer="Edificaciones")

# LINEAS - Ver si queremos cambiar su transformación con spTransform
datos.curvanivel <- readOGR(dsn="datos/", layer="CurvaNivel")

# MULTIPUNTOS - Ver si queremos cambiar su transformación con spTransform
#datos.puntocota <- readOGR(dsn="datos/", layer="PuntoCota") # necesito pasarlo a puntos
# Error in readOGR(dsn = "data/", layer = "Toponimos") : Incompatible geometry: 4
# https://github.com/yutannihilation/kokudosuuchi/issues/3
# Parece que es un error de RGDAL, entonces he encontrado otra librería (library(sf)) que parece que lo hace

# Estoy abriendo un archivo donde son puntos (transformado con QGIS)
datos.puntocota <- readOGR(dsn="datos/", layer="TransformadoPuntoCota")


# Parte correspondiente a la Interfaz de Usuario
# https://shiny.rstudio.com/tutorial/written-tutorial/lesson2/
# ---------------------------------------------------------------------------------------------------------------

ui <- fluidPage(
  
  # Establecemos el título
  titlePanel(p("Ontología GEOARES", style = "color:#6433FF")),
  
  # Definimos el Layout
  sidebarLayout(
    position = "left",
    sidebarPanel(
      # Select Box
      selectInput(
        inputId = "geometriaselected",
        label = ("Selecciona una capa"),
        choices = c("Edificaciones", "Curvas de Nivel", "Puntos de Cota")
      ),
      # Numeric Input
      numericInput(
        inputId = "num", 
        label = ("Introduce valor de GID"), 
        value = 1
      ),   
      # Button
      actionButton("do", "Buscar", position="right")
      # https://shiny.rstudio.com/articles/action-buttons.html
    ),
    
    # Definimos el texto que va escrito en la aplicación
    mainPanel(
      p("La Web Semántica nos ha permitido integrar Información Geográfica. Una vez obtenidas las consultas con GeoSPARQL, es posible localizar los GID de acuerdo a su capa."),
      br(),
      strong("Ejemplos:"),
      div("Capa Edificaciones - GID 181062"),
      div("Capa Curvas de Nivel - GID 459757"),
      div("Capa Puntos de Cota - GID 890947"),
      br(),
      textOutput("selected_var"),
      br(),br(),
      mapviewOutput("map", width = "100%", height = 400)
    )
  )
)


# Parte correspondiente al Servidor
# https://shiny.rstudio.com/tutorial/written-tutorial/lesson2/
# ---------------------------------------------------------------------------------------------------------------

server <- function(input, output) {
  
  output$table <- renderDT(data)
  
  # Motramos la capa y el GID seleccionado
  output$selected_var <- renderText({ 
    paste("Has seleccionado la capa", input$geometriaselected, "y el GID", input$num)
  })
  
  # Mostramos el mapa con las capas cargadas
  output$map <- renderMapview({
    mapview(datos.edificaciones) + mapview(datos.curvanivel) + mapview(datos.puntocota)
  })
  
  # Al pulsar el botón se carga la geometría específica
  observeEvent(input$do, {
    
    # Si pulsamos la capa de Curvas de Nivel, creamos la capa nueva con ese GID
    if (input$geometriaselected == "Curvas de Nivel")
      shape <- datos.curvanivell[datos.curvanivell$GID == input$num,] # Nos quedamos con el valor que nos interesa 
      
    # Si pulsamos la capa de Edificaciones, creamos la capa nueva con ese GID
    else if (input$geometriaselected == "Edificaciones")
      shape <- datos.edificaciones[datos.edificaciones$GID == input$num,] # Nos quedamos con el valor que nos interesa 
    
    # Si pulsamos la capa de Puntos de Cota, creamos la capa nueva con ese GID
    else if (input$geometriaselected == "Puntos de Cota")
      shape <- datos.puntocota[datos.puntocota$GID == input$num,] # Nos quedamos con el valor que nos interesa
   
    # Nos pinta en el mapa, la nueva capa obtenida
    output$map <- renderMapview({
      mapview(datos.edificaciones) + mapview(datos.curvanivel) + mapview(datos.puntocota) + mapview(shape)
    })
  })
} 


# Ejecuta la aplicación
# ---------------------------------------------------------------------------------------------------------------
shinyApp(ui, server)


