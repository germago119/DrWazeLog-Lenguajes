package GUI;

import Link.PLManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import model.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class Node {
  public Circle ring;
  public Label label;
  public double orgSceneX, orgSceneY;
  public ArrayList<Node> followers = new ArrayList<>();
  private String nodeName;
  private EventHandler<MouseEvent> mouseDraggedEventHandler =
      (event) -> {
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;

        Circle tempRing = this.ring;

        tempRing.setCenterX(tempRing.getCenterX() + offsetX);
        tempRing.setCenterY(tempRing.getCenterY() + offsetY);
        label.setLayoutY(tempRing.getCenterY() - 10);
        label.setLayoutX(tempRing.getCenterX() - 30);
        label.toFront();

        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
      };
  /**
   * Este es el evento que determina cuando es presionado un nodo, lo cual lo manda hacia en frente
   * del scene
   */
  private EventHandler<MouseEvent> mousePressedEventHandler =
      (event) -> {
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
        Circle tempRing = this.ring;
        tempRing.toFront();
        label.toFront();
      };
  // Test
  private EventHandler<ActionEvent> newPlacesEvent =
      (t) -> {
        Dialog<newInput> dialog = new Dialog<>();
        dialog.setTitle("Add a new Place");
        dialog.setHeaderText("Please input data");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField = new TextField("Place Name");
        TextField textField2 = new TextField("Km Distance");
        dialogPane.setContent(new VBox(8, textField, textField2));
        dialog.setResultConverter(
            (ButtonType button) -> {
              if (button == ButtonType.OK) {
                return new newInput(textField.getText(), this.nodeName, textField2.getText());
              } else {
                return null;
              }
            });

        Optional<newInput> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent(
            (newInput results) -> {
              int x;
              try {
                x = Integer.parseInt(results.weight);
              } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Kilometers");
                alert.setHeaderText("Error->KM Number");
                alert.setContentText("Kilometers Need to be an Integer");
                alert.showAndWait();
                return;
              }
              if (results.weight == null || results.weight == "Km camino" || results.weight == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Null Number");
                alert.setHeaderText("Error->KM Number");
                alert.setContentText("Need to Input a Number");
                alert.showAndWait();
                return;
              }
              if (results.newName.isEmpty()
                  || results.newName == "Nombre Lugar"
                  || results.newName == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Null Name");
                alert.setHeaderText("Need to Input a Name");
                alert.showAndWait();
                return;
              }
              if (Graph.exists(results.newName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Already Exist");
                alert.setHeaderText("Node already exists the program is not case sensitive");
                alert.showAndWait();
                return;
              }
              PLManager plManager = new PLManager();
              try {

                String nodo = results.newName.toLowerCase();
                nodo = nodo.replaceAll("\\s+", "");
                UINodesFactory.createNode(nodo);
                plManager.addArcs(nodo, results.endName, x);
                plManager.addPlaces(nodo);
                UINodesFactory.createLine(Graph.getNode(nodo), Graph.getNode(results.endName), x);

              } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File NOT Found");
                alert.setHeaderText("Prolog's PL file missing");
                alert.setContentText("Prolog file has problems");
                alert.showAndWait();
                return;
              }
            });
      };
  /**
   * Este tambien es un evento y lo que hace es seleccionar el nodo que fue tocado y lo marca como
   * un end
   */
  private EventHandler<ActionEvent> nodeSelectedEndEvent =
      (t) -> {
        PLManager plManager = new PLManager();
        drawNodes.end = this;
        if (drawNodes.start != null && drawNodes.end != null) {
          ArrayList<String> camino =
              plManager.getaWay(
                  drawNodes.start.getnode_name(), "[" + drawNodes.end.getnode_name() + "]");
          if (camino == null) {
            if (drawNodes.start.equals(drawNodes.end)) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Error");
              alert.setHeaderText(null);
              alert.setContentText("Error Start Node can not be the same as End Node");
              alert.showAndWait();
              drawNodes.end = null;
              drawNodes.start = null;
              drawNodes.reset();
              return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There is no way between these Nodes");

            alert.showAndWait();
            drawNodes.end = null;
            drawNodes.start = null;
            drawNodes.reset();
            return;
          }
          if (drawNodes.start.equals(drawNodes.end)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error Start Node can not be the same as End Node");

            alert.showAndWait();
            drawNodes.end = null;
            drawNodes.start = null;

            drawNodes.reset();
            return;
          }

          drawNodes.drawTheWay(camino);
        }
      };
  /** Este evento lo que hace es definir el nodo como un start */
  private EventHandler<ActionEvent> nodeOriginEvent =
      (t) -> {
        PLManager plManager = new PLManager();
        drawNodes.start = this;
        if (drawNodes.start != null && drawNodes.end != null) {

          ArrayList<String> camino =
              plManager.getaWay(
                  drawNodes.start.getnode_name(), "['" + drawNodes.end.getnode_name() + "']");
          if (camino == null) {
            if (drawNodes.end.equals(drawNodes.start)) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Error");
              alert.setHeaderText(null);
              alert.setContentText("Error Start Node can not be the same as End Node");

              alert.showAndWait();
              drawNodes.reset();
              drawNodes.end = null;
              drawNodes.start = null;
              drawNodes.reset();
              return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There is no way between these Nodes");
            alert.showAndWait();
            drawNodes.end = null;
            drawNodes.start = null;
            drawNodes.reset();
            return;
          }
          if (drawNodes.start.equals(drawNodes.end)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error Start Node can not be the same as End Node");
            alert.showAndWait();
            drawNodes.end = null;
            drawNodes.start = null;
            drawNodes.reset();
            return;
          }
          if (camino == null) {
            return;
          }
          drawNodes.drawTheWay(camino);
        }
      };
  /** Esta lo que hace es crear una calle entre dos destinos, genera la ventana de seleccion */
  private EventHandler<ActionEvent> newStreetEvent =
      (t) -> {
        ArrayList<String> nodesNameList = Graph.getNames(this.nodeName);
        ObservableList<String> options = FXCollections.observableArrayList(nodesNameList);
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().selectFirst();
        Dialog<newInput> dialog = new Dialog<>();
        dialog.setTitle("Add a new Street");
        dialog.setHeaderText("Select an END node and type the distance in km");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField2 = new TextField("Km street");
        dialogPane.setContent(new VBox(8, textField2, comboBox));
        dialog.setResultConverter(
            (ButtonType button) -> {
              if (button == ButtonType.OK) {
                return new newInput(this.nodeName, comboBox.getValue(), textField2.getText());
              }
              return null;
            });
        Optional<newInput> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent(
            (newInput results) -> {
              int x;
              try {
                x = Integer.parseInt(results.weight);
              } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Kilometers");
                alert.setHeaderText("Error->KM Number");
                alert.setContentText("Kilometers Need to be an Integer");
                alert.showAndWait();
                return;
              }

              if (Graph.getArcs(Graph.getNode(results.newName), Graph.getNode(results.endName))
                  != (null)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Street already exists");
                alert.showAndWait();
                return;
              }
              if (results.weight == null || results.weight == "Km camino" || results.weight == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Null Number");
                alert.setHeaderText("Error->KM Number");
                alert.setContentText("Need to Input a Number");
                alert.showAndWait();
                return;
              }
              PLManager plManager = new PLManager();
              try {
                String node = results.newName.toLowerCase();
                node = node.replaceAll("\\s+", "");
                plManager.addArcs(results.newName, results.endName, x);
                UINodesFactory.createLine(Graph.getNode(node), Graph.getNode(results.endName), x);

              } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File NOT Found");
                alert.setHeaderText("Prolog's PL file missing");
                alert.setContentText("Prolog file has problems");
                alert.showAndWait();
                return;
              }
            });
      };
  private EventHandler<ActionEvent> showAllStreetsEvent =
      (t) -> {
        VBox box = new VBox(8);
        Dialog<newInput> dialog = new Dialog<>();
        dialog.setTitle("Streets ");
        dialog.setHeaderText("These are the different streets available");

        for (int x = 0; x < Graph.vertexes.size(); x++) {
          String startName = Graph.vertexes.get(x).start.getnode_name();
          String endName = Graph.vertexes.get(x).end.getnode_name();
          String km = Integer.toString(Graph.vertexes.get(x).weight);
          Label label = new Label("Start: " + startName + " End: " + endName + " " + km + "km");
          box.getChildren().addAll(label);
        }
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialogPane.setContent(box);
        dialog.showAndWait();
      };
  /** Esta lo que hace es desplegar el menu de contexto */
  private EventHandler<ContextMenuEvent> contextHandler =
      (event) -> {
        ContextMenu contextMenu = contextMenuInit();
        contextMenu.show(this.ring, event.getScreenX(), event.getScreenY());
      };
  public Node(String text, Circle ring, Label label) {

    this.label = label;
    this.nodeName = text;
    this.ring = ring;
    Graph.nodes.add(this);
    this.ring.setOnMousePressed(mousePressedEventHandler);
    this.ring.setOnContextMenuRequested(contextHandler);
    this.ring.setOnMouseDragged(mouseDraggedEventHandler);
    this.label.setOnMousePressed(mousePressedEventHandler);
    this.label.setOnContextMenuRequested(contextHandler);
    this.label.setOnMouseDragged(mouseDraggedEventHandler);
  }

  public String getnode_name() {
    return nodeName;
  }

  /**
   * Esta lo que hace es crear un menucontexto
   *
   * @return
   */
  public ContextMenu contextMenuInit() {
    ContextMenu contextMenu = new ContextMenu();
    MenuItem menu1 = new MenuItem("Select Start Node");
    menu1.setOnAction(nodeOriginEvent);
    MenuItem menu2 = new MenuItem("Select End Node");
    menu2.setOnAction(nodeSelectedEndEvent);
    MenuItem menu3 = new MenuItem("Add a new place");
    menu3.setOnAction(newPlacesEvent);
    MenuItem menu4 = new MenuItem("Add a Street");
    menu4.setOnAction(newStreetEvent);
    menu2.setOnAction(nodeSelectedEndEvent);
    MenuItem menu5 = new MenuItem("Streets");
    menu5.setOnAction(showAllStreetsEvent);
    contextMenu.getItems().addAll(menu1, menu2, menu3, menu4, menu5);
    return contextMenu;
  }
}
