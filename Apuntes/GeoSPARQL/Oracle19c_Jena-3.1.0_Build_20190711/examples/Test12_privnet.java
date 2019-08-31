import org.apache.jena.query.*;
import oracle.spatial.rdf.client.jena.*;
import org.apache.jena.graph.*;

public class Test12_privnet
{
  public static void main(String[] args) throws Exception
  {
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    String szModelName = args[3];
    String szNetworkName = args[4];
    
    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
    
    ModelOracleSem model = ModelOracleSem.createOracleSemModel(oracle, szModelName, szUser, szNetworkName);
    GraphOracleSem g = model.getGraph();

    g.add(Triple.create(
          NodeFactory.createURI("u:John"), NodeFactory.createURI("u:parentOf"), NodeFactory.createURI("u:Mary")));
    String queryString = " ASK { <u:John> <u:parentOf> <u:Mary> } ";

    Query query = QueryFactory.create(queryString) ;
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
    boolean b = qexec.execAsk();
    System.out.println("ask result = " + ((b)?"TRUE":"FALSE"));
    qexec.close() ; 
    
    OracleUtils.dropSemanticModel(oracle, szModelName, szUser, szNetworkName);
    
    model.close();    
    oracle.dispose();
  }
}
