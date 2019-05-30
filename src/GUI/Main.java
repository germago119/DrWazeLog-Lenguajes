package GUI;

import Link.PLManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.GraphFiller;

import java.io.IOException;

public class Main extends Application {
  public static void main(String[] args) throws IOException {
      PLManager n = new PLManager();
      n.getaWay("sanjose", "[turrialba,orosi]");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("DrWazeLog");
    Pane pane = new Pane();
    UINodesFactory.setMainPane(pane);
    Scene scene = new Scene(pane, 1280, 720);
    pane.setBackground(
        new Background(new BackgroundFill(Color.LIGHTCORAL, CornerRadii.EMPTY, Insets.EMPTY)));
    primaryStage.setScene(scene);
    GraphFiller.GraphFiller();
    primaryStage.show();
  }
}
