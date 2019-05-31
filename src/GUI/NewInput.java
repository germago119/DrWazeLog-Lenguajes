package GUI;

class NewInput {
  String newName;
  String endName;
  String weight;
  
  /**
   * Creates a new input for the context menu
   * @param newName
   * @param endName
   * @param arc
   */
  NewInput (String newName, String endName, String arc) {
    this.newName = newName;
    this.endName = endName;
    this.weight = arc;
  }
}
