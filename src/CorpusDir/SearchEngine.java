package CorpusDir;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashMap;
import java.util.Vector;
import java.awt.EventQueue;
import java.io.File;

public class SearchEngine {

	public static String searchWord;
	public static String fileDirectory;
	public static String stopWordsFileName;
	public static String resultsOutput;
	public static String queriesOutput;
	public static String dummyOutPut;
	public static int fixedInt;
	public static boolean stem;

	public static AtomicInteger files = new AtomicInteger(0);
	public static AtomicInteger counterList = new AtomicInteger(0);

	public static HashMap<String, Integer> stopList = new HashMap<String, Integer>();

	public static HashMap<String, indexObject> hash = new HashMap<String, indexObject>();

	public static Vector<indexObject> vector = new Vector<indexObject>();

	public static Vector<indexObject> searchVector = new Vector<indexObject>();
	public static File[] directoryListing;
	public static String[] cmdlineargs;

	public static void main(String[] args) throws Exception {
		cmdlineargs = args.clone();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

//		
	}

	public static void run(String[] args) throws Exception {

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("-CorpusDir")) {
				fileDirectory = args[i + 1];
				// eclipse-workspace/335Project1/src/CorpusDir/folderdir
			} else if (args[i].equals("-InvertedIndex")) {
				dummyOutPut = args[i + 1];
				// eclipse-workspace/335Project1/src/dummy.txt
			} else if (args[i].equals("-StopList")) {
				stopWordsFileName = args[i + 1];
				// eclipse-workspace/335Project1/src/english-stopwords.txt
			} else if (args[i].equals("-Queries")) {
				queriesOutput = args[i + 1];
				// eclipse-workspace/335Project1/src/searchword.txt

			} else if (args[i].equals("-Results")) {
				resultsOutput = args[i + 1];
				// eclipse-workspace/335Project1/src/results.txt
			} else if (args[i].equals("-Fixed")) {
				fixedInt = Integer.parseInt((args[i + 1]));
				// 5
			} else if (args[i].equals("-Stem")) {
				stem = Boolean.parseBoolean((args[i + 1].toLowerCase()));
				// True or False
			}

		}

		if (stem) {
			System.out.print("Stemming has been enabled! " + "\n");
		}
		File dir = new File(fileDirectory);
		hashFunc.stopListHash(stopWordsFileName);

//		Scanner st = new Scanner(System.in); // Create a Scanner object
//		System.out.println("Enter a search word: " + "\n");
//		searchWord = st.nextLine();
//		st.close();

		// Reads through a text file containing ALL stop words

		directoryListing = dir.listFiles();
		counterList.set(dir.listFiles().length - 1);

		if (directoryListing != null) {

			for (File child : directoryListing) {
				// System.out.println(child.toString().substring(child.toString().lastIndexOf(File.separator),
				// child.toString().length()));
				hashFunc.readHTML(child, dummyOutPut, true, stem);
				files.incrementAndGet();
			}
		} else {
			System.out.println("There are no files in -CorpusDir");
		}

		// Deletes the dummy file after all is set and done
		Files.deleteIfExists(Paths.get(dummyOutPut));

	}

}
