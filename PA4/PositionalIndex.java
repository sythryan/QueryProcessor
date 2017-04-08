//Author Syth Ryan

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PositionalIndex {

	File[] listOfFiles;
	static Map<String, Map<String, ArrayList<Integer>>> invertedIndex = new HashMap<String, Map<String, ArrayList<Integer>>>();
	static Map<String, Map<String, Double>> docWeightVectorIndex = new HashMap<String, Map<String, Double>>(); // weight of term in that doc
	
	public PositionalIndex(String absoluteFilePath) {
		listOfFiles = new File(absoluteFilePath).listFiles();
		
		for(int i = 0; i < listOfFiles.length; i++) { // for each document
			Map<String, ArrayList<Integer>> docTermPositions =  parseADocument(listOfFiles[i]);
		    Iterator<Entry<String, ArrayList<Integer>>> myIterator = docTermPositions.entrySet().iterator();
		    while (myIterator.hasNext()) {
		        Map.Entry<String, ArrayList<Integer>> myEntry = (Map.Entry<String, ArrayList<Integer>>)myIterator.next();		        
		        Map<String, ArrayList<Integer>> temp = new HashMap<String, ArrayList<Integer>>();
		        if (invertedIndex.containsKey(myEntry.getKey())) {
		        	temp = invertedIndex.get(myEntry.getKey());
		        }
		        temp.put(listOfFiles[i].toString(), myEntry.getValue());
		        invertedIndex.put(myEntry.getKey(), temp);
		    }
		}
		invertedIndex.remove("");
		// build doc weight vectors
		Map<String, Map<String, ArrayList<Integer>>> temp = invertedIndex;
	    Iterator<Entry<String, Map<String, ArrayList<Integer>>>> termIndexIterator = temp.entrySet().iterator();
	    while (termIndexIterator.hasNext()) {
	    	
	        Map.Entry<String, Map<String, ArrayList<Integer>>> termEntry = (Map.Entry<String, Map<String, ArrayList<Integer>>>)termIndexIterator.next();
		    Iterator<Entry<String, ArrayList<Integer>>> docIterator = temp.get(termEntry.getKey()).entrySet().iterator();
		    while (docIterator.hasNext()) {
		        Map.Entry<String, ArrayList<Integer>> docEntry = (Entry<String, ArrayList<Integer>>)docIterator.next();
		        
				//add a weight to the doc vector
				if (docWeightVectorIndex.containsKey(docEntry.getKey())) { // if we already have a vector started for the doc
					Map<String, Double> tempMap = docWeightVectorIndex.get(docEntry.getKey());
					Double tempWeight = weight(termEntry.getKey(), docEntry.getKey());
					tempMap.put(termEntry.getKey(), tempWeight);
					docWeightVectorIndex.put(docEntry.getKey(), tempMap);
				} else { // add a new one
					Map<String, Double> tempMap = new HashMap<String, Double>();
					Double tempWeight = weight(termEntry.getKey(), docEntry.getKey());
					tempMap.put(termEntry.getKey(), tempWeight);
					docWeightVectorIndex.put(docEntry.getKey(), tempMap);
				}
			}
		}	
	}
	
	/* returns term -> Position Indexes; for that document. note: doc number is handled elsewhere */
	private Map<String, ArrayList<Integer>> parseADocument(File document) {
		Map<String, ArrayList<Integer>> retVal = new HashMap<String, ArrayList<Integer>>();
		Scanner myFileScanner = null;
	    try {
	    	myFileScanner = new Scanner(document);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();  
	    }
	    int positionIndex = 0;
	    
	    while (myFileScanner.hasNextLine()) {
            Scanner myLineScanner = new Scanner(myFileScanner.nextLine());
	        while (myLineScanner.hasNext()) {
	            String s = myLineScanner.next().toLowerCase(); //grab next word and make case insensitive
	            s = removePunctuation(s);
	            ArrayList<Integer> temp = new ArrayList<Integer>();
	            if (retVal.containsKey(s)) {
	            	temp = retVal.get(s);
	            }
            	temp.add(positionIndex);
            	retVal.put(s, temp);
            	positionIndex++;
	        }
	        myLineScanner.close();
	    }
	    myFileScanner.close();
		return retVal;
	}
		
	static public String removePunctuation(String s) {
		for(int i = 0; i < s.length(); i++) { //for each character
			if (s.charAt(i) == '.') {
				if(0 == s.indexOf(".")) {
					s = s.replaceFirst(".", "");
					i= i-1;
				} else if (i == s.length() - 1) {
					s = s.substring(0, s.length() - 1);
				} else if ((!Character.isDigit(s.charAt(i-1))) || (!Character.isDigit(s.charAt(i+1)))) {
					s = s.substring(0, i).concat(s.substring(i + 1, s.length()));
					i = i-1;
				}
			}
		}
    	            
        s = s.replaceAll(",", "");
        s = s.replaceAll("\"", "");
        s = s.replaceAll("\\?", "");
        s = s.replaceAll("]", "");
        s = s.replaceAll("\\[", "");
        s = s.replaceAll("'", "");
        s = s.replaceAll("\\{", "");
        s = s.replaceAll("}", "");
        s = s.replaceAll(":", "");
        s = s.replaceAll(";", "");
        s = s.replaceAll("\\(", "");
        s = s.replaceAll("\\)", "");
        
        return s;
	}
	
	/* Returns the number of times term appears in doc. */
	public int termFrequency(String term, String Doc) {
		if (invertedIndex.containsKey(term)) {
			Map<String, ArrayList<Integer>> retVal = invertedIndex.get(term);
			if (retVal.containsKey(Doc)) {
				return retVal.get(Doc).size();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	
	/* Returns the number of documents in which term appears. */
	public int docFrequency(String term) {
		if (invertedIndex.containsKey(term)) {
			return invertedIndex.get(term).size();
			
		} else {
			System.out.println(invertedIndex);
			return 0;
		}
	}
	
	/* Returns string representation of the postings(t). */
	public String postingsList(String t) {
		// format is as follows. returns Map(Documents mapped to list of position indexes);
		return invertedIndex.get(t).toString();
	}
	
	/* Returns the weight of term t in document d */
	public double weight(String t, String d) {
		double temp = (double)docFrequency(t);
		if (temp > 0) {
			return Math.log(1.0+(double)termFrequency(t,d)) / Math.log(2.0) * Math.log10((double)listOfFiles.length / (double)docFrequency(t));
		} else {
			return 0;
		}
	}
	
	public double TPScore(String query, String doc) {
		ArrayList<String> queryVector = new ArrayList<String>();
		StringTokenizer traverser = new StringTokenizer(query);
		while (traverser.hasMoreTokens()){
			queryVector.add(removePunctuation(traverser.nextToken().toLowerCase()));
		}
		
		if (queryVector.size() < 2) {
			return 0; // There is only one word or no words
		} else {
			int sum = 0;
			for (int i = 0; i < queryVector.size() - 1; i++) {
				if((invertedIndex.containsKey(queryVector.get(i))) & (invertedIndex.containsKey(queryVector.get(i+1))) ){ // if we have both terms
					if((invertedIndex.get(queryVector.get(i)).containsKey(doc)) & (invertedIndex.get(queryVector.get(i+1)).containsKey(doc))) { // if they both exist in the doc
						sum = sum + distance(invertedIndex.get(queryVector.get(i)).get(doc), invertedIndex.get(queryVector.get(i+1)).get(doc));
					} else {
						sum = sum + 17;
					}
				} else {
					sum = sum + 17;
				}
			}
			return (double) queryVector.size() / (double) sum;
		}
	}
	
	static public int distance(ArrayList<Integer> tOneList, ArrayList<Integer>  tTwoList) {
		int min = 17;
		for (int i = 0; i < tTwoList.size(); i++) {
			for (int j = 0; j < tOneList.size(); j++ ) {
				if (tTwoList.get(i) > tOneList.get(j)) {
					min = Math.min(min, tTwoList.get(i) - tOneList.get(j));
				}
			}
		}
		return min; 
	}
	
	public double VSScore(String query, String doc) {
		double sum = 0.0;
		StringTokenizer traverser = new StringTokenizer(query);
		int numTokens = traverser.countTokens();
		if (numTokens > 0) {
			if (docWeightVectorIndex.containsKey(doc)) {
				Map<String, Double> docVector = docWeightVectorIndex.get(doc);
				while (traverser.hasMoreTokens()) {
					
					String qWord = removePunctuation(traverser.nextToken().toLowerCase());
					if (docVector.containsKey(qWord)) { // if do doc's weight vector contains the term
						sum = sum + weight(qWord, doc) * docVector.get(qWord);
					}
				}
				sum = sum / (numTokens * docVector.size());
			}
		}
		return sum;
	}

	public double Relevance(String query, String doc) {
		return 0.6 * TPScore(query, doc) + 0.4 * VSScore(query, doc);
	}

}
