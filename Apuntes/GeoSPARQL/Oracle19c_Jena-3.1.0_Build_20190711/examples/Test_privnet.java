import oracle.spatial.rdf.client.jena.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.graph.*;
import org.apache.jena.query.*;

public class Test_privnet {

  public static void main(String[] args) throws Exception
  {
  
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    String szModelName = args[3];    
    String szNetworkName = args[4];
	  
    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
    Model model = ModelOracleSem.createOracleSemModel(oracle, szModelName, szUser, szNetworkName);

    model.getGraph().add(Triple.create(
          NodeFactory.createURI("http://example.com/John"),
          NodeFactory.createURI("http://example.com/fatherOf"),
          NodeFactory.createURI("http://example.com/Mary")));

    Query query = QueryFactory.create(
        "select ?f ?k WHERE {?f <http://example.com/fatherOf> ?k .}");
    QueryExecution qexec = QueryExecutionFactory.create(query, model);
    ResultSet results = qexec.execSelect();
    ResultSetFormatter.out(System.out, results, query);

    model.close();
    oracle.dispose();
  }
}

