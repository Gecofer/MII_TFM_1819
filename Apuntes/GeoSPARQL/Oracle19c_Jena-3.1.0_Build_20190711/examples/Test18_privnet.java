import org.apache.jena.query.*;
import oracle.spatial.rdf.client.jena.*;
import org.apache.jena.update.*;

public class Test18_privnet
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
    String insertString =  
      " PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
      " INSERT DATA "                                     +
      " { <u:Object1> <u:temp>    \"18.1\"^^xsd:float ; " +
      "               <u:name>    \"Foo... \" . "         + 
      "   <u:Object2> <u:temp>    \"32.0\"^^xsd:float ; " +
      "               <u:name>    \"Bar... \" . "         + 
      " }   ";

    UpdateAction.parseExecute(insertString,  model);

    String queryString = 
      " PREFIX  fn: <http://www.w3.org/2005/xpath-functions#> " + 
      " SELECT ?subject ((?temp - 32.0)*5/9 as ?celsius_temp) " +
      "WHERE { ?subject <u:temp> ?temp } " 
      ;

    Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
    QueryExecution qexec = QueryExecutionFactory.create(query, model);
    ResultSet results = qexec.execSelect();

    ResultSetFormatter.out(System.out, results, query);

    model.close();    
    OracleUtils.dropSemanticModel(oracle, szModelName, szUser, szNetworkName);
    oracle.dispose();
  }
}
