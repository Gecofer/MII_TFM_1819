import java.io.*;
import java.util.*;
import org.apache.jena.graph.*;
import org.apache.jena.update.*;

import oracle.spatial.rdf.client.jena.*;

import oracle.spatial.rdf.client.jena.SemNetworkAnalyst;
import oracle.spatial.network.lod.LODGoalNode;
import oracle.spatial.network.lod.LODNetworkConstraint;
import oracle.spatial.network.lod.NetworkAnalyst;
import oracle.spatial.network.lod.PointOnNet;
import oracle.spatial.network.lod.LogicalSubPath;

/*
 *  alwu      07/31/12 - update to Apache Jena 2.7.2: DataSourceImpl => DatasetImpl
 */

/**
 * This class implements a nearestNeighbors function on top of semantic data
 * using public APIs provided in SemNetworkAnalyst and Oracle Spatial NDM
 */
public class TestNearestNeighbor
{
  public static void main(String[] args) throws Exception
  {
    String szJdbcURL = args[0];
    String szUser    = args[1];
    String szPasswd  = args[2];

    PrintStream psOut = System.out;

    Oracle oracle = new Oracle(szJdbcURL, szUser, szPasswd);
    
    String szModelName = "test_nn";
    // First construct a TBox and load a few axioms
    ModelOracleSem model = ModelOracleSem.createOracleSemModel(oracle, szModelName);
    String insertString =  
      " PREFIX my:  <http://my.com/> " +
      " INSERT DATA "                             +
      " { my:A   my:likes my:B .                " +
      "   my:A   my:likes my:C .                " +
      "   my:A   my:knows my:D .                " +
      "   my:A   my:dislikes my:X .             " +
      "   my:A   my:dislikes my:Y .             " +
      "   my:C   my:likes my:E .                " +
      "   my:C   my:likes my:F .                " +
      "   my:C   my:dislikes my:M .             " +
      "   my:D   my:likes my:G .                " +
      "   my:D   my:likes my:H .                " +
      "   my:F   my:likes my:M .                " +
      " }   ";
    UpdateAction.parseExecute(insertString,  model);

    GraphOracleSem g = model.getGraph();
    g.commitTransaction();
    g.setDOP(4);

    HashMap<Node, Double> costMap = new HashMap<Node, Double>();
    costMap.put(NodeFactory.createURI("http://my.com/likes"), Double.valueOf(1.0));
    costMap.put(NodeFactory.createURI("http://my.com/dislikes"), Double.valueOf(4.0));
    costMap.put(NodeFactory.createURI("http://my.com/knows"), Double.valueOf(2.0));

    SemNetworkAnalyst sna = SemNetworkAnalyst.getInstance(
        g,     // source RDF graph
        true,  // directed graph
        true,  // cleanup old Node/Link tables
        costMap
        );

    Node nodeStart = NodeFactory.createURI("http://my.com/A");
    long origNodeID = sna.getNodeID(nodeStart);

    long[] lIDs = {origNodeID};

    // translate from the original ID
    long nodeID = (sna.mapNodeIDs(lIDs))[0]; 

    NetworkAnalyst networkAnalyst = sna.getEmbeddedNetworkAnalyst();

    LogicalSubPath[] lsps = networkAnalyst.nearestNeighbors(
      new PointOnNet(nodeID),      // startPoint
      6,                           // numberOfNeighbors
      1,                           // searchLinkLevel
      1,                           // targetLinkLevel
      (LODNetworkConstraint) null, // constraint
      (LODGoalNode) null           // goalNodeFilter
      );

    if (lsps != null) {
      for (int idx = 0; idx < lsps.length; idx++) {
        LogicalSubPath lsp = lsps[idx];
        Node[] nodePath = sna.processLogicalSubPath(lsp, nodeStart);
        psOut.println("Path " + idx);
        printNodeArray(nodePath, psOut);
      }
    }

    g.close();
    sna.close();
    oracle.dispose();
  }


  public static void printNodeArray(Node[] nodeArray, PrintStream psOut)
  {
    if (nodeArray == null) {
      psOut.println("Node Array is null");
      return;
    }

    if (nodeArray.length == 0) {
      psOut.println("Node Array is empty");
    }

    int iFlag = 0;

    psOut.println("printNodeArray: full path starts");
    for (int iHops = 0; iHops < nodeArray.length; iHops++) {
      psOut.println("printNodeArray: full path item " + iHops + " = "
          + ((iFlag == 0) ? "[n] ":"[e] ") + nodeArray[iHops]);
      iFlag = 1 - iFlag;
    }
  }
}
