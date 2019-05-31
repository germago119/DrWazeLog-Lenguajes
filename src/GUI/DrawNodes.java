package GUI;

import javafx.scene.paint.Color;
import model.Graph;

import java.util.ArrayList;

public class DrawNodes {
  public static Node end;
  public static Node start;
  public static ArrayList<Node> routes = new ArrayList<>();
  public static ArrayList<LinkingLine> conectedLinesList;

  /**
   * Useas a list of LinkingLines to be drawn
   *
   * @param line
   */
  public static void drawLine(ArrayList<LinkingLine> line) {
    System.out.println("Lines length is " + line.size());
    for (LinkingLine linkingLine : line) {
      linkingLine.linea.setStroke(Color.web("#6cbf84"));
      linkingLine.linea.toFront();
      linkingLine.end.ring.toFront();
      linkingLine.start.ring.toFront();
      linkingLine.start.label.toFront();
      linkingLine.end.label.toFront();
    }
  }

  /** @param route a list with the name of nodes and streets that need to be drawn */
  public static void drawTheWay(ArrayList<String> route) {
    if (conectedLinesList != null && !conectedLinesList.contains(null)) {
      reset();
    }
    routes = new ArrayList<>();
    for (String target : route) {
      target = target.replaceAll("\\s+", "");
      Node graphNode = Graph.getNode(target);
      if (graphNode != null) {
        graphNode.ring.setFill(Color.web("#6cbf84"));
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

  /** Changes color when a new route is calculated */
  public static void reset() {
    if (conectedLinesList == null) {
      System.out.println("connectedLinesList IS NULL");
      return;
    }
    for (Node route : routes) {
      route.ring.setFill(Color.web("#709fb0"));
    }
    for (LinkingLine linkingLine : conectedLinesList) {
      linkingLine.linea.setStroke(Color.web("#726a95"));
    }
    conectedLinesList = null;
  }
 }
