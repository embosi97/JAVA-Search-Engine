package CorpusDir;

import java.util.LinkedHashSet;

@SuppressWarnings("rawtypes")
public class indexObject implements Comparable {

	private String document;
	private Integer itemCount;
	private String word;
	private LinkedHashSet<String> arr;

	public indexObject() {
		this.word = "null";
		this.itemCount = 0;
		this.document = "null";
	}

	public indexObject(String word, Integer itemCount, String document, LinkedHashSet<String> arr) {
		this.word = word;
		this.itemCount = itemCount;
		this.document = document;
		this.arr = arr;
	}

	// necessary getters
	public String getWord() {
		return word;
	}

	public Integer getitemCount() {
		return itemCount;
	}

	public String getDocument() {
		return document;
	}

	// necessary setters
	public void setString(String word) {
		this.word = word;
	}

	public void setitemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	// modified compareTo
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		int freqDif = ((indexObject) o).getitemCount();
		return (int) (freqDif - itemCount);
	}

	// modified toString
	@Override
	public String toString() {

		if (itemCount > 1) {

			return " " + word + " appears in " + document + " " + itemCount + " times. It appears in these documents: "
					+ arr + "\n";

		} else {

			return " " + word + " appears in " + document + " " + itemCount + " time. It appears in these documents: "
					+ arr + "\n";
		}
	}

}