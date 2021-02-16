package CorpusDir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class hashFunc {

	public static HashMap<String, LinkedHashSet<String>> hashObjs = new HashMap<String, LinkedHashSet<String>>();

	public static ArrayList<String> freq = new ArrayList<String>();

//putting stop words in stopList 
	public static void stopListHash(String argv) throws IOException {

		File file = new File(argv);

		Integer i = 0;

		if (SearchEngine.stopList.size() != 0) {

			SearchEngine.stopList.clear();

		}

		Scanner textFile = new Scanner(file);

		while (textFile.hasNext()) {

			String word = textFile.next();

			i++;

			SearchEngine.stopList.put(word.toUpperCase(), i);
		}

		textFile.close();

	}

	@SuppressWarnings("unchecked")
	public static void printHash(String output) throws IOException {

		File file = new File(output);

		// Sorting by number of instances
		Collections.sort(SearchEngine.vector);

		@SuppressWarnings("rawtypes")
		Iterator it2 = SearchEngine.vector.iterator();

		FileWriter arg2write = new FileWriter(file);

		arg2write.write("The Results Out of " + (SearchEngine.counterList.intValue() + 1) + " Files: " + "\n" + "\n");

		while (it2.hasNext()) {
			arg2write.write(it2.next() + "\n");
		}
		arg2write.close();

		System.out.println("Results have been posted ");

	}

	@SuppressWarnings({ "rawtypes" })
	public static void readHTML(File argv, String dummy, boolean flag, boolean stem) throws IOException {

		String doc = argv.toString().substring(argv.toString().lastIndexOf(File.separator), argv.toString().length());

		// Since all the words will be upper case
		// String searchWordUpper = SearchEngine.searchWord.toUpperCase();
		String searchWordUpper = SearchEngine.searchWord.toUpperCase();
		BufferedReader reader;

		File file = new File(dummy);

		FileWriter arg2write = new FileWriter(file);

		StringBuilder sb = new StringBuilder();

		try {

			// Reads the raw HTML file
			reader = new BufferedReader(new FileReader(argv));

			String line = reader.readLine() + " ";

			while (line != null) {

				sb.append(line.toUpperCase() + "\n");

				line = reader.readLine();

			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Using REGEX to remove tags, paratheticals, brackets, and all that is
		// contained within them

		String nohtml = sb.toString().replaceAll("<[^>]*>", "").replaceAll("\\(.*\\)", "")
				.replaceAll("[^a-zA-Z0-9\\s.-]", "").replaceAll("/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/", "");

		// The dummy text file contains the modified StringBuilder
		arg2write.append(nohtml);

		arg2write.close();

		Scanner textFile = new Scanner(file);

		// WORKS BUT IS WAY TOO SLOW
		// THIS WOULD'VE BEEN MUCH MORE EFFICIENT FOR GATHERING SNIPPETS

//    while(textFile.hasNext()) {
//    	
//    	String line = textFile.nextLine();
//    	
//    	String[] words = line.split(" ");
//    	
//    	for(String word: words) {
//    		
//    		if ((!(word.contains("-"))) && (!(word.contains("."))) && !(word.matches(".*\\d.*")) && !(word.length() == 1)
//    	          && (word.length() < 20)) {
//    			
//    			freq.add(word);
//    			
//    			Integer count = Collections.frequency(freq, word);
//    			
//    			System.out.print(word + "\n");
//          
//           if (!(hashObjs.containsKey(word))) {
//            	hashObjs.put(word, new LinkedHashSet<String>());
//            	}
//           
//           	hashObjs.get(word).add(doc);
//           	
//           	SearchEngine.hash.put(word, new indexObject(word, count, doc, hashObjs.get(word)));
//    			
//    		}
//    		
//    	}
// 	
//    }

		// Reading through the dummy text file to find the words

		while (textFile.hasNext()) {

			String word = textFile.next();

			// Counts the instances of words in the File
			if ((!(word.contains("-"))) && (!(word.contains("."))) && !(word.matches(".*\\d.*"))
					&& !(word.length() == 1) && (word.length() < 20)) {

				// stemming will NOT work on upper case words
				Stemmer s = new Stemmer();

				String lc = word.toLowerCase();

				// stemming the word
				if (stem) {

					s.add(lc.toCharArray(), lc.length());

					s.stem();

					lc = s.toString();

					word = lc.toUpperCase();

				}

				freq.add(word);

				Integer count = Collections.frequency(freq, word);

				if (!(hashObjs.containsKey(word))) {
					hashObjs.put(word, new LinkedHashSet<String>());
				}

				hashObjs.get(word).add(doc);

				SearchEngine.hash.put(word, new indexObject(word, count, doc, hashObjs.get(word)));

			}
		}

		textFile.close();

		// If told specifically to avoid stop words, this will purge the hash data
		// structure of stop words

		if (flag) {

			Iterator it = SearchEngine.stopList.entrySet().iterator();

			while (it.hasNext()) {

				Map.Entry pair = (Map.Entry) it.next();

				String str = pair.getKey().toString();

				SearchEngine.hash.remove(str);

			}
		}

		Iterator itt = SearchEngine.hash.entrySet().iterator();

		while (itt.hasNext()) {

			Map.Entry pair = (Map.Entry) itt.next();

			String str = pair.getKey().toString();

			Integer instances = SearchEngine.hash.get(str).getitemCount();

			indexObject newObj = new indexObject(str, instances, doc, hashObjs.get(str));

			SearchEngine.vector.add(newObj);

		}

		if (SearchEngine.hash.containsKey(searchWordUpper)) {

			Integer instances = SearchEngine.hash.get(searchWordUpper).getitemCount();

			indexObject searchObj = new indexObject(searchWordUpper, instances, doc, hashObjs.get(searchWordUpper));

			SearchEngine.searchVector.add(searchObj);

		}

		SearchEngine.hash.clear();

		// If True, the stop words will be removed from the data structure
		// If False, the stop words will remain within

		if (SearchEngine.files.intValue() == SearchEngine.counterList.intValue()) {

			printSearchWord(SearchEngine.queriesOutput, SearchEngine.fixedInt);

			printHash(SearchEngine.resultsOutput);
		}
	}

	@SuppressWarnings("unchecked")
	public static void printSearchWord(String output, int snip) throws IOException {

		if (SearchEngine.searchVector.size() != 0) {

			File file = new File(output);
			FileWriter arg2write = new FileWriter(file);

			// Sorting by number of instances
			Collections.sort(SearchEngine.searchVector);

			arg2write.write(SearchEngine.searchWord.toUpperCase() + " has appeared in "
					+ SearchEngine.searchVector.size() + " file(s): " + "\n" + "\n");

			@SuppressWarnings("rawtypes")
			Iterator it2 = SearchEngine.searchVector.iterator();

			int index = freq.indexOf(SearchEngine.searchWord.toUpperCase());

			String[] strArray = new String[hashObjs.get(SearchEngine.searchWord.toUpperCase()).size()];

			strArray = hashObjs.get(SearchEngine.searchWord.toUpperCase()).toArray(strArray);

			arg2write.write("SNIPPET FROM DOC " + strArray[0] + ":  '");

			strArray = null;

			// for the snippet
			// this will use the flag "fixedInt" to create the snippet

			if (!(index - snip < 0) && !(index + snip > freq.size())) {
				for (int i = (index - snip); i < (index + snip); i++) {
					arg2write.write(freq.get(i) + " ");
				}
			} else {
				arg2write.write(freq.get(index) + " ");
			}
			arg2write.write("'" + "\n" + "\n");

			while (it2.hasNext()) {
				arg2write.write(it2.next() + "\n");
			}
			arg2write.close();
		} else {

			System.out.println("No instances of word " + SearchEngine.searchWord.toUpperCase() + " found");

		}
	}

}
