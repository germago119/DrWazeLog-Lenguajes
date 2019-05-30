package Link;

import org.jpl7.Query;
import org.jpl7.Term;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class PLManager {

  private ArrayList<String> cleanData(String prologOut) {
    int length = prologOut.length();
    int i = 0;
    String tempChar = "";
    ArrayList<String> result = new ArrayList<>();
    while (length != i) {
      String tempChar2 = Character.toString(prologOut.charAt(i));
      {
        if (tempChar2.equals(" ")) {
          result.add(tempChar);
          tempChar = "";
        } else tempChar += tempChar2;
      }
      i++;
    }
    return result;
  }

  private String getData(String prologOut) {
    int length = prologOut.length();
    int i = 0;
    String result = "";
    while (length != i) {
      String tempChar = Character.toString(prologOut.charAt(i));
      {
        if (tempChar.equals("(")
            || tempChar.equals(")")
            || tempChar.equals("|")
            || tempChar.equals(",")
            || tempChar.equals("[")
            || tempChar.equals("]")) result += "";
        else result += tempChar;
      }
      i++;
    }
    return result;
  }

  public ArrayList<String> getPlaces() {
    String prologPl = "consult('Lugares.pl')"; // consulta prolog
    Query query1 = new Query(prologPl);
    System.out.println(prologPl + (query1.hasSolution() ? "Connected" : "Connection Failed"));
    ArrayList<String> result = new ArrayList<>();
    String places = "lugares(X)";
    Query query2 = new Query(places);
    Map<String, Term>[] allSolutions = query2.allSolutions(); // obtiene todas las soluciones
    for (int i = 0; i < allSolutions.length; i++) {
      result.add(getData(allSolutions[i].get("X").toString()));
    }
    System.out.println(result);
    return result;
  }

  public ArrayList<ArrayList<String>> getArcs() {
    String prologPl = "consult('arcos.pl')"; // consulta prolog
    Query query1 = new Query(prologPl);
    System.out.println(prologPl + (query1.hasSolution() ? "Connected" : "Connection Failed"));
    ArrayList<ArrayList<String>> result = new ArrayList<>();
    String arc = "arco(X,Y,Z)";
    Query query2 = new Query(arc);
    Map<String, Term>[] allSolutions = query2.allSolutions();
    for (int i = 0; i < allSolutions.length; i++) {
      ArrayList<String> temp = new ArrayList<>(); // crea un array con los datos de cada arco
      temp.add(getData(allSolutions[i].get("X").toString()));
      temp.add(getData(allSolutions[i].get("Y").toString()));
      temp.add(getData(allSolutions[i].get("Z").toString()));
      result.add(temp);
    }
    System.out.println(result);
    return result;
  }

  public void addPlaces(String place) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("lugares.pl", true))) {
      String newPlace = "\n" + "lugares" + "(" + place.toLowerCase() + ").";
      writer.append(newPlace);
    }
  }

  public void addArcs(String start, String destiny, int distance) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("arcos.pl", true))) {
      String newArc =
          "\n"
              + "arco("
              + start.toLowerCase()
              + ","
              + destiny.toLowerCase()
              + ","
              + distance
              + ").";
      writer.append(newArc);
    }
  }

  public ArrayList<String> getaWay(String start, String end) {
    try {
      String prologPl = "consult('Logica.pl')"; // consulta prolog
      Query query1 = new Query(prologPl);
      System.out.println(prologPl + (query1.hasSolution() ? "Connected" : "Connection Failed"));
      ArrayList<String> result = new ArrayList<>();
      String path = "java(" + start + ","  + end + ",[],0,R,Y)";
      Query query2 = new Query(path);
      Map<String, Term> data = query2.oneSolution(); // recibe un solucion
      System.out.println(path);
      System.out.println(data);
      result = cleanData(getData(data.get("R").toString()));
      System.out.println("Soy este: "+result);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
