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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class Node {
  public Circle ring;
  public Label label;
    public double posSceneX, posSceneY;
  public ArrayList<Node> followers = new ArrayList<>();
  private String nodeName;
   
   /**Handles drag position updates
    */
  private EventHandler<MouseEvent> mousePressedEventHandler =
      (event) -> {
          posSceneX = event.getSceneX();
          posSceneY = event.getSceneY();
        Circle tempRing = this.ring;
        tempRing.toFront();
        label.toFront();
      };
  
  /**Creates the window for adding new places
    */
  private EventHandler<ActionEvent> newPlacesEvent =
      (t) -> {
          Dialog<NewInput> dialog = new Dialog<>();
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
                  return new NewInput(textField.getText(), this.nodeName, textField2.getText());
              } else {
                return null;
              }
            });
    
          Optional<NewInput> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent(
                (NewInput results) -> {
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
                    if (results.weight == null
                                || results.weight.equals("Km camino")
                                || results.weight.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Null Number");
                alert.setHeaderText("Error->KM Number");
                alert.setContentText("Need to Input a Number");
                alert.showAndWait();
                return;
              }
                    if (results.newName.isEmpty() || results.newName.equals("Nombre Lugar")) {
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
                  UINodesFactory.createLine(
                          Objects.requireNonNull(Graph.getNode(nodo)),
                          Objects.requireNonNull(Graph.getNode(results.endName)),
                          x);

              } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File NOT Found");
                alert.setHeaderText("Prolog's PL file missing");
                alert.setContentText("Prolog file has problems");
                alert.showAndWait();
              }
            });
      };
  
    /**It manages the node selected as end of the route
    */
    private EventHandler<ActionEvent> nodeSelectedEndEvent =
          t -> {
        PLManager plManager = new PLManager();
              DrawNodes.end = this;
              if (DrawNodes.start != null) {
                  ArrayList<String> route =
                          plManager.getaWay(DrawNodes.start.getnode_name(), DrawNodes.end.getnode_name());
                  if (route == null) {
                      if (DrawNodes.start.equals(DrawNodes.end)) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Error");
              alert.setHeaderText(null);
              alert.setContentText("Error Start Node can not be the same as End Node");
              alert.showAndWait();
                          DrawNodes.end = null;
                          DrawNodes.start = null;
                          DrawNodes.reset();
              return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There is no way between these Nodes");

            alert.showAndWait();
                      DrawNodes.end = null;
                      DrawNodes.start = null;
                      DrawNodes.reset();
            return;
          }
                  if (DrawNodes.start.equals(DrawNodes.end)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error Start Node can not be the same as End Node");

            alert.showAndWait();
                      DrawNodes.end = null;
                      DrawNodes.start = null;
                    
                      DrawNodes.reset();
            return;
          }
                
                  DrawNodes.drawTheWay(route);
        }
      };
    
    /**It manages the node selected as origin for the route and other functions
     */
  private EventHandler<ActionEvent> nodeOriginEvent =
          t -> {
        PLManager plManager = new PLManager();
              DrawNodes.start = this;
              if (DrawNodes.end != null) {
                
                  ArrayList<String> route =
                          plManager.getaWay(DrawNodes.start.getnode_name(), DrawNodes.end.getnode_name());
                  if (route == null) {
                      if (DrawNodes.end.equals(DrawNodes.start)) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Error");
              alert.setHeaderText(null);
              alert.setContentText("Error Start Node can not be the same as End Node");

              alert.showAndWait();
                          DrawNodes.reset();
                          DrawNodes.end = null;
                          DrawNodes.start = null;
                          DrawNodes.reset();
              return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("There is no way between these Nodes");
            alert.showAndWait();
                      DrawNodes.end = null;
                      DrawNodes.start = null;
                      DrawNodes.reset();
            return;
          }
                  if (DrawNodes.start.equals(DrawNodes.end)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error Start Node can not be the same as End Node");
            alert.showAndWait();
                      DrawNodes.end = null;
                      DrawNodes.start = null;
                      DrawNodes.reset();
            return;
          }
                  DrawNodes.drawTheWay(route);
        }
      };
    
    /**It manages the window for creating a new street
     */
  private EventHandler<ActionEvent> newStreetEvent =
          t -> {
        ArrayList<String> nodesNameList = Graph.getNames(this.nodeName);
        ObservableList<String> options = FXCollections.observableArrayList(nodesNameList);
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().selectFirst();
              Dialog<NewInput> dialog = new Dialog<>();
        dialog.setTitle("Add a new Street");
        dialog.setHeaderText("Select an END node and type the distance in km");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField2 = new TextField("Km street");
        dialogPane.setContent(new VBox(8, textField2, comboBox));
        dialog.setResultConverter(
            (ButtonType button) -> {
              if (button == ButtonType.OK) {
                  return new NewInput(this.nodeName, comboBox.getValue(), textField2.getText());
              }
              return null;
            });
              Optional<NewInput> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent(
                (NewInput results) -> {
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
                    if (results.weight == null
                                || results.weight.equals("Km camino")
                                || results.weight.equals("")) {
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
                  UINodesFactory.createLine(
                          Objects.requireNonNull(Graph.getNode(node)),
                          Objects.requireNonNull(Graph.getNode(results.endName)),
                          x);

              } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File NOT Found");
                alert.setHeaderText("Prolog's PL file missing");
                alert.setContentText("Prolog file has problems");
                alert.showAndWait();
              }
            });
      };
    
    /**It manages the window that shows all the streets
     */
  private EventHandler<ActionEvent> showAllStreetsEvent =
          t -> {
        VBox box = new VBox(8);
              Dialog<NewInput> dialog = new Dialog<>();
        dialog.setTitle("Streets ");
        dialog.setHeaderText("These are the different streets available");
            
              IntStream.range(0, Graph.vertexes.size()).forEach(x -> {
                  String startName = Graph.vertexes.get(x).start.getnode_name();
                  String endName = Graph.vertexes.get(x).end.getnode_name();
                  String km = Integer.toString(Graph.vertexes.get(x).weight);
                  Label label = new Label("Start: " + startName + " End: " + endName + " " + km + "km");
                  box.getChildren().addAll(label);
              });
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialogPane.setContent(box);
        dialog.showAndWait();
      };
    
    /**It manages the context menu itself
     */
  private EventHandler<ContextMenuEvent> contextHandler =
      (event) -> {
        ContextMenu contextMenu = contextMenuInit();
        contextMenu.show(this.ring, event.getScreenX(), event.getScreenY());
      };
    
    /**Constructor for the class
     * @param text Node's name
     * @param ring JavaFX Circle
     * @param label Text inside Circle that shows the node Name
     */
    public Node(String text, Circle ring, Label label) {

    this.label = label;
    this.nodeName = text;
    this.ring = ring;
    Graph.nodes.add(this);
    this.ring.setOnMousePressed(mousePressedEventHandler);
    this.ring.setOnContextMenuRequested(contextHandler);
        EventHandler<MouseEvent> mouseDraggedEventHandler = event -> {
            double offsetX = event.getSceneX() - posSceneX;
            double offsetY = event.getSceneY() - posSceneY;
            
            Circle tempRing = this.ring;
            
            tempRing.setCenterX(tempRing.getCenterX() + offsetX);
            tempRing.setCenterY(tempRing.getCenterY() + offsetY);
            label.setLayoutY(tempRing.getCenterY() - 10);
            label.setLayoutX(tempRing.getCenterX() - 30);
            label.toFront();
            
            posSceneX = event.getSceneX();
            posSceneY = event.getSceneY();
        };
        this.ring.setOnMouseDragged(mouseDraggedEventHandler);
    this.label.setOnMousePressed(mousePressedEventHandler);
    this.label.setOnContextMenuRequested(contextHandler);
    this.label.setOnMouseDragged(mouseDraggedEventHandler);
  }
    
    /**@return the Node Object name
     */
  public String getnode_name() {
    return nodeName;
  }
    
    /** Creates the context Menu and adds the menu items
     */
  public ContextMenu contextMenuInit() {
    ContextMenu contextMenu = new ContextMenu();
      MenuItem selectStartNode = new MenuItem("Select Start Node");
      selectStartNode.setOnAction(nodeOriginEvent);
      MenuItem selectEndNode = new MenuItem("Select End Node");
      selectEndNode.setOnAction(nodeSelectedEndEvent);
      MenuItem addANewPlace = new MenuItem("Add a new place");
      addANewPlace.setOnAction(newPlacesEvent);
      MenuItem addAStreet = new MenuItem("Add a Street");
      addAStreet.setOnAction(newStreetEvent);
      selectEndNode.setOnAction(nodeSelectedEndEvent);
      MenuItem showAllStreets = new MenuItem("Show All Streets");
      showAllStreets.setOnAction(showAllStreetsEvent);
      contextMenu.getItems().addAll(addANewPlace, addAStreet, selectStartNode, selectEndNode, showAllStreets);
    return contextMenu;
  }
}
