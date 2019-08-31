geometry2rdf
------------
Geometry2rdf is a library for generating RDF from geometrical information

geometry2rdf defines a set of RDF triples for geometrical information (which could be available in GML or WKT).

Working with the configuration file
-----------------------------------
The library needs a properties file for its execution. The default name for that file is options.properties. A dummy file with that name is provided in the distribution and can be used. If you want to use a different file, change the path of the properties file in the bat file.

Structure of the properties file:

1. Input and output parameters
Working dir and the name of output file.  

2. DDBB parameters
All the parameters necessaries for the Data Base connection are set in this section. Also the name of the type of resources that will be created is set in this section. 

3. Namespaces parameters
The namespaces and prefixes for the resources generated and for the ontology used are set in this section.

4. Reference systems parameters
Geometry2rdf works for EPSG reference systems. If your gml data is not in EPGS, a transformation between your reference system and a EPSG system is necessary. For doing that, use fields gmlSourceRS and gmlTargetRS. If another transformation is required or wanted for the final data in the RDF, use sourceRS and targetRS. 

5. Types parameters
In this section, the URIs for Point, Linestring and Polygon resources are defined. Also URI of the relationship "formBy" is defined. A Linestring and a Polygon are "formBy" Points.

6. Other parameters
In this section, the default language of the label of each resource is defined.