package experimentale.shakespeare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import briandais.BRDArbre;

public class EtudeExperimentale2 {

	public static void main(String[] args) {
		String str = "";
		String word = "etudeexperimentale";
		File file1 = new File("data\\hamlet.txt");
		File file2 = new File("data\\richardiii.txt");

		BufferedReader br = null;
		ArrayList<String> words1 = new ArrayList<String>();
		ArrayList<String> words2 = new ArrayList<String>();

		try {
			br = new BufferedReader(new FileReader(file1.getAbsolutePath()));
			while ((str = br.readLine()) != null) {
				words1.add(str);
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

		try {
			br = new BufferedReader(new FileReader(file2.getAbsolutePath()));
			while ((str = br.readLine()) != null) {
				words2.add(str);
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

		System.out.println("size = " + words1.size());
		int numberOfDistinctWords = countDistinctWords(words1);
		System.out.println("Nombre de mots distincts : "
				+ numberOfDistinctWords);
		long start, end;
		/************************************** Trie Hybride Test ********************************************/

		// // Test construction
		// long start = System.currentTimeMillis();
		//
		// TrieHybride tree1 = new TrieHybride(words1);
		// TrieHybride tree2 = new TrieHybride(words2);
		//
		// TrieHybride finalTree =
		// long end = System.currentTimeMillis();
		// System.out.println("TrieHybride: temps pour construire TrieHybride = "
		// + (end - start)
		// + " ms");
		//
		// System.out.println("\nTrieHybride: Est-ce que \"" + word +
		// "\" existe ? " + tree1.recherche(word)
		// + "\n");

		/************************************** END Trie Hybride Test ****************************************/

		printline();

		/************************************** Arbre Briandais Test ********************************************/

		// Test construction
		BRDArbre arbre1 = BRDArbre.createBRDArbre(words1);
		BRDArbre arbre2 = BRDArbre.createBRDArbre(words2);

		start = System.currentTimeMillis();
		BRDArbre finalArbre = BRDArbre.mergeBRDArbre(arbre1, arbre2);
		end = System.currentTimeMillis();
		System.out
				.println("Arbre Briandais: Temps pour fusionner arbre1 et arbre2 = "
						+ (end - start) + " ms");

		// String[] list2 = BRDArbre.ListeMots(arbre);

		System.out.println("\nArbre Briandais: Est-ce que \"" + word
				+ "\" existe ? " + finalArbre.recherche(arbre1, word) + "\n");

		// Test insertion
		start = System.currentTimeMillis();
		finalArbre = finalArbre.insertion(word);
		end = System.currentTimeMillis();
		System.out.println("Arbre Briandais: Temps pour ajouter \"" + word
				+ "\" à l'arbre de la Briandais final = " + (end - start)
				+ " ms");

		System.out.println("\nArbre Briandais: Est-ce que \"" + word
				+ "\" existe ? " + finalArbre.recherche(finalArbre, word)
				+ "\n");

		// print(list2);
		/************************************** END Arbre Briandais Test ****************************************/

	}

	@SuppressWarnings("unused")
	private static void print(String[] list) {
		int length = list.length;

		System.out.print("[");
		for (int i = 0; i < length; i++) {
			System.out.print(list[i]);
			if (i != length - 1)
				System.out.print(", ");
		}
		System.out.print("]\n");
	}

	private static int countDistinctWords(ArrayList<String> words) {
		Set<String> set = new HashSet<String>();
		for (String wrd : words) {
			set.add(wrd);
		}
		return set.size();
	}

	private static void printline() {
		for (int i = 0; i < 160; i++) {
			System.out.print("*");
		}
		System.out.println();
	}

}
