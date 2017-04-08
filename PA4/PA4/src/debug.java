import java.util.ArrayList;

//Author Syth Ryan

public class debug {

	public static void main(String[] args) {
		/*ArrayList<Integer> temp1 = new ArrayList<Integer>();
		temp1.add(6);
		temp1.add(18);
		temp1.add(21);
		temp1.add(46);
		
		ArrayList<Integer> temp2 = new ArrayList<Integer>();
		temp2.add(5);
		temp2.add(9);
		temp2.add(11);
		temp2.add(20);
		temp2.add(34);
		*/
		
		//System.out.println(PositionalIndex.removePunctuation("13.88â ft.62"));
		
		//PositionalIndex test = new PositionalIndex("C:/Users/sryan/Desktop/tester");
		//System.out.println(test.VSScore("this", "C:\\Users\\sryan\\Desktop\\tester\\fun.txt"));
		
		/*
		PositionalIndex test = new PositionalIndex("C:/Users/Syth/Desktop/IR/IR");
		
		System.out.println(test.Relevance("nude", "C:\\Users\\Syth\\Desktop\\IR\\IR\\Marilyn_Monroe.txt"));
		System.out.println(test.Relevance("", "C:\\Users\\Syth\\Desktop\\IR\\IR\\Marilyn_Monroe.txt"));
		System.out.println(test.Relevance("Joan Crawford", "C:\\Users\\Syth\\Desktop\\IR\\IR\\Marilyn_Monroe.txt"));
		System.out.println(test.Relevance("Monroe nude 1951", "C:\\Users\\Syth\\Desktop\\IR\\IR\\Marilyn_Monroe.txt"));
		System.out.println(test.Relevance("nude potato", "C:\\Users\\Syth\\Desktop\\IR\\IR\\Marilyn_Monroe.txt"));
		System.out.println(test.Relevance("potato nude", "C:\\Users\\Syth\\Desktop\\IR\\IR\\Marilyn_Monroe.txt"));
		*/
		
		/*
		long time = System.currentTimeMillis();
		QueryProcessor test = new QueryProcessor("C:/Users/Syth/Desktop/IR/IR");
		time = System.currentTimeMillis() - time;
		System.out.println(time);
		
		time = System.currentTimeMillis();
		System.out.println("Running: joan crawford ");
		System.out.println(test.topKDocs("Joan Crawford", 10));
		time = System.currentTimeMillis() - time;
		System.out.println(time);
		time = System.currentTimeMillis();
		System.out.println("Running: This is FUN");
		System.out.println(test.topKDocs("This is FUN", 10));
		time = System.currentTimeMillis() - time;
		System.out.println(time);
		time = System.currentTimeMillis();
		System.out.println("Running: phone number of participant");
		System.out.println(test.topKDocs("phone number of participant", 10));
		time = System.currentTimeMillis() - time;
		System.out.println(time);
		time = System.currentTimeMillis();
		System.out.println("new samsung galaxy smart phone");
		System.out.println(test.topKDocs("new samsung galaxy smart phone", 10));
		time = System.currentTimeMillis() - time;
		System.out.println(time);
		
		*/
		
		PositionalIndex test = new PositionalIndex("C:/Users/Syth/Downloads/data1");
		System.out.println( "Query = massive, Document = file1.txt,");
		System.out.println("Answer = " + test.termFrequency("massive", "C:\\Users\\Syth\\Downloads\\data1\\file1.txt"));

		System.out.println( "Query = the, Document = file1.txt,");
		System.out.println("Answer = " + test.termFrequency("the", "C:\\Users\\Syth\\Downloads\\data1\\file1.txt"));
	
		System.out.println( "Query = hash, Document = file1.txt,");
		System.out.println("Answer = " + test.termFrequency("hash", "C:\\Users\\Syth\\Downloads\\data1\\file1.txt"));
		
		System.out.println("doc freq query = the" );
		System.out.println("Answer = " + test.docFrequency("the"));
		
		System.out.println("doc freq query = massive");
		System.out.println("Answer = " + test.docFrequency("massive"));
		
		System.out.println("weights for query = massive");
		System.out.println("Answer = " + test.weight("massive", "C:\\Users\\Syth\\Downloads\\data1\\file1.txt"));
		
		System.out.println("weights for query = the" );
		System.out.println("Answer = " + test.weight("the", "C:\\Users\\Syth\\Downloads\\data1\\file1.txt"));
		
		System.out.println("TSScore query = efficient data structure");
		System.out.println("Answer = " + test.TPScore("efficient data structure", "C:\\Users\\Syth\\Downloads\\data1\\file6.txt"));
		
		System.out.println("TSScore query = hash table efficient");
		System.out.println("Answer = " + test.TPScore("hash table efficient", "C:\\Users\\Syth\\Downloads\\data1\\file6.txt"));
		
		System.out.println("TSScore query = table hash");
		System.out.println("Answer = " + test.TPScore("table hash", "C:\\Users\\Syth\\Downloads\\data1\\file6.txt"));
		
		System.out.println("TSScore query = hash filter");
		System.out.println("Answer = " + test.TPScore("hash filter", "C:\\Users\\Syth\\Downloads\\data1\\file6.txt")); 
		
		System.out.println("TSScore query = efficient data structure");
		System.out.println("Answer = " + test.TPScore("efficient data structure" , "C:\\Users\\Syth\\Downloads\\data1\\file7.txt"));

		
		System.out.println();
			
	}

}
