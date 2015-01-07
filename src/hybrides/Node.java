package hybrides;

public class Node {
  /** Valeur du node */
  protected int value;

  /** vrai si le char est la fin d'un mot */
  protected boolean isWord;

  /** compte le nombre d'apparitions dans le Trie Hybride */
  protected int count;

  /** le char du noeud */
  protected char character;

  /**
   * Le noeud gauche
   */
  protected Node left;

  /**
   * Le noeud du milieu
   */
  protected Node mid;

  /**
   * Le noeud de droite
   */
  protected Node right;

  /**
   * Constructeur par défaut de la classe {@link #Node}. Initialise le char du noeud créé à
   * <b>character</b>
   * 
   * @param character - le char
   */
  public Node(char character) {
    value = 0;
    isWord = false;
    count = 0;
    this.character = character;
    left = null;
    mid = null;
    right = null;

  }

  public int getValue() {
    return value;
  }

  /**
   * Retourne si le noeud contient le dernier caratère d'un mot
   * 
   * @return - vrai si le noeud contient le dernier caratère d'un mot
   */
  public boolean isWord() {
    return isWord;
  }

  /**
   * Retourne le nombre de mots dans le dictionnaire
   * 
   * @return - le nombre de mots dans le dictionnaire
   */
  public int getCount() {
    return count;
  }

  /**
   * Retourne le caractère du noeud
   * 
   * @return - le caractère du noeud
   */
  public char getCharacter() {
    return character;
  }

  /**
   * Retourne le fils gauche du noeud
   * 
   * @return - le fils gauche du noeud
   */
  public Node getLeft() {
    return left;
  }

  /**
   * Retourne le fils "du milieu" du noeud
   * 
   * @return - le fils "du milieu" du noeud
   */
  public Node getMid() {
    return mid;
  }

  /**
   * Retourne le fils droit du noeud
   * 
   * @return - le fils droit du noeud
   */
  public Node getRight() {
    return right;
  }

}