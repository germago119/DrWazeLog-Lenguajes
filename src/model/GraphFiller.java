package model;

import GUI.Node;
import GUI.UINodesFactory;
import Link.PLManager;

import java.util.ArrayList;

public class GraphFiller {

  public static PLManager plManager = new PLManager();

  public static void GraphFiller() {

    ArrayList<Integer> ints = new ArrayList<>();
    ints.add(5);
    ints.add(10);
    ints.add(15);
    ints.add(20);
    ints.add(25);
    ints.add(30);
    ArrayList<String> nodesList = plManager.getPlaces();

    int coord_x = 50;
    int coord_y = 50;
    for (int i = 0; i < nodesList.size(); i++) {
      if (ints.contains(i)) {
        coord_x = 50;
        coord_y = coord_y + 200;
      }
      String name = nodesList.get(i);
      name = name.replaceAll("\\s+", "");
      System.out.println(nodesList.get(i));
      UINodesFactory.createNode(name, coord_x, coord_y);
      coord_x = coord_x + 150;
    }
    GraphFillerLines(plManager.getArcs());
  }

  private static void GraphFillerLines(ArrayList<ArrayList<String>> arcs) {
    for (int i = 0; i < arcs.size(); i++) {
      String startName = arcs.get(i).get(0);
      startName = startName.replaceAll("\\s+", "");
      Node start = Graph.getNode(startName);
      String endName = arcs.get(i).get(1);
      endName = endName.replaceAll("\\s+", "");
      Node end = Graph.getNode(endName);
      int weight = Integer.parseInt(arcs.get(i).get(2));
      UINodesFactory.createLine(start, end, weight);
    }
  }
}
