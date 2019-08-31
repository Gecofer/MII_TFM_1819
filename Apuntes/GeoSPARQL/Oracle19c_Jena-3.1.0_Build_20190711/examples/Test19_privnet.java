import org.apache.jena.query.*;
import org.apache.jena.graph.*;
import oracle.spatial.rdf.client.jena.*;
import oracle.jdbc.pool.*;
import oracle.jdbc.*;

public class Test19_privnet
{
  public static void main(String[] args) throws Exception
  {
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    String szModelName = args[3];
    String szNetworkName = args[4];

    OracleDataSource ds = new OracleDataSource();
    ds.setURL(szJdbcURL);
    ds.setUser(szUser);
    ds.setPassword(szPasswd);

    OracleConnection conn = (OracleConnection) ds.getConnection();
    Oracle oracle = new Oracle(conn);

    ModelOracleSem model = ModelOracleSem.createOracleSemModel(oracle, szModelName, szUser, szNetworkName);
    GraphOracleSem g = model.getGraph();

    g.add(Triple.create(
          NodeFactory.createURI("u:John"), NodeFactory.createURI("u:parentOf"), NodeFactory.createURI("u:Mary")));
    g.add(Triple.create(
          NodeFactory.createURI("u:John"), NodeFactory.createURI("u:parentOf"), NodeFactory.createURI("u:Jack")));
    g.add(Triple.create(
          NodeFactory.createURI("u:Mary"), NodeFactory.createURI("u:parentOf"), NodeFactory.createURI("u:Jill")));
        
    String queryString =
       " SELECT ?s ?o  WHERE { ?s <u:parentOf> ?o .} ";

    Query query = QueryFactory.create(queryString) ;
    QueryExecution qexec = QueryExecutionFactory.create(query, model) ;

    ResultSet results = qexec.execSelect() ;
    ResultSetFormatter.out(System.out, results, query);
    qexec.close() ; 
    
    OracleUtils.dropSemanticModel(oracle, szModelName, szUser, szNetworkName);
    
    model.close();    
    oracle.dispose();
  }
}
