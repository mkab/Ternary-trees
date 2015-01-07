package briandais;

import hybrides.TrieHybride;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import utils.Utils;

public class BRDArbre {

  public static final char ETX = (char) 3;

  /**
   * La clé du BRDArbre. Il s'agit toujours de la plus petite lettre de l'arbre
   */
  private char cle;

  /**
   * Un BRDArbre qui représente le fils d'un noeud
   */
  private BRDArbre fils;

  /**
   * Un BRDArbre qui représente le frère d'un noeud
   */
  private BRDArbre frere;

  /**
   * Ce booléen est utilisé pour déterminer quand utiliser la méthode BRDcons(String mot). Il est
   * statique afin d'avoir la même valeur pour toutes les instances pendant les appels de fonctions
   * récursives. Principalement utilisé dans la méthode BRDinsertion
   */
  private static boolean skip = false;

  /**
   * Utilisé pour savoir à quel niveau de l'arbre on se trouve. Principalement utilisé dans la
   * méthode ListeMots(BRDArbre arbre, String str). Commence à -1 car le premier niveau de l'arbre
   * est 0.
   */
  static int level = -1;

  /**
   * Constructeur par défaut
   * 
   * @param cle - la clé (un char) de l'arbre. Elle sert de clé pour la racine de l'arbre. Pendant
   *          insertion, l'arbre est toujours mis à jour de façon à ce que la clé contienne le plus
   *          petit char de l'arbre.
   * @param fils - le fils. Un BRDArbre
   * @param frere - le frère. Un BRDArbre
   */
  public BRDArbre(char cle, BRDArbre fils, BRDArbre frere) {
    this.cle = cle;
    this.fils = fils;
    this.frere = frere;
  }

  /**
   * Construit un BRDArbre. Le BRDArbre produit contiendra les paramètres du tableau de mots
   * <b>words</b>. Cette méthode appelle la méthode {@link #insertion(String)} pour chaque mot dans
   * le tableau <b>words</b>.
   * 
   * Complexité O(nlogn)
   * 
   * @param words - le tableau de mots à insérer
   * @return - le BRDArbre créé contenant les mots de words
   */
  public static BRDArbre createBRDArbre(String[] words) {
    BRDArbre arbre = new BRDArbre(ETX, null, null);

    int length = words.length;
    for (int i = 0; i < length; i++) {
      arbre = arbre.insertion(words[i]);
    }
    return arbre;
  }

  /**
   * Construit un BRDArbre. Le BRDArbre produit contiendra les paramètres du tableau de mots
   * <b>words</b>. Cette méthode appelle la méthode {@link #insertion(String)} pour chaque mot dans
   * l'ArrayList <b>words</b>.
   * 
   * Complexité O(nlogn)
   * 
   * @param words - la liste de mots à insérer
   * @return - le BRDArbre créé contenant les mots de words
   */
  public static BRDArbre createBRDArbre(ArrayList<String> words) {
    BRDArbre arbre = new BRDArbre(ETX, null, null);

    int length = words.size();
    for (int i = 0; i < length; i++) {
      arbre = arbre.insertion(words.get(i));
    }
    return arbre;
  }

  private BRDArbre BRDcons(String mot) {
    if (mot.equals("")) {
      return new BRDArbre(ETX, null, null);
    } else {
      return new BRDArbre(tete(mot), BRDcons(queue(mot)), null);
    }
  }

  /**
   * Insère le mot <b>mot</b> dans le BRDArbre.
   * 
   * @param mot - le mot à insérer
   * @return - l'arbre après insertion
   */
  public BRDArbre insertion(String mot) {
    if (recherche(this, mot))
      return this;
    return BRDinsertion(mot);
  }

