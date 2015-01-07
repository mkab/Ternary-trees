package briandais;

import hybrides.TrieHybride;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import utils.Utils;

public class BriandaisTest {

  public static void main(String[] args) {

    int c;
    String str = "";
    File file = new File("exemple");
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();
    ArrayList<String> mots = new ArrayList<String>();

    BRDArbre arbre = new BRDArbre(BRDArbre.ETX, null, null);

    long start = 0L, end = 0L;

    try {
      br = new BufferedReader(new FileReader(file.getAbsolutePath()));
      while ((c = br.read()) != -1) {
        sb.append((char) c);
        // System.out.print(caracter);
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

    int length = sb.length();

    for (int i = 0; i < length; i++) {
      char caracter = sb.charAt(i);
      if ((caracter == ' ') || (caracter == '\n')) {
        mots.add(str);
        mots.add(" ");
        str = "";
      } else {
        str += caracter;
      }
    }

    mots.add(str);

    Utils.printline();
    System.out.println("TRIE HYBRIDE");
    Utils.printline();

    start = System.currentTimeMillis();
    TrieHybride tree = new TrieHybride(mots);
    end = System.currentTimeMillis();
    System.out.println("Trie Hybride: Construction = " + (end - start) + " ms");

    System.out.println("Nombre de pointeurs nil = " + tree.comptageNils());
    System.out.println("Profondeur moyenne = " + tree.profondeurMoyenne());

    Utils.printline();

    System.out.println();

    Utils.printline();
    System.out.println("ARBRE DE LA BRIANDAIS");
    Utils.printline();
    start = System.currentTimeMillis();
    for (String mot : mots) {
      arbre = arbre.insertion(mot);
    }
    end = System.currentTimeMillis();

    arbre.imprimer();

    System.out.println("Arbre Briandais: Construction = " + (end - start) + " ms");

    System.out.println("Nombre de pointeurs nil = " + arbre.comptageNils());
    System.out.println("Profondeur moyenne = " + arbre.profondeurMoyenne());
    System.out.println("Hauteur = " + arbre.hauteur());
  }
}
