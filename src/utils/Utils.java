package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

  /**
   * Une fonction utilitaire pour fusionner deux tableaux de String en un seule. Retourne un tableau
   * de string (non-triée) contenant les données des deux tableaux passées en paramètres.
   * 
   * @param firstArray - le premier tableau de String
   * @param secondArray - le deuxième tableau de String
   * @return - un tableau de String contenant les données des tableaux <b>firstArray</b> et
   *         <b>secondArray</b>
   */
  public static String[] mergeTables(String[] firstArray, String[] secondArray) {
    int m = firstArray.length;
    int n = secondArray.length;

    String[] mergedArray = new String[m + n];

    System.arraycopy(firstArray, 0, mergedArray, 0, m);
    System.arraycopy(secondArray, 0, mergedArray, m, n);

    // Arrays.sort(mergedArray); // uncomment this makes merging two BRDArbre a little bit slower

    return mergedArray;
  }

  /**
   * Retourne un ArrayList contenant toutes les mot de l'oeuvre de shakespeare. Les fichiers texts
   * sont placés dans le dossier data.
   * 
   * @return - un ArrayList contenant toutes les mot de l'oeuvre de shakespeare
   */
  public static ArrayList<String> prepareFiles() {
    String str = "";
    BufferedReader br = null;
    ArrayList<String> words = new ArrayList<String>();
    File f = null;
    File[] paths;

    f = new File("data");
    paths = f.listFiles();

    for (File file : paths) {

      try {
        br = new BufferedReader(new FileReader(file));
        while ((str = br.readLine()) != null) {
          words.add(str);
        }
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return words;
  }

  /**
   * Imprime le contenu d'un tableau de String
   * 
   * @param list - le tableau de string à imprimer
   */
  public static void print(String[] list) {
    int length = list.length;

    System.out.print("[");
    for (int i = 0; i < length; i++) {
      System.out.print(list[i]);
      if (i != length - 1)
        System.out.print(", ");
    }
    System.out.print("]\n");
  }

  public static void printline() {
    for (int i = 0; i < 100; i++) {
      System.out.print("*");
    }
    System.out.println();
  }
}