  /**
   * Insère un mot dans l'arbre puis retourne cet arbre. Le booléen <b>skip</b> est utilisé afin de
   * déterminer quand utiliser la méthode BDRcons(String mot)
   * 
   * @param mot - le mot à insérer
   * @return Retourne l'arbre après insertion
   */
  private BRDArbre BRDinsertion(String mot) {
    if (estVide() && !skip) {
      return BRDcons(mot);
    }

    if (mot.equals("")) {
      if (cle == ETX) {
        return this;
      } else {
        return new BRDArbre(ETX, null, this);
      }
    } else {
      if (tete(mot) < cle) {
        skip = false;
        return new BRDArbre(tete(mot), BRDcons(queue(mot)), this);
      } else if (tete(mot) > cle) {
        skip = false;
        if (frere == null) {
          frere = new BRDArbre(ETX, null, null);
        }
        return new BRDArbre(cle, fils, frere.insertion(mot));
      } else {
        skip = true;
        if (fils == null) {
          fils = new BRDArbre(ETX, null, null);
        }
        if (cle == ETX && !mot.equals("")) {
          if (frere == null) {
            frere = new BRDArbre(ETX, null, null);
          }
          return new BRDArbre(ETX, fils, frere.insertion(queue(mot)));
        }

        return new BRDArbre(cle, fils.insertion(queue(mot)), frere);
      }
    }
  }

  /**
   * Supprime le mot donné de l'arbre et retourne cet arbre. Cette méthode appelle
   * BRDsearch(BRDArbre, String mot) pour vérifier que le mot existe avant de tenter de le
   * supprimer.
   * 
   * @param mot - le mot à supprimer
   * @return l'arbre après suppression
   */
  public BRDArbre suppression(String mot) {
    /**
     * Vérifie que le mot existe dans l'arbre. S'il n'existe pas, retourne l'arbre.
     */
    boolean exists = recherche(this, mot);
    if (!exists) {
      return this;
    }

    // else le mot existe
    return BRDSuppression(mot);
  }

  /**
   * Supprime le mot donné de l'arbre et retourne cet arbre.
   * 
   * @param mot - le mot à supprimer
   * @return l'arbre après suppression
   */
  private BRDArbre BRDSuppression(String mot) {
    if (mot.equals("")) {
      return frere;
    }
    if (tete(mot) == cle) {
      fils = fils.BRDSuppression(queue(mot));
      if (fils == null) {
        return frere;
      } else {
        return new BRDArbre(tete(mot), fils, frere);
      }
    } else {
      return new BRDArbre(cle, fils, frere.BRDSuppression(mot));
    }
  }

  /**
   * Retourne <b>true</b> si le mot est dans l'arbre, <b>false</b> sinon
   * 
   * @param arbre - l'arbre
   * @param mot - le mot à chercher
   * @return <b>true</b> si le mot est dans l'arbre, <b>false</b> sinon
   */
  public boolean recherche(BRDArbre arbre, String mot) {
    if (arbre == null)
      return false;

    try {
      while (arbre != null) {
        if ((arbre.cle != tete(mot)))
          arbre = arbre.frere;
        else
          break;

      }
    } catch (IllegalArgumentException e) {
      // le mot n'existe pas
    }

    if (arbre == null)
      return false;

    int length = mot.length();
    int i = 0;
    for (i = 0; i < length; i++) {

      if (arbre.cle != mot.charAt(i)) {

        arbre = arbre.frere;
        if (arbre == null)
          return false;
        else {
          i--;
          continue;
        }
      }

      arbre = arbre.fils;
    }

    if (arbre.cle != ETX) {
      return false;
    }

    return true;
  }

  /**
   * Retourne un tableau de String contenant la liste des mots dans le BRDArbre dans l'ordre
   * alphabétique
   * 
   * @param arbre - le BRDArbre
   * @return - un tableau de String contenant la liste des mots dans le BRDArbre dans l'ordre
   *         alphabétique
   */
  public static String[] ListeMots(BRDArbre arbre) {
    ArrayList<String> list = new ArrayList<String>();
    String str = "", substr = "";

    ListeMotsRecursive(arbre, str, substr, list);
    String[] alphabeticalOrder = new String[list.size()];
    alphabeticalOrder = list.toArray(alphabeticalOrder);

    return alphabeticalOrder;
  }

  /**
   * 
   * @param arbre
   * @param str
   * @param list
   */
  private static void ListeMotsRecursive(BRDArbre arbre, String str, String substr,
      ArrayList<String> list) {

    if (arbre.cle == ETX) {
      list.add(str);
      if (arbre.frere != null) {
        // parfois ETX peut contenir un frère donc on doit appeler
        // récursivement la méthode ListeMots sur son frère
        ListeMotsRecursive(arbre.frere, str, substr, list);
      }
      str = ""; // après avoir appelé la méthode sur ses fils et frères
      // initialise str à "" afin d'avoir un String vide
      // pour les autres frères et soeurs
      return;
    }

    str += arbre.cle;
    level++;

    ListeMotsRecursive(arbre.fils, str, substr, list);
    if (arbre.frere != null) {
      level = 0;

      substr = str.substring(level, str.length() - 1);

      str = ""; // str vide avant de récupérer la valeur depuis frère
      ListeMotsRecursive(arbre.frere, substr, substr, list);
    }
  }

