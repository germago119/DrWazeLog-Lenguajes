package GUI;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.util.Random;

public class UINodesFactory {
  public static Pane mainPane;

  public static void setMainPane(Pane canvas) {
    mainPane = canvas;
  }

  public static Node createNode(String name, int coordx, int coordy) {
    Random rand = new Random();
    Circle ring =
        new Circle(
            coordx, coordy, 50, Color.rgb(rand.nextInt(100), rand.nextInt(100), rand.nextInt(100)));
    ring.setStroke(Color.rgb(rand.nextInt(100), rand.nextInt(100), rand.nextInt(100)));
    ring.setStrokeWidth(4);
    Label label = new Label(name);
    label.setTextFill(Color.WHITE);
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetY(7.0f);
    dropShadow.setColor(Color.BLACK);
    label.setEffect(dropShadow);
    ring.setEffect(dropShadow);
    mainPane.getChildren().addAll(ring, label);
    label.setLabelFor(ring);
    label.relocate(coordx - 30, coordy);
    Node node = new Node(name, ring, label);
    return node;
  }

  public static Node createNode(String name) {
    Random rand = new Random();
    Circle ring =
        new Circle(
            100, 100, 50, Color.rgb(rand.nextInt(100), rand.nextInt(100), rand.nextInt(100)));
    ring.setStroke(Color.rgb(rand.nextInt(100), rand.nextInt(100), rand.nextInt(100)));
    ring.setStrokeWidth(4);
    Label label = new Label(name);
    label.setTextFill(Color.WHITE);
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetY(7.0f);
    dropShadow.setColor(Color.BLACK);
    label.setEffect(dropShadow);
    ring.setEffect(dropShadow);
    mainPane.getChildren().addAll(ring, label);
    label.setLabelFor(ring);
    label.relocate(70, 100);

    Node node = new Node(name, ring, label);
    return node;
  }

  public static linkingLine createLine(Node start, Node end, int weight) {
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
    linkingLine linkingLine = new linkingLine(start, end, weight, label, line);
    return linkingLine;
  }
}
