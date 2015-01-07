package hybrides;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import briandais.BRDArbre;

/**
 * This class implements a Ternary tree. It uses the class <b>hybrides.Node</b> to represent a node
 * in the tree.
 * 
 * @see hybrides.Node
 */
public class TrieHybride {

  /**
   * A constant to represent a case when a word isn't found in the TrieHybride
   */
  private final static int NOT_FOUND = -1;

  /**
   * The root of the TrieHybride
   */
  private Node root;

  /**
   * The number of words in the TrieHybride
   */
  private int numberOfWords;

  /**
   * Default constructor of the TrieHybride. Sets the root to null
   */
  public TrieHybride() {
    root = null;
    numberOfWords = 0;
  }

  /**
   * Creates a {@link TrieHybride}. The resultant {@link #TrieHybride} will contain the words array
   * parameter <b>words</b> has. This method calls the {@link #insertion(String, int)} method for
   * each word in the array <b>words</b>.
   * 
   * 
   * @param mots - the array of words to be inserted
   */
  public TrieHybride(String[] mots) {
    root = null;
    numberOfWords = 0;

    int length = mots.length;

    for (int i = 0; i < length; i++) {
      numberOfWords++;
      insertion(mots[i], i);
    }
  }

  /**
   * Creates a {@link TrieHybride}. The resultant {@link #TrieHybride} will contain the words the
   * ArrayList parameter <b>words</b>. This method calls the {@link #insertion(String, int)} method
   * for each word in the array <b>words</b>.
   * 
   * 
   * @param mots - the arraylist of words to be inserted
   * @see #insertion(String, int)
   */
  public TrieHybride(ArrayList<String> mots) {
    root = null;
    numberOfWords = 0;
    int i = 0;
    int length = mots.size();

    for (i = 0; i < length; i++) {
      numberOfWords++;
      insertion(mots.get(i), i);
    }

  }

  /**
   * Inserts the word <b>mot</b> in the BRDArbre.
   * 
   * @param word - the word to be added
   * @param value - the value of the word i.e the value of the last node
   */
  public void insertion(String word, int value) {
    if (recherche(word))
      return;
    root = put(root, word, 1, 0);
  }

  private Node put(Node node, String word, int value, int index) {
    char c = word.charAt(index);

    if (isEmpty(node)) { // node == null : No word has been added yet
      node = new Node(c);
    }

    if (c < node.character) {
      node.left = put(node.left, word, value, index);
      // check balance

    } else if (c > node.character) {
      node.right = put(node.right, word, value, index);
      // check balance

    } else if (index < word.length() - 1) {
      node.mid = put(node.mid, word, value, index + 1);
      // check balance

    } else { // index == word.length?
      // && value != 0
      if (!node.isWord)
        numberOfWords++;

      node.value = ++value;
      node.isWord = true;
      node.count++;

    }

    return node;
  }

  /**
   * Cherche le mot donné dans le Trie Hybride
   * 
   * @param word - le mot à chercher
   * @return <b>true</b> si le Trie Hybride contient le mot, <b>false</b> sinon
   */
  public boolean recherche(String word) {
    return get(word) != NOT_FOUND;
  }

  private int get(String word) {
    if (word == null)
      throw new NullPointerException("word cannot be null");
    if (word.length() == 0)
      throw new IllegalArgumentException("word must have length >= 1");

    Node node = getPrefix(root, word, 0);
    if (node != null && node.isWord)
      return node.value;

    return NOT_FOUND;
  }

  private Node getPrefix(Node node, String word, int index) {
    if (word == null)
      throw new NullPointerException("word cannot be null");
    if (word.length() == 0)
      throw new IllegalArgumentException("word must have length > 1");
    if (node == null)
      return null;

    char c = word.charAt(index);
    if (c < node.character)
      return getPrefix(node.left, word, index);
    else if (c > node.character)
      return getPrefix(node.right, word, index);
    else if (index < (word.length() - 1))
      return getPrefix(node.mid, word, (index + 1));
    else
      return node;
  }

  /**
   * Retourne le nombre de mots dans le {@link TrieHybride}
   * 
   * @return - le nombre de mots dans le {@link TrieHybride}
   */
  public int comptageMots() {
    if (root == null)
      return 0;
    return comptageMotsRecursive(root);
  }

  private int comptageMotsRecursive(Node node) {
    if (node == null)
      return 0;

    if (node.isWord)
      return 1;

    return comptageMotsRecursive(node.left) + comptageMotsRecursive(node.mid)
        + comptageMotsRecursive(node.right);
  }

  /**
   * Retourne la hauteur de la {@link #TrieHybride}
   * 
   * @return - la hauteur de la {@link #TrieHybride}
   */
  public int hauteur() {
    if (root == null)
      return 0;
    return hauteurRecursive(root);
  }

  private int hauteurRecursive(Node node) {
    if (node == null)
      return -1;
    else
      return 1 + Math.max(Math.max(hauteurRecursive(node.left), hauteurRecursive(node.mid)),
          hauteurRecursive(node.right));
  }

  /**
   * Retourne un tableau de String contenant la liste de mots du {@link #TrieHybride} dans l'ordre
   * alphabétique.
   * 
   * @return -un tableau de String contenant la liste de mots du {@link #TrieHybride} dans l'ordre
   *         alphabétique.
   */
  public String[] listeMots() {
    if (isEmpty(root)) {
      return new String[1];
    }

    ArrayList<String> list = new ArrayList<String>();

    listeMotsRecursive(root, "", list);

    String[] alphabeticalOrder = new String[list.size()];
    alphabeticalOrder = list.toArray(alphabeticalOrder);

    return alphabeticalOrder;
  }