  /**
   * Retourne le nombre de mots dans le BRDArbre
   * 
   * @param arbre - le BRDArbre
   * @return - le nombre de mots dans le BRDArbre
   */
  public static int comptageMots(BRDArbre arbre) {
    if (arbre == null)
      return 0;
    if (arbre.cle == ETX) {
      if (arbre.frere != null)
        return 1 + comptageMots(arbre.frere);
      else
        return 1;
    }

    return comptageMots(arbre.fils) + comptageMots(arbre.frere);
  }

  /**
   * Retourne le nombre de pointeurs nil dans le {@link #BRDArbre}
   * 
   * @return - le nombre de pointeurs nil dans le {@link #BRDArbre}
   */
  public int comptageNils() {
    return comptageNilsRecursive(this);
  }

  private int comptageNilsRecursive(BRDArbre arbre) {
    if (arbre == null)
      return 1;

    return comptageNilsRecursive(arbre.fils) + comptageNilsRecursive(arbre.frere);
  }

  /**
   * Retourne la profondeur moyenne du noeud des feuilles du BRDarbre
   * 
   * @return - la profondeur moyenne du noeud des feuilles du BRDarbre
   */
  public int profondeurMoyenne() {
    int depth = 0;
    Integer sum = 0;
    ArrayList<Integer> list = new ArrayList<Integer>();

    // calcule de la profondeur moyenne
    profondeur(this, list, depth);

    if (!list.isEmpty()) {
      for (Integer value : list) {
        sum += value;
      }
      return (sum / list.size());
    }

    return 0;
  }

  private void profondeur(BRDArbre arbre, ArrayList<Integer> list, int depth) {
    if (arbre == null)
      return;

    if (arbre.cle == ETX) {
      list.add(depth);
    }

    profondeur(arbre.fils, list, depth + 1);
    profondeur(arbre.frere, list, depth);

  }

  /**
   * Retourne le nombre de mots du BRDArbre ayant le mot <b>word</b> comme préfixe
   * 
   * @param word - le mot
   * @return - le nombre de mots du BRDArbre ayant le mot <b>word</b> comme préfixe
   */
  public int prefixe(String word) {
    Queue<String> queue = new LinkedList<String>();

    BRDArbre arbre = getPrefixe(this, word, 0);

    // prefix n'existe pas, plus besoin de vérifier
    if (arbre == null)
      return 0;

    // appel récursif sur le fils
    prefixeRecursive(arbre, word, queue);

    return queue.size();
  }

  private void prefixeRecursive(BRDArbre arbre, String word, Queue<String> queue) {
    if (arbre.cle == ETX) {
      if (arbre.frere != null) {
        word = word.substring(0, (word.length() - 1));
        prefixeRecursive(arbre.frere, word + arbre.frere.cle, queue);
      }
      return;
    }

    if (arbre.fils.cle == ETX) {
      queue.add(word);
    }

    prefixeRecursive(arbre.fils, (word + arbre.fils.cle), queue);

    if (arbre.frere != null) {
      word = word.substring(0, (word.length() - 1));

      prefixeRecursive(arbre.frere, word + arbre.frere.cle, queue);

    }

  }

  public BRDArbre getPrefixe(BRDArbre arbre, String word, int index) {
    if (word == null)
      throw new NullPointerException("word ne peut pas etre null");
    if (word.length() == 0)
      throw new IllegalArgumentException("word doit avoir length > 1");
    if (arbre == null)
      return null;

    char c = word.charAt(index);

    if (c != arbre.cle)
      return getPrefixe(arbre.frere, word, index);

    else if (index < (word.length() - 1)) {
      if (c == arbre.cle)
        return getPrefixe(arbre.fils, word, index + 1);
    }

    return arbre;
  }

