package model;

import GUI.Node;
import GUI.linkingLine;

import java.util.ArrayList;

public class Graph {
  public static ArrayList<Node> nodes = new ArrayList<>();
  public static ArrayList<linkingLine> vertexes = new ArrayList<>();

  public Graph() {
    nodes = new ArrayList<Node>();
    vertexes = new ArrayList<linkingLine>();
  }

  public static ArrayList<String> getNames(String toIgnore) {
    ArrayList<String> lista = new ArrayList<>();
    for (int i = 0; i < nodes.size(); i++) {
      if (!nodes.get(i).getnode_name().equals(toIgnore)) {
        lista.add(nodes.get(i).getnode_name());
      }
    }
    return lista;
  }

  public static boolean exists(String name) {
    name = name.toLowerCase();
    name = name.replaceAll("\\s+", "");
    for (int i = 0; i < nodes.size(); i++) {
      if (nodes.get(i).getnode_name().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public static Node getNode(String name) {
    for (int i = 0; i != nodes.size(); i++) {
      if (nodes.get(i).getnode_name().equals(name)) {
        return nodes.get(i);
      }
    }
    return null;
  }

  public static linkingLine getArcs(Node node1, Node node2) {
    for (int i = 0; i < vertexes.size(); i++) {
      if (vertexes.get(i).start.equals(node1) && vertexes.get(i).end.equals(node2)) {
        return vertexes.get(i);
      }
    }
    return null;
  }

  public static ArrayList<linkingLine> getLines(ArrayList<Node> ways) {
    int x = ways.size();
    int i = 0;
    ArrayList<linkingLine> conectoras = new ArrayList<>();
    while (i < x - 1) {
      conectoras.add(getArcs(ways.get(i), ways.get(i + 1)));
      i++;
    }
    return conectoras;
  }
}
