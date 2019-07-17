# La web semántica

_pagina 3_

- Combinar información no estructurada (web) y estructura.
- Símil a la BD
- *Linked data*, tiene infomacion semántica y es parecida a la wikipedia (http://linkeddata.org/)
- Con la semántica tenemos más libertad que con la BD, no es rígido.
- Tengo tripletas (sujeto, predicado, objeto) y debo razonar.
- Podemos hacer que crezca el documento (libertad), independiente del dominio (RDF).

### Objetivo RDFs

- Validar sentencias de acuerdo al ámbito en el que estoy trabajando: rango y dominio.
- Con un esquema RDF se definen como han de estructurarse los metadatos.

### RDF-Schema

- La propiedad es lo mismo que el predicado.

### Instancias, Clases y Jerarquías de Clases

- Marta es un tipo persona.
~~~
marta rdf:type Persona
~~~

- Una clase puede tener varias superclases (herencia múltiple).

### Definición del Esquema RDF

- Sentencias, recursos y propiedades.
- Se está usando JSON-LD, N3, Turtle, aunque generalmente XML. Ahora se está tendiendo a escribir las comunicaciones entre los elementos con JSON-L. Para mostrar el esquema Turtle, es más plano, más sencillo a simple vista. N3 es muy parecido a Turtle.

### Clases y subclases

- Todas las clases y las subclases, una URI única.

### RDF/RDFs

- _Ver para que usar cada una_

## Semánticas de RDF

- Semántica en logica de predicados: muy fuerte y dificil de escalar.
- Semántica basa en reglas de inferencias.


- Podemos inferir y hacer busquedas semanticas.

## Problema con RDFs

- No podemos definir propiedades transitivas, ni inversas o simetricas.

## RDF Schema vs. XML Schema

- RDFSchema usa RDF y OWL añade cosas.
- RDFSchema es semántica, XML Schema sintáctica.


---

# TEMA NUEVO

- Compartir información entre ontología.

## Ingeniería de Ontologías y Web Semántica.

- Definición de Ontología, de donde surgió: IA, linguistica, informatica, filosofía.
- Protegé necesita individuos para razonar, es decir, en coche mercedes poner el modelo.
- Origen e Historia: las personas necesitamos símbolos (palabras) para comunicarnos.
- Ontologías para comunicar entre máquinas.
- La ontología más usada (metología) por Noy y MacGuiness.
- Lenguajes de definicion de Ontologias: oil, daml, owl, owl 2 (historia de las ontologías)
- Herramientas para creación de ontología:
  - Dependientes del Lenguajes
  - INDEPENDIENTES del Lenguajes  
    - protege es el mas usando
    - web protege ()

## OWL
OWL (Web Ontology Language) --> no WOL, porque OWL es el buhó es del de la sabiduría

- Es un conjunto de eleentos y atributos XML, con un significado estándar, que se usa para definir términos y sus relacionas
- Soporte al razonamient en owl
- OWL asume un mundo abierto y distribuido
- Sublenguajes de OWL:
  - OWL lite, menor expresiva pero mayor raonzamiento (aconseja)
  - owl dl: uso todo lo de owl lite + cosas
  - owl full: maximo expresiva pero no hay razonadores que hay

- Nomenclatura básica ontología entre owl y protege  
- USar SPARQL para buscar en ontologías, enazul los conceptos basicos

---

New tab > creamos y en el hacemos
windows > views > ontology views  > RDF/XML


---

USar editor de texto en vez de protege


---
# Dudas para el tutor

1. **¿Uso JSON-LD o XML?**
2. **Ontologías simples**: mundo real que se necesita
3. **Ver si hay ontologías de SIG**: ver si las URI las reutilizo, y enlazo datos.
4. **Crear yo la ontología** y mirar la generación para los Sistemas de Información Geografico (centrar los datos a coger)
5. Hacer uso de las buenas prácticas para crear ontología --> BUENAS PRACTICAS (IMPORTANTEAAAAAAAA)
  - BIEN DOCUMENTADA
  - DEFERENCIABLE. URI PERMAMENTE: es importante que la uri funcione (perl, algo que sea estable y que cuelgue de la universidad de granada y que no cierre)
  - USADA POR AL MENOS DOS CONJUNTOS DE DATOS INDEPENDIENTES
  - USADA POR ALMENOS 2 PROVEEdores de DATOS
  - permite usarse por herramientas existentes: protege, validator W3C
  - otros: interoperabilidad, reusable y escalable.
6. Requisitos generales.
7. ¿Que usar protege o web protege? Me gusta más la web.