  private void listeMotsRecursive(Node node, String prefix, ArrayList<String> list) {
    if (node != null) {
      listeMotsRecursive(node.left, prefix, list);

      if (node.isWord)
        list.add(prefix + node.character);

      listeMotsRecursive(node.mid, (prefix + node.character), list);
      listeMotsRecursive(node.right, prefix, list);
    }
  }

  /**
   * Retourne le nombre de pointeurs nil dans le {@link #TrieHybride}
   * 
   * @return - le nombre de pointeurs nil dans le {@link #TrieHybride}
   */
  public int comptageNils() {
    return comptageNilsRecursive(root);
  }

  private int comptageNilsRecursive(Node node) {
    if (node == null)
      return 1;
    return comptageNilsRecursive(node.left) + comptageNilsRecursive(node.mid)
        + comptageNilsRecursive(node.right);
  }

  /**
   * Retourne le nombre de mots du {@link #TrieHybride} ayant le mot <b>word</b> comme préfixe
   * 
   * @param word - le mot
   * @return - le nombre de mots du {@link #TrieHybride} ayant le mot <b>word</b> comme préfixe
   */
  public int prefixe(String word) {
    Queue<String> queue = new LinkedList<String>();

    Node node = getPrefix(root, word, 0);

    if (node == null)
      return 0;

    if (node.character != ' ' && node.isWord)
      queue.add(word);

    prefixRecursive(node.mid, word, queue);
    System.out.println("Le(s) mot(s) qui contient le prefix \"" + word + "\" est(sont): " + queue);

    return queue.size();
  }

  /**
   * Retourne la profondeur moyenne du {@link #TrieHybride}
   * 
   * @return - la profondeur moyenne du {@link #TrieHybride}
   */
  public int profondeurMoyenne() {
    int depth = 0;
    Integer sum = 0;
    ArrayList<Integer> list = new ArrayList<Integer>();

    if (root == null)
      return 0;

    // calculate average depth
    profondeur(root, list, depth);

    if (!list.isEmpty()) {
      for (Integer value : list) {
        sum += value;
      }
      return (sum / list.size());
    }

    return 0;
  }

  private void profondeur(Node node, ArrayList<Integer> list, int depth) {
    if (node == null)
      return;

    if (node.isWord) {
      list.add(depth);
    }

    profondeur(node.left, list, depth + 1);
    profondeur(node.mid, list, depth + 1);
    profondeur(node.right, list, depth + 1);
  }

  private void prefixRecursive(Node node, String prefix, Queue<String> queue) {
    if (node == null)
      return;

    prefixRecursive(node.left, prefix, queue);

    if (node.character != ' ' && node.isWord)
      queue.add(prefix + node.character);

    prefixRecursive(node.mid, prefix + node.character, queue);
    prefixRecursive(node.right, prefix, queue);
  }

  /**
   * Supprime le mot <b>word</b> du dictionnaire Retourne <b>vrai</b> si la suppression est réussie,
   * <b>faux</b> sinon.
   * 
   * @param word - le mot à supprimer
   * @return - <b>vrai</b> si la suppression est réussie, <b>faux</b> sinon.
   */
  public boolean suppression(String word) {

    boolean find = recherche(word);
    if (!find)
      return false;

    return suppressionRecursive(root, word, 0);
  }

  private boolean suppressionRecursive(Node node, String word, int index) {
    if (node == null)
      return false;

    char c = word.charAt(index);

    if (c < node.character)
      suppressionRecursive(node.left, word, index);
    else if (c > node.character)
      suppressionRecursive(node.right, word, index);
    else {
      if (node.isWord && index == word.length() - 1)
        node.isWord = false;

      else if (index < word.length() - 1)
        suppressionRecursive(node.mid, word, index + 1);
    }

    return true;
  }

  /**
   * Convertie le {@link #TrieHybride} en {@link briandais.BRDArbre}
   * 
   * @return - un {@link briandais.BRDArbre}
   */
  public BRDArbre convertToArbreBriandais() {
    String[] words = listeMots();

    return BRDArbre.createBRDArbre(words);
  }

  /**
   * Retourne <b>vrai</b> si le noeud du {@link #TrieHybride} est vide (null), <b>faux</b> sinon
   * 
   * @param node - le noeud
   * 
   * @return - <b>vrai</b> si le noeud du {@link #TrieHybride} est vide (null), <b>faux</b> sinon
   */
  public boolean isEmpty(Node node) {
    return (node == null);
  }

  /**
   * Retourne la racine du {@link #TrieHybride}
   * 
   * @return - la racine du {@link #TrieHybride}
   */
  public Node getRoot() {
    return root;
  }

  /**
   * The value of root of a TrieHybride
   * 
   * @return - the value of the root of the TrieHybride
   */
  public int getValue() {
    return root.value;
  }

  /**
   * Returns the left node of the TrieHybride
   * 
   * @return - the left node of the TrieHybride
   */
  public Node getLeft() {
    return root.left;
  }

  /**
   * Returns the mid node of the TrieHybride
   * 
   * @return - the mid node of the TrieHybride
   */
  public Node getMid() {
    return root.mid;
  }

  /**
   * Returns the right node of the TrieHybride
   * 
   * @return - the right node of the TrieHybride
   */
  public Node getRight() {
    return root.right;
  }

  /**
   * Retourne le nombre de mots dans le {@link #TrieHybride}
   * 
   * @return - le nombre de mots dans le {@link #TrieHybride}
   */
  public int getComptageMots() {
    return numberOfWords++;
  }
}
