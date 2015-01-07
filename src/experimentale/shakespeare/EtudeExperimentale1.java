package experimentale.shakespeare;

import hybrides.TrieHybride;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import utils.Utils;
import briandais.BRDArbre;

public class EtudeExperimentale1 {

	public static void main(String[] args) {
		String word = "etudeexperimentale";
		ArrayList<String> words = new ArrayList<String>();
		words = Utils.prepareFiles();

		System.out.println("Nombre total de mots = " + words.size());
		int numberOfDistinctWords = countDistinctWords(words);
		System.out.println("Nombre de mots distincts : "
				+ numberOfDistinctWords);

		/**************************************
		 * Trie Hybride Test
		 ********************************************/

		Utils.printline();
		System.out.println("TRIE HYBRIDE");
		Utils.printline();
		// Test construction
		long start = System.currentTimeMillis();
		TrieHybride tree = new TrieHybride(words);
		long end = System.currentTimeMillis();
		System.out.println("TrieHybride: CONSTRUCTION = " + (end - start)
				+ " ms");

		System.out.println("\nTrieHybride: Est-ce que \"" + word
				+ "\" existe ? " + tree.recherche(word) + "\n");

		// Test insertion
		start = System.currentTimeMillis();
		tree.insertion(word, 99);
		end = System.currentTimeMillis();
		System.out.println("TrieHybride: INSERTION -  Temps pour ajouter\""
				+ word + "\" au TrieHybride = " + (end - start) + " ms");

		start = System.currentTimeMillis();
		System.out.println("\nTrieHybride: Est-ce que \"" + word
				+ "\" existe ? " + tree.recherche(word) + "\n");
		end = System.currentTimeMillis();

		System.out
				.println("Trie Hybride: RECHERCHE = " + (end - start) + " ms");

		System.out.println("Nombre de pointeurs nul = " + tree.comptageNils());
		System.out.println("Profondeur moyenne = " + tree.profondeurMoyenne());
		System.out.println("Hauteur = " + tree.hauteur());

		Utils.printline();

		/**************************************
		 * END Trie Hybride Test
		 ****************************************/
		/**************************************
		 * Arbre Briandais Test
		 ********************************************/

		// Test construction
		start = System.currentTimeMillis();
		BRDArbre arbre = BRDArbre.createBRDArbre(words);
		end = System.currentTimeMillis();
		System.out.println("Arbre Briandais: CONSTRUCTION = " + (end - start)
				+ " ms");

		System.out.println("\nArbre Briandais: Est-ce que \"" + word
				+ "\" existe ? " + arbre.recherche(arbre, word) + "\n");

		// Test insertion
		start = System.currentTimeMillis();
		arbre = arbre.insertion(word);
		end = System.currentTimeMillis();
		System.out.println("Arbre Briandais: INSERTION - insertion du mot \""
				+ word + "\" dans l'arbre de la Briandais = " + (end - start)
				+ " ms");

		start = System.currentTimeMillis();
		System.out.println("\nArbre Briandais: Does \"" + word + "\" exists? "
				+ arbre.recherche(arbre, word) + "\n");
		end = System.currentTimeMillis();

		System.out.println("Arbre Briandais: RECHERCHE = " + (end - start)
				+ " ms");

		System.out.println("Nombre de pointeurs nul = " + arbre.comptageNils());
		System.out.println("Profondeur moyenne = " + arbre.profondeurMoyenne());
		System.out.println("Hauteur = " + arbre.hauteur());

		/**************************************
		 * END Arbre Briandais Test
		 ****************************************/

	}

	private static int countDistinctWords(ArrayList<String> words) {
		Set<String> set = new HashSet<String>();
		for (String wrd : words) {
			set.add(wrd);
		}
		return set.size();
	}

}
