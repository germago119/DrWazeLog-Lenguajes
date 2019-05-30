package GUI;

import javafx.scene.paint.Color;
import model.Graph;

import java.util.ArrayList;
import java.util.Random;

public class DrawNodes {
  public static Node end;
  public static Node start;
  public static ArrayList<Node> routes = new ArrayList<>();
  public static ArrayList<LinkingLine> conectedLinesList;

  public static void drawTheWay(ArrayList<String> route) {
    if (conectedLinesList != null && !conectedLinesList.contains(null)) {
      reset();
    }
    routes = new ArrayList<>();
    for (String target : route) {
      target = target.replaceAll("\\s+", "");
      Node graphNode = Graph.getNode(target);
      if (graphNode != null) {
        graphNode.ring.setFill(Color.FORESTGREEN);
      }
      routes.add(graphNode);
    }
    start = routes.get(0);
    end = routes.get(routes.size() - 1);
    ArrayList<LinkingLine> lineas = Graph.getLines(routes);
    conectedLinesList = lineas;
    System.out.println("Length is: " + routes.size());
    drawLine(lineas);
  }

  public static void reset() {
    if (conectedLinesList == null) {
      System.out.println("connectedLinesList IS NULL");
      return;
    }
    Random rand = new Random();
    for (Node route : routes) {
      route.ring.setFill(Color.rgb(rand.nextInt(200), rand.nextInt(200), rand.nextInt(200)));
    }
    for (LinkingLine linkingLine : conectedLinesList) {
      linkingLine.linea.setStroke(
              Color.rgb(rand.nextInt(200), rand.nextInt(200), rand.nextInt(200)));
    }
    conectedLinesList = null;
  }
  
  public static void drawLine (ArrayList<LinkingLine> line) {
    System.out.println("Lines length is " + line.size());
    for (LinkingLine linkingLine : line) {
      linkingLine.linea.setStroke(Color.DEEPSKYBLUE);
      linkingLine.linea.toFront();
      linkingLine.end.ring.toFront();
      linkingLine.start.ring.toFront();
      linkingLine.start.label.toFront();
      linkingLine.end.label.toFront();
    }
  }
}
