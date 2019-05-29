package GUI;

import javafx.scene.paint.Color;
import model.Graph;

import java.util.ArrayList;
import java.util.Random;

public class drawNodes {
  public static Node end;
  public static Node start;
  public static ArrayList<Node> routes = new ArrayList<>();
  public static ArrayList<linkingLine> conectedLinesList;

  public static void drawTheWay(ArrayList<String> route) {
    if (conectedLinesList != null && !conectedLinesList.contains(null)) {
      reset();
    }
    routes = new ArrayList<>();
    for (int i = 0; i < route.size(); i++) {
      String target = route.get(i);
      target = target.replaceAll("\\s+", "");
      Node graphNode = Graph.getNode(target);
      graphNode.ring.setFill(Color.FORESTGREEN);
      routes.add(graphNode);
    }
    start = routes.get(0);
    end = routes.get(routes.size() - 1);
    ArrayList<linkingLine> lineas = Graph.getLines(routes);
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
    for (int i = 0; i < routes.size(); i++) {
      routes
          .get(i)
          .ring
          .setFill(Color.rgb(rand.nextInt(200), rand.nextInt(200), rand.nextInt(200)));
    }
    for (int i = 0; i < conectedLinesList.size(); i++) {
      conectedLinesList
          .get(i)
          .linea
          .setStroke(Color.rgb(rand.nextInt(200), rand.nextInt(200), rand.nextInt(200)));
    }
    conectedLinesList = null;
  }

  public static void drawLine(ArrayList<linkingLine> line) {
    System.out.println("Lines length is " + line.size());
    for (int i = 0; i < line.size(); i++) {
      line.get(i).linea.setStroke(Color.DEEPSKYBLUE);
      line.get(i).linea.toFront();
      line.get(i).end.ring.toFront();
      line.get(i).start.ring.toFront();
      line.get(i).start.label.toFront();
      line.get(i).end.label.toFront();
    }
  }
}
