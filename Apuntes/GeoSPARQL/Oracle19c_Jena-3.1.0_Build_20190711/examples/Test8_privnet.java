import java.io.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;
import oracle.spatial.rdf.client.jena.*;
import org.apache.jena.graph.*;

public class Test8_privnet 
{
  public static void main(String[] args) throws Exception
  {
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    String szModelName = args[3];
    String networkName = args[4];
    
    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
    
    ModelOracleSem model = ModelOracleSem.createOracleSemModel(oracle, szModelName, szUser, networkName);
    GraphOracleSem g = model.getGraph();

    g.add(Triple.create(
          NodeFactory.createURI("u:John"), NodeFactory.createURI("u:parentOf"), NodeFactory.createURI("u:Mary")));
    g.add(Triple.create(
          NodeFactory.createURI("u:John"), NodeFactory.createURI("u:parentOf"), NodeFactory.createURI("u:Jack")));
    g.add(Triple.create(
          NodeFactory.createURI("u:Mary"), NodeFactory.createURI("u:parentOf"), NodeFactory.createURI("u:Jill")));
        
    String queryString =
      " SELECT ?s ?o ?gkid WHERE { ?s <u:parentOf> ?o . OPTIONAL {?o <u:parentOf> ?gkid }} ";

    Query query = QueryFactory.create(queryString) ;
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;

    try {
      ResultSet results = qexec.execSelect() ;
      ResultSetFormatter.out(System.out, results, query);
    } 
    finally { 
      qexec.close() ; 
    }
    
    OracleUtils.dropSemanticModel(oracle, szModelName, szUser, networkName);
    
    model.close();    
    oracle.dispose();
  }
}
