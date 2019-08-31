import org.apache.jena.util.iterator.*;
import oracle.spatial.rdf.client.jena.*;
import org.apache.jena.graph.*;
import org.apache.jena.update.*;

public class Test16
{
  public static void main(String[] args) throws Exception
  {
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    String szModelName = args[3];
    
    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
    
    ModelOracleSem model = ModelOracleSem.createOracleSemModel(oracle, szModelName);
    GraphOracleSem g = model.getGraph();
    String insertString =  
      " PREFIX dc: <http://purl.org/dc/elements/1.1/> "         + 
      " INSERT DATA "                                           +
      " { <http://example/book3> dc:title    \"A new book\" ; " +
      "                         dc:creator  \"A.N.Other\" . "   + 
      " }   ";

    UpdateAction.parseExecute(insertString,  model);

    ExtendedIterator<Triple> ei = GraphUtil.findAll(g);
    while (ei.hasNext()) {
      System.out.println("Triple " + ei.next().toString());
    }
    OracleUtils.dropSemanticModel(oracle, szModelName);
    model.close();    
    oracle.dispose();
  }
}
