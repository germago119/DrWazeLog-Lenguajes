package model;

import GUI.Node;
import GUI.UINodesFactory;
import Link.PLManager;

import java.util.ArrayList;
import java.util.Objects;

public class GraphFiller {

  public static PLManager plManager = new PLManager();
  
  /**
   * It uses plManager to access Prolog and fill the graph with that data.
   */
  public static void GraphFiller() {
    
    ArrayList<Integer> integers = new ArrayList<>();
    integers.add(5);
    integers.add(10);
    integers.add(15);
    integers.add(20);
    integers.add(25);
    integers.add(30);
    ArrayList<String> nodesList = plManager.getPlaces();
    
    int coordX = 50;
    int coordY = 50;
    for (int i = 0; i < nodesList.size(); i++) {
      if (integers.contains(i)) {
        coordX = 50;
        coordY = coordY + 200;
      }
      String name = nodesList.get(i);
      name = name.replaceAll("\\s+", "");
      System.out.println(nodesList.get(i));
      UINodesFactory.createNode(name, coordX, coordY);
      coordX = coordX + 150;
    }
    graphFillerLines(plManager.getArcs());
  }
  
  /**
   * It uses arcs' list to create the streets
   *
   * @param arcs all the arcs
   */
  private static void graphFillerLines (ArrayList<ArrayList<String>> arcs) {
    for (ArrayList<String> arc : arcs) {
      String startName = arc.get(0);
      startName = startName.replaceAll("\\s+", "");
      Node start = Graph.getNode(startName);
      String endName = arc.get(1);
      endName = endName.replaceAll("\\s+", "");
      Node end = Graph.getNode(endName);
      int weight = Integer.parseInt(arc.get(2));
      if (end != null) {
        UINodesFactory.createLine(Objects.requireNonNull(start), end, weight);
      }
    }
  }
}
