package GUI;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;

public class UINodesFactory {
  public static Pane mainPane;

  public static void setMainPane(Pane canvas) {
    mainPane = canvas;
  }
  
  /**
   * Creates a line that connects two nodes
   * @param start first node
   * @param end last node
   * @param weight distance for the graph
   * @return a line to be displayed
   */
  public static LinkingLine createLine (Node start, Node end, int weight) {
    Label label = new Label(Integer.toString(weight));
    Line line = new Line();
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetY(3.0f);
    dropShadow.setColor(Color.WHITE);
    label.setEffect(dropShadow);
    line.startXProperty().bind(start.ring.centerXProperty());
    line.startYProperty().bind(start.ring.centerYProperty());
    line.endXProperty().bind(end.ring.centerXProperty());
    line.endYProperty().bind(end.ring.centerYProperty());
    line.setStrokeWidth(6);
    line.setStrokeLineCap(StrokeLineCap.BUTT);
    mainPane.getChildren().addAll(label, line);
    line.toBack();
    label.toFront();
    return new LinkingLine(start, end, weight, label, line);
  }
  
  /**
   * Creates a graph node with all the attributes it needs
   * @param name node's name
   * @param coordx x starting location
   * @param coordy y starting location
   * @return the Node
   */
  public static Node createNode(String name, int coordx, int coordy) {
    Circle ring =
        new Circle(
                coordx, coordy, 65, Color.web("#e7cc8f"));
    ring.setStroke(Color.web("#efaa52"));
    ring.setStrokeWidth(4);
    Label label = new Label(name);
    label.setTextFill(Color.BLACK);
    label.setFont(new Font(16));
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetY(7.0f);
    dropShadow.setColor(Color.WHITE);
    label.setEffect(dropShadow);
    ring.setEffect(dropShadow);
    mainPane.getChildren().addAll(ring, label);
    label.setLabelFor(ring);
    label.relocate(coordx - 30, coordy);
    return new Node(name, ring, label);
  }
  
  public static Node createNode(String name) {
    Circle ring =
            new Circle(
                    100, 100, 65, Color.web("#e7cc8f"));
    ring.setStroke(Color.web("#efaa52"));
    ring.setStrokeWidth(4);
    Label label = new Label(name);
    label.setTextFill(Color.BLACK);
    label.setFont(new Font(16));
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetY(7.0f);
    dropShadow.setColor(Color.WHITE);
    label.setEffect(dropShadow);
    ring.setEffect(dropShadow);
    mainPane.getChildren().addAll(ring, label);
    label.setLabelFor(ring);
    label.relocate(70, 100);
    
    return new Node(name, ring, label);
  }
}
