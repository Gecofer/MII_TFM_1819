import org.apache.jena.query.*;
import oracle.spatial.rdf.client.jena.*;
import org.apache.jena.graph.*;

public class Test11
{
  public static void main(String[] args) throws Exception
  {
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    String szModelName = args[3];
    
    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);

    Dataset ds = DatasetFactory.create();

    ModelOracleSem model = ModelOracleSem.createOracleSemModel(oracle, szModelName);
    model.getGraph().add(Triple.create(NodeFactory.createURI("http://example.org/bob"),
          NodeFactory.createURI("http://purl.org/dc/elements/1.1/publisher"),
          NodeFactory.createLiteral("Bob Hacker")));
    model.getGraph().add(Triple.create(NodeFactory.createURI("http://example.org/alice"),
          NodeFactory.createURI("http://purl.org/dc/elements/1.1/publisher"),
          NodeFactory.createLiteral("alice Hacker")));


    ModelOracleSem model1 = ModelOracleSem.createOracleSemModel(oracle, szModelName+"1");

    model1.getGraph().add(Triple.create(NodeFactory.createURI("urn:bob"),
                                        NodeFactory.createURI("http://xmlns.com/foaf/0.1/name"),
                                        NodeFactory.createLiteral("Bob")
          ));
    model1.getGraph().add(Triple.create(NodeFactory.createURI("urn:bob"),
                                        NodeFactory.createURI("http://xmlns.com/foaf/0.1/mbox"),
                                        NodeFactory.createURI("mailto:bob@example")
          ));

    ModelOracleSem model2 = ModelOracleSem.createOracleSemModel(oracle, szModelName+"2");
    model2.getGraph().add(Triple.create(NodeFactory.createURI("urn:alice"),
                                        NodeFactory.createURI("http://xmlns.com/foaf/0.1/name"),
                                        NodeFactory.createLiteral("Alice")
          ));
    model2.getGraph().add(Triple.create(NodeFactory.createURI("urn:alice"),
                                        NodeFactory.createURI("http://xmlns.com/foaf/0.1/mbox"),
                                        NodeFactory.createURI("mailto:alice@example")
          ));

    ds.setDefaultModel(model);
    ds.addNamedModel("http://example.org/bob",model1);
    ds.addNamedModel("http://example.org/alice",model2);

    String queryString =  
      " PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
    + " PREFIX dc: <http://purl.org/dc/elements/1.1/> "
    + " SELECT ?who ?graph ?mbox "
    + " FROM NAMED <http://example.org/alice> "
    + " FROM NAMED <http://example.org/bob> "
    + " WHERE "
    + " { " 
    + "    ?graph dc:publisher ?who . "
    + "    GRAPH ?graph { ?x foaf:mbox ?mbox } "
    + " } ";

    Query query = QueryFactory.create(queryString) ;
    QueryExecution qexec = QueryExecutionFactory.create(query, ds) ;

    ResultSet results = qexec.execSelect() ;
    ResultSetFormatter.out(System.out, results, query);
    
    qexec.close();
    model.close();    
    model1.close();    
    model2.close();    

    OracleUtils.dropSemanticModel(oracle, szModelName);
    OracleUtils.dropSemanticModel(oracle, szModelName + "1");
    OracleUtils.dropSemanticModel(oracle, szModelName + "2");
    oracle.dispose();
  }
}