  /**
   * Retourne la hauteur du BRDArbre
   * 
   * @return - la hauteur du BRDArbre
   */
  public int hauteur() {
    int depth = 0;
    BRDArbre arbre = this;

    if (estVide()) {
      return 0;
    }

    while (arbre != null) {
      int temp = 0;
      BRDArbre tempArbre = new BRDArbre(ETX, null, null);
      tempArbre.cle = arbre.cle;
      tempArbre.fils = arbre.fils;

      temp = hauteurRecursive(tempArbre);

      if (temp > depth)
        depth = temp;
      arbre = arbre.frere;
    }

    return depth;
  }

  private int hauteurRecursive(BRDArbre arbre) {
    if (arbre == null)
      return -1;

    return 1 + Math.max(hauteurRecursive(arbre.fils), hauteurRecursive(arbre.frere) - 1);
  }

  /**
   * Fusionne deux BRDArbre et retourne le BRDArbre ainsi formé. Prend trop de place.
   * 
   * @param arbre1 - un BRDArbre
   * @param arbre2 - un BRDArbre
   * @return - le BRDArbre résultant de la fusion des deux BRDArbre
   */
  public static BRDArbre mergeBRDArbre(BRDArbre arbre1, BRDArbre arbre2) {
    String[] order1 = ListeMots(arbre1);
    String[] order2 = ListeMots(arbre2);

    String[] finalOrder = Utils.mergeTables(order1, order2);

    return createBRDArbre(finalOrder);
  }

  /**
   * Convertie un BRDArbre en TrieHybride
   * 
   * @param arbre - un BRDArbre
   * 
   * @return - un TrieHybride
   */
  public static TrieHybride convertToTrieHybride(BRDArbre arbre) {
    String[] words = ListeMots(arbre);

    return new TrieHybride(words);
  }

  /**
   * Retourne la première lettre d'un mot donné
   * 
   * @param mot - le mot
   * @return la première lettre d'un mot donné
   */
  private char tete(String mot) {
    if (mot.equals(""))
      throw new IllegalArgumentException();
    return mot.charAt(0);
  }

  /**
   * Retourne un substring d'un mot donné sans la première lettre
   * 
   * @param mot - le mot
   * @return un substring d'un mot donné sans la première lettre
   */
  private String queue(String mot) {
    return mot.substring(1);
  }

  /**
   * Retourne <b>vrai</b> si l'arbre est vide, <b>faux</b> sinon
   * 
   * Un {{@link #BRDArbre} s'il a comme clé {@link #ETX} et {@link #fils} et {@link #frere} sont
   * null;
   * 
   * @return <b>vrai</b> si l'arbre est vide, <b>faux</b> sinon
   */
  public boolean estVide() {
    return (cle == ETX) && (fils == null) && (frere == null);
  }

  /**
   * Imprime le BRD Arbre pour avoir un aperçu visuel
   */
  public void imprimer() {
    imprimer(this, 1, false);
    System.out.println();
  }

  private void imprimer(BRDArbre arbre, int niveau, boolean b) {
    if (arbre == null) {
      --niveau;
      return;
    }
    if (b) {
      System.out.print("  "); // ajustement
      System.out.printf("%" + (9 * (niveau - 1)) + "s", "|\n");
      System.out.print(" "); // ajustement

      System.out.printf("%" + (9 * (niveau - 1)) + "s", arbre.cle);
    } else
      System.out.print(arbre.cle);

    if (arbre.cle != ETX)
      System.out.print("--------");

    imprimer(arbre.fils, ++niveau, false);

    if (arbre.cle == ETX && !b) {
      System.out.println("\n|");

    }

    if (arbre.frere != null) {
      if (niveau == 2)
        imprimer(arbre.frere, --niveau, false);
      else
        imprimer(arbre.frere, --niveau, true);
    }

  }

  /**
   * Retourne la clé de l'arbre
   * 
   * @return la clé de l'arbre
   */
  public char getCle() {
    return cle;
  }

  /**
   * Retourne le noeud fils du BRDArbre
   * 
   * @return le noeud fils du BRDArbre
   */
  public BRDArbre getFils() {
    return fils;
  }

  /**
   * Retourne le noeud frère du BRDArbre
   * 
   * @return le noeud frère du BRDArbre
   */
  public BRDArbre getFrere() {
    return frere;
  }

}
