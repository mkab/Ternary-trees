package experimentale.exemple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import utils.Utils;
import briandais.BRDArbre;

public class BriandaisApercu {

  public static void main(String[] args) {

    int c;
    String str = "";
    File file = new File("exemple");
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();
    ArrayList<String> mots = new ArrayList<String>();

    BRDArbre arbre = new BRDArbre(BRDArbre.ETX, null, null);

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
    System.out.println("ARBRE DE LA BRIANDAIS");
    Utils.printline();
    for (String mot : mots) {
      arbre = arbre.insertion(mot);
    }

    arbre.imprimer();

  }
}
