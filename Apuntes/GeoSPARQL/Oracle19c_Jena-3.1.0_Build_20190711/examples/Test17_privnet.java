import org.apache.jena.query.*;
import oracle.spatial.rdf.client.jena.*;
import org.apache.jena.update.*;

public class Test17_privnet
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
      " PREFIX dc: <http://purl.org/dc/elements/1.1/> "         + 
      " INSERT DATA "                                           +
      " { <http://example/book3> dc:title    \"A new book\" ; " +
      "                         dc:creator  \"A.N.Other\" . "   + 
      "   <http://example/book4> dc:title    \"Semantic Web Rocks\" ; " +
      "                         dc:creator  \"TB\" . "   + 
      " }   ";

    UpdateAction.parseExecute(insertString,  model);

    String queryString = "PREFIX  dc:   <http://purl.org/dc/elements/1.1/> " +
      " PREFIX  fn: <http://www.w3.org/2005/xpath-functions#> " + 
      " SELECT ?subject (fn:upper-case(?object) as ?object1) (fn:string-length(?object) as ?strlen) " + 
      "WHERE { ?subject dc:title ?object } " 
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
