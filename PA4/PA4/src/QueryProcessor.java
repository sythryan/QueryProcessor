//Author Syth Ryan

import java.io.File;
import java.util.ArrayList;

/* This program will have a method named topKDocs that gets a query and an integer k as parameter
and returns an arraylist consisting of top k documents that are relevant to the query. */

//Syth Ryan

class QueryProcessor {

	PositionalIndex myPosIndex;
	File[] listOfFiles;
	
	QueryProcessor(String filePathToDocuments) {
		myPosIndex = new PositionalIndex(filePathToDocuments);
		listOfFiles = new File(filePathToDocuments).listFiles();
	}
	
	public ArrayList<String> topKDocs(String query, int k) {
		ArrayList<String> retVal = new ArrayList<String>();
		WeightedKQ myQueue = new WeightedKQ(k);
		for (int i = 0; i < listOfFiles.length; i++) {
			myQueue.Add(new Tuple2<String, Double>(listOfFiles[i].toString(), myPosIndex.Relevance(query, listOfFiles[i].toString())));
		}
		
		for (int i = 0; i < k; i++) {
			/*// printlns for report
			System.out.println("Relevance = " + myQueue.Queue.getFirst()._1);
			System.out.println("VSScore   = " + myPosIndex.VSScore(query, myQueue.Queue.getFirst()._0));
			System.out.println("TPScore   = " + myPosIndex.TPScore(query, myQueue.Queue.getFirst()._0));
			*/
			retVal.add(myQueue.Extract());
		}
		return retVal;
	}
}
