package GUI;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.Graph;

import java.util.Random;

public class LinkingLine {
  int weight;
  public Node start;
  public Node end;
  public Label weightLabel;
  Line linea;
  
  LinkingLine (Node start, Node end, int weight, Label weightLabel, Line line) {
    this.start = start;
    this.end = end;
    this.weight = weight;
    this.weightLabel = weightLabel;
    this.linea = line;
    Graph.vertexes.add(this);
    this.start.followers.add(end);
    Random rand = new Random();
    line.setStroke(Color.rgb(rand.nextInt(100), rand.nextInt(100), rand.nextInt(100)));
    if (Graph.getArcs(end, start) != null) {
      weightLabel
          .layoutYProperty()
          .bind(
              this.start.ring.centerYProperty().add(end.ring.centerYProperty()).divide(2).add(-20));
      weightLabel
          .layoutXProperty()
          .bind(
              this.start.ring.centerXProperty().add(end.ring.centerXProperty()).divide(2).add(-20));
    } else {
      weightLabel
          .layoutYProperty()
          .bind(
              this.start.ring.centerYProperty().add(end.ring.centerYProperty()).divide(2).add(20));
      weightLabel
          .layoutXProperty()
          .bind(
              this.start.ring.centerXProperty().add(end.ring.centerXProperty()).divide(2).add(20));
    }
  }
}
