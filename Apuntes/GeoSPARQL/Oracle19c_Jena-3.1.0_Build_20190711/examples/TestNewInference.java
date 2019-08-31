import java.io.*;
import org.apache.jena.query.*;
import oracle.spatial.rdf.client.jena.*;

import org.apache.jena.update.*;
import org.apache.jena.sparql.core.DatasetImpl; 

// DataSourceImpl removed in Apache Jena 2.7.2

/* 
 *  alwu      07/31/12 - update to Apache Jena 2.7.2: DataSourceImpl => DatasetImpl
 */ 

public class TestNewInference
{
  public static void main(String[] args) throws Exception
  {
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    PrintStream psOut = System.out;

    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
    
    String szTBoxName = "test_new_tbox";
    {
      // First construct a TBox and load a few axioms
      ModelOracleSem modelTBox = ModelOracleSem.createOracleSemModel(oracle, szTBoxName);
      String insertString =  
        " PREFIX my:  <http://my.com/> " +
        " PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#> " +
        " INSERT DATA "                                     +
        " { my:C1  rdfs:subClassOf my:C2 .                " +
        "   my:C2  rdfs:subClassOf my:C3 .                " +
        "   my:C3  rdfs:subClassOf my:C4 .                " +
        " }   ";
      UpdateAction.parseExecute(insertString,  modelTBox);
      modelTBox.close();
    }

    String szABoxName = "test_new_abox";
    {
      // First construct an ABox and load a few assertions
      ModelOracleSem modelABox = ModelOracleSem.createOracleSemModel(oracle, szABoxName);
      DatasetGraphOracleSem dataset = DatasetGraphOracleSem.createFrom(modelABox.getGraph());
      modelABox.close();

      String insertString =  
        " PREFIX my:    <http://my.com/> " +
        " PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
        " INSERT DATA                                                 " +
        " { GRAPH my:G1 { my:I1  rdf:type my:C1 .                     " +
        "                 my:I2  rdf:type my:C2 .                     " +
        "               }                                             " +
        " };                                                          " +
        " INSERT DATA                                                 " +
        " { GRAPH my:G2 { my:J1  rdf:type my:C3 .                     " +
        "               }                                             " +
        " }    ";
      UpdateAction.parseExecute(insertString,  dataset);
      dataset.close();
    }


    String[] attachedModels = new String[1];
    attachedModels[0] = szTBoxName;

    String[] attachedRBs = {"OWL2RL"};

    Attachment attachment = Attachment.createInstance(
        attachedModels, attachedRBs,
        InferenceMaintenanceMode.NO_UPDATE,
        QueryOptions.ALLOW_QUERY_INVALID);

    attachment.setUseLocalInference(true);
    attachment.setDefGraphForLocalInference(szTBoxName);
    attachment.setInferenceOption("DOP=4,RAW8=T");

    GraphOracleSem graph = new GraphOracleSem(
        oracle, 
        szABoxName, 
        attachment
        );

    DatasetGraphOracleSem dsgos = DatasetGraphOracleSem.createFrom(graph);
    graph.close();

    dsgos.performInference();

    psOut.println("TestNewInference: # of inferred graph " + 
        Long.toString(dsgos.getInferredGraphSize()));

    String queryString = 
      " SELECT ?g ?s ?p ?o WHERE {  GRAPH ?g {?s ?p ?o } } " ;

    Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
    QueryExecution qexec = QueryExecutionFactory.create(
        query, DatasetImpl.wrap(dsgos));
    ResultSet results = qexec.execSelect();

    ResultSetFormatter.out(psOut, results);

    dsgos.close();
    oracle.dispose();
  }
}
