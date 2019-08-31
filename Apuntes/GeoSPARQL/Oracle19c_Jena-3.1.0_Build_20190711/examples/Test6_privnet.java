import java.io.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import oracle.spatial.rdf.client.jena.*;

public class Test6_privnet 
{
  public static void main(String[] args) throws Exception
  {
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    String szModelName = args[3];    
    String szNetworkName = args[4];
    
    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
    
    Model model = ModelOracleSem.createOracleSemModel(oracle, szModelName, szUser, szNetworkName);
        
    // load UNIV ontology
    InputStream in = FileManager.get().open("./univ-bench.owl" );

    model.read(in, null);
   
    OutputStream os = new FileOutputStream("./univ-bench.nt");
    model.write(os, "N-TRIPLE");
    os.close();

    String queryString =
      " SELECT ?subject ?prop ?object WHERE { ?subject ?prop ?object } ";

    Query query = QueryFactory.create(queryString) ;
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;

    try {
      int iTriplesCount = 0;
      ResultSet results = qexec.execSelect() ;
      for ( ; results.hasNext() ; ) {
        QuerySolution soln = results.nextSolution() ;
        iTriplesCount++;
      }
      System.out.println("Asserted  triples count: " + iTriplesCount);
    } 
    finally { 
      qexec.close() ; 
    }
    
    Attachment attachment = Attachment.createInstance(
        new String[] {}, "OWLPRIME",
        InferenceMaintenanceMode.NO_UPDATE,
        QueryOptions.DEFAULT);

    GraphOracleSem graph = new GraphOracleSem(oracle, szModelName, attachment, szUser, szNetworkName);
    graph.analyze();
    graph.performInference();


    query = QueryFactory.create(queryString) ;
    qexec = QueryExecutionFactory.create(query,new ModelOracleSem(graph)) ;

    try {
      int iTriplesCount = 0;
      ResultSet results = qexec.execSelect() ;
      for ( ; results.hasNext() ; ) {
        QuerySolution soln = results.nextSolution() ;
        iTriplesCount++;
      }
      System.out.println("Asserted + Infered triples count: " + iTriplesCount);
    } 
    finally { 
      qexec.close() ; 
    }

    OracleUtils.dropSemanticModel(oracle, szModelName, szUser, szNetworkName);
    
    model.close();    
    oracle.dispose();
  }
}
