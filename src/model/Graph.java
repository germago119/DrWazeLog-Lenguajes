package model;

import GUI.LinkingLine;
import GUI.Node;

import java.util.ArrayList;

/**
 * A Class that is a graph with a list for nodes and arcs.
 */
public class Graph {
  public static ArrayList<Node> nodes = new ArrayList<>();
  public static ArrayList<LinkingLine> vertexes = new ArrayList<>();
  
  /**
   * @param start node at the start of the street
   * @param end   node at the end of the street
   * @return the LinkingLine(arc) between two nodes
   */
  public static LinkingLine getArcs (Node start, Node end) {
    for (LinkingLine vertex : vertexes) {
      if (vertex.start.equals(start) && vertex.end.equals(end)) {
        return vertex;
      }
    }
    return null;
  }
  
  /**
   * It gets all the arcs to a route
   *
   * @param ways list of nodes of a route
   * @return a list that matches arcs to the route
   */
  public static ArrayList<LinkingLine> getLines (ArrayList<Node> ways) {
    int x = ways.size();
    int i = 0;
    ArrayList<LinkingLine> linkingLines = new ArrayList<>();
    while (i < x - 1) {
      linkingLines.add(getArcs(ways.get(i), ways.get(i + 1)));
      i++;
    }
    return linkingLines;
  }
  
  /**
   * Retrieves all nodes' names except the argument
   * @param toIgnore node name to be discarded
   * @return An arraylist with almost all nodes' names
   */
  public static ArrayList<String> getNames(String toIgnore) {
    ArrayList<String> arrayList = new ArrayList<>();
    for (Node node : nodes) {
      if (! node.getnode_name().equals(toIgnore)) {
        arrayList.add(node.getnode_name());
      }
    }
    return arrayList;
  }
  
  /**
   * It verifies if a node already exists
   * @param name Node name to be compared
   * @return false if it does not find it, true otherwise
   */
  public static boolean exists(String name) {
    name = name.toLowerCase();
    name = name.replaceAll("\\s+", "");
    for (Node node : nodes) {
      if (node.getnode_name().equals(name)) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Receives a node's name and return the node
   * @param name Node's name
   * @return a Node with the matching name
   */
  public static Node getNode(String name) {
    int i = 0;
    while (i != nodes.size()) {
      if (nodes.get(i).getnode_name().equals(name)) {
        return nodes.get(i);
      }
      i++;
    }
    return null;
  }
  
 
}
