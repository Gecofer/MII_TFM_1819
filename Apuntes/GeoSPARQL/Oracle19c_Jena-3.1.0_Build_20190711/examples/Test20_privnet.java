import org.apache.jena.graph.*;
import oracle.spatial.rdf.client.jena.*;

public class Test20_privnet
{
  public static void main(String[] args) throws Exception
  {
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    String szModelName = args[3];
    String szNetworkName = args[4];

    // test with connection properties (taken from some example)
    java.util.Properties prop = new java.util.Properties();
    prop.setProperty("MinLimit", "2");     // the cache size is 2 at least 
    prop.setProperty("MaxLimit", "10");
    prop.setProperty("InitialLimit", "2"); // create 2 connections at startup
    prop.setProperty("InactivityTimeout", "1800");    //  seconds
    prop.setProperty("AbandonedConnectionTimeout", "900");  //  seconds
    prop.setProperty("MaxStatementsLimit", "10");
    prop.setProperty("PropertyCheckInterval", "60"); // seconds

    System.out.println("Creating OraclePool");
    OraclePool op = new OraclePool(szJdbcURL, szUser, szPasswd, prop, "OracleSemConnPool");
    System.out.println("Done creating OraclePool");

    // grab an Oracle and do something with it
    System.out.println("Getting an Oracle from OraclePool");
    Oracle oracle = op.getOracle();
    System.out.println("Done");
    System.out.println("Is logical connection:" +
        oracle.getConnection().isLogicalConnection());
    GraphOracleSem g = new GraphOracleSem(oracle, szModelName, szUser, szNetworkName);
    g.add(Triple.create(
          NodeFactory.createURI("u:John"), NodeFactory.createURI("u:parentOf"), NodeFactory.createURI("u:Mary")));
    g.close();
    // return the Oracle back to the pool
    oracle.dispose();
    
    // grab another Oracle and do something else 
    System.out.println("Getting an Oracle from OraclePool");
    oracle = op.getOracle();
    System.out.println("Done");
    System.out.println("Is logical connection:" +
        oracle.getConnection().isLogicalConnection());
    g = new GraphOracleSem(oracle, szModelName, szUser, szNetworkName);
    g.add(Triple.create(
          NodeFactory.createURI("u:John"), NodeFactory.createURI("u:parentOf"), NodeFactory.createURI("u:Jack")));
    g.close();
    
    OracleUtils.dropSemanticModel(oracle, szModelName, szUser, szNetworkName); 
    
    // return the Oracle back to the pool
    oracle.dispose();
  }
}
