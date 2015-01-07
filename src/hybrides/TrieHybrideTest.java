package hybrides;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TrieHybrideTest {

  public static void main(String[] args) {

    TrieHybride trie = new TrieHybride();
    int c;
    String str = "";
    File file = new File("exemple");
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();
    ArrayList<String> mots = new ArrayList<String>();

    try {
      br = new BufferedReader(new FileReader(file.getAbsolutePath()));
      while ((c = br.read()) != -1) {
        sb.append((char) c);
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

    int i, length = sb.length();

    for (i = 0; i < length; i++) {
      char caracter = sb.charAt(i);
      if ((caracter == ' ') || (caracter == '\n')) {
        mots.add(str);
        mots.add(" ");
        str = "";
      } else {
        str += caracter;
      }
    }

    mots.add(str); // add the last word

    i = 0;
    for (String mot : mots) {
      trie.insertion(mot, i++);
    }

    for (String mot : mots) {
      boolean b = trie.recherche(mot);
      if (!b)
        System.out.println(mot + " : " + b);
    }

    System.out.println("Nombre de mots = " + trie.getComptageMots());
    System.out.println("Does it exist? : " + trie.recherche("a"));

    // trie.ListeMots();
    System.out.println(trie.prefixe("d"));

  }
}
