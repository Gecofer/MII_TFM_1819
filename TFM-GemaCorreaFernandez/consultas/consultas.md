## Consultas donde intervienen solo polígonos

Ejemplo 1. Consulta que obtiene los tres primeros polígonos de Edificaciones que pertenecen a la clase TipoEdificaciones y son EDI (Edificación). Con esta consulta queremos obtener las Edificaciones que están edificadas.

```
PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX geo:<http://www.opengis.net/ont/geosparql#>

SELECT ?x ?p
WHERE {
	?x rdf:type geo:EDI .
	?polygon geo:asWKT ?p
}
LIMIT 3
```


Ejemplo 2. Consulta que obtiene la geometría específica de WKT mediante el operador \texttt{geof:sfEquals. Con esta consulta queremos obtener las Edificaciones correspondientes a una determinada ubicación.


```
PREFIX my:<http://example.org/ApplicationSchema#>
PREFIX geo:<http://www.opengis.net/ont/geosparql#>
PREFIX geof:<http://www.opengis.net/def/function/geosparql/>

SELECT ?f
WHERE {
	?f geo:asWKT ?fWKT .
	FILTER (geof:sfEquals(?fWKT, '''
	POLYGON ((446050.16 4107127.71,446053.42 4107107.66,446029.09 4107103.93,446028.94 4107104.69,446027.13 4107112.67,446041.35 4107115.08,446039.91 4107125.24,446050.16
	4107127.71)) '''^^geo:wktLiteral))
}
```


## Consultas donde intervienen solo puntos

Ejemplo 3. Consulta que obtiene los cinco primeros puntos de PuntoCota con una cota de 700 metros de altitud, y que pertenecen a la clase ContextoPuntoCota y son DEP(Depresión). Con esta consulta queremos obtener los Puntos de Cota que tiene más de una cierta altura y son depresiones.



```
PREFIX my: <http://example.org/ApplicationSchema#>
PREFIX geo: <http://www.opengis.net/ont/geosparql#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT DISTINCT ?point ?pointCota
WHERE {
	?point rdf:type geo:DEP ;
	geo:tieneCota ?pointCota ;

	FILTER (?pointCota > 700)
}
ORDER BY DESC(?pointCota)
LIMIT 5
```


Ejemplo 4. Consulta que obtiene la geometría específica de WKT mediante el operador \texttt{geof:sfWithin. Con esta consulta queremos obtener los Puntos de Cota que están dentro de una determinada ubicación, pudiendo obtener un único valor o varios.



```
PREFIX my: <http://example.org/ApplicationSchema#>
PREFIX geo: <http://www.opengis.net/ont/geosparql#>
PREFIX geof: <http://www.opengis.net/def/function/geosparql/>

PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT ?f
WHERE {
	?f geo:asWKT ?fWKT .
	FILTER (geof:sfWithin(?fWKT, '''
	MULTIPOINT ((446228.92 4108432.53))'''^^geo:wktLiteral))
}
```


## Consultas donde intervienen solo líneas

Ejemplo 5. Consulta que obtiene  la geometría específica de WKT mediante el operador \texttt{geof:sfEquals. Con esta consulta obtener las Curvas de Nivel correspondientes a una ubicación exacta.



```
PREFIX my: <http://example.org/ApplicationSchema#>
PREFIX geo: <http://www.opengis.net/ont/geosparql#>
PREFIX geof: <http://www.opengis.net/def/function/geosparql/>

SELECT ?f
WHERE {
	?f geo:asWKT ?fWKT .
	FILTER (geof:sfEquals(?fWKT, '''
	LINESTRING (440972.52 4106237.28,440972.4 4106232.04,440972.15 4106226.75,440967.68 4106204.44,440964.32 4106205.94,440963.48 4106208.23,440962.08 4106213.03,440960.59 4106219.28,440959.9 4106224.26,440958.99 4106229.78,440958.44 4106234.81,440958.11 4106240.01,440958.05 4106245.3,440958.04 4106250.86,440958.16 4106255.93,440958.41 4106261.44,440959.21 4106266.96,440960.76 4106272.24,440971.61 4106288.61,440974.39 4106281.06,440974.85 4106275.81,440974.77 4106270.3,440972.52 4106237.28)
	'''^^geo:wktLiteral))
}
```



## Consultas donde intervienen líneas y puntos

Ejemplo 6. Consulta que obtiene las cincos primeras geometrías (líneas y puntos) que tienen una cota superior a 700 metros de altitud. Con esta consulta estamos quedándonos con las alturas correspondientes a un valor.


```
PREFIX geo: <http://www.opengis.net/ont/geosparql#>

SELECT ?f
WHERE {
	?f geo:tieneCota ?fCota
	FILTER (?fCota > 700)
}
LIMIT 5
```


Ejemplo 7. Consulta que obtiene las cincos primeras geometrías (líneas y puntos) que tienen una cota superior a 720 metros de altitud y muestra tanto su ubicación con WKT como su cota. Con esta consulta estamos quedándonos con las alturas correspondientes a un valor, y mostrando el valor de su cota y su ubicación. En la figura \ref{fig:salida6} se observa el resultado.



```
PREFIX my: <http://example.org/ApplicationSchema#>
PREFIX geo: <http://www.opengis.net/ont/geosparql#>

SELECT DISTINCT ?point ?pointWKT ?pointCOTA
WHERE {
	?point 	geo:asWKT ?pointWKT ;
	geo:tieneCota ?pointCOTA .
	FILTER (xsd:double(?pointCOTA) > 720)
}
LIMIT 3
```

## Consultas donde intervienen polígonos y puntos

Ejemplo 8. Consulta que obtiene las cincos primeras geometrías de puntos cercanas a un metro del GID de Edificaciones 889549. Con esta consulta nos estamos quedando los Puntos de Cota que están cercanos al GID 889549, con esto es posible obtener la altura asociada a dicha edificación.



```
PREFIX uom: <http://www.opengis.net/def/uom/OGC/1.0/>
PREFIX my: <http://example.org/ApplicationSchema#>
PREFIX geo: <http://www.opengis.net/ont/geosparql#>
PREFIX geof: <http://www.opengis.net/def/function/geosparql/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX sf: <http://www.opengis.net/ont/sf#>

SELECT ?f
WHERE {
	geo:889549 geo:asWKT ?WKT .

	?f rdf:type sf:Point ;
	geo:asWKT ?fWKT .
	FILTER (?fGeom != ?WKT)
}
ORDER BY ASC(geof:distance(?cWKT, ?fWKT, uom:metre))
LIMIT 5
```

Ejemplo 9. Consulta que obtiene las cincos primeras geometrías de polígonos cercanas a un metro de las geometrías de puntos. Con esta consulta nos estamos quedando con las Edificaciones que están a más de 700 metros de altitud.



```
PREFIX uom: <http://www.opengis.net/def/uom/OGC/1.0/>
PREFIX my: <http://example.org/ApplicationSchema#>
PREFIX geo: <http://www.opengis.net/ont/geosparql#>
PREFIX geof: <http://www.opengis.net/def/function/geosparql/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX sf: <http://www.opengis.net/ont/sf#>

SELECT ?p
WHERE {
	?p rdf:type sf:Polygon ;
	geo:asWKT ?pWKT .

	?f rdf:type sf:Point ;
	geo:tieneCota ?pointCota ;
	geo:asWKT ?fWKT .

	FILTER (?pointCota > 700)
}
ORDER BY ASC(geof:distance(?pWKT, ?fWKT, uom:metre))
LIMIT 5
```

## Consultas donde intervienen polígonos y líneas

Ejemplo 10. Consulta que obtiene las cincos primeras geometrías de líneas cercanas a un metro del GID de Edificaciones 889549. Con esta consulta nos estamos quedando las Curvas de Nivel que están cercanos al GID 889549, con esto es posible obtener la altura asociada a dicha edificación.



```
PREFIX uom: <http://www.opengis.net/def/uom/OGC/1.0/>
PREFIX my: <http://example.org/ApplicationSchema#>
PREFIX geo: <http://www.opengis.net/ont/geosparql#>
PREFIX geof: <http://www.opengis.net/def/function/geosparql/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX sf: <http://www.opengis.net/ont/sf#>

SELECT ?f
WHERE {
	geo:889549 geo:asWKT ?WKT .

	?f rdf:type sf:LineString ;
	geo:asWKT ?fWKT .
	FILTER (?fGeom != ?WKT)
}

ORDER BY ASC(geof:distance(?cWKT, ?fWKT, uom:metre))
LIMIT 5
```


Ejemplo 11. Consulta que obtiene las cincos primeras geometrías más cercanas a un metro del GID de Edificaciones 889549. Con esta consulta nos estamos quedando con las características más cercanas al GID 889549.




```
PREFIX uom: <http://www.opengis.net/def/uom/OGC/1.0/>
PREFIX my: <http://example.org/ApplicationSchema#>
PREFIX geo: <http://www.opengis.net/ont/geosparql#>
PREFIX geof: <http://www.opengis.net/def/function/geosparql/>

SELECT ?f
WHERE {
	geo:889549 geo:asWKT ?WKT .
	?f geo:asWKT ?fWKT .
	FILTER (?fGeom != ?WKT)
}

ORDER BY ASC(geof:distance(?cWKT, ?fWKT, uom:metre))
LIMIT 5
```
