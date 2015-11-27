import items.AgeItem;
import items.Item;
import items.ItemSet;
import items.Rule;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class FileProcess {
	/* parse the input .csv file, according to the columns in the schema below:
    	Trip Duration (seconds)
    	Start Time and Date
    	Stop Time and Date
    	Start Station Name
    	End Station Name
    	Station ID
    	Station Lat/Long
    	Bike ID
    	User Type
    	Gender
    	Year of Birth
	*/
	public static void readFile(String filename, HashSet<Transaction> hs) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(filename));
		//skip the headerLine
		String headerLine=br.readLine();
		String line=null;
		if(headerLine!=null){
			line=br.readLine();
		}
		int i=1;
		while(line!=null){
			//process
			line=line.replace("\"", "");
			String[] args=line.split(",");
			int tripduration=Integer.parseInt(args[0]);
		    int startStationId=Integer.parseInt(args[3]);
			String startStationName=args[4];
			int endStationId=Integer.parseInt(args[7]);
			String endStationName=args[8];
			String usertype=args[12];
			
			int birthYear=0;
			if(!args[13].isEmpty()){
				birthYear=Integer.parseInt(args[13]);
			}
			
			int gender=Integer.parseInt(args[14]);
			int tID=i; // primary key for transaction
			
			Transaction t=new Transaction(tripduration, startStationId,
					startStationName,  endStationId,  endStationName,
					 usertype, birthYear,gender, tID);
			
			hs.add(t); // add the transaction to the hashset
			
			i++;
			
			line=br.readLine();
		}
		
	}
	
	public static void writeSupportConfToFile(TreeSet<ItemSet> tsup, TreeSet<Rule> tconf, HashMap<String, Double> ruleSupportTable){
		
		System.out.println("==Frequent Itemsets (min_sup="+(int)(AprioriLargeSets.MIN_SUP*100)+"%)");
		for(ItemSet key:tsup){
			System.out.println(key.getItemsString()+", "+(int)(key.support*100)+"%");
		}
		System.out.println("==High-confidence association rules (min_conf="+(int)(AprioriLargeSets.MIN_CONF*100)+"%)");
		for(Rule r:tconf){
			System.out.println(r.rule+"(Conf: "+(int)(r.conf*100)+"%"+",Supp: "+(int)(ruleSupportTable.get(r.rule)*100)+"%)");
		}
		
	}
	
	
	public static void main(String[] args) throws IOException{
		HashMap<ItemSet,Integer> hm=new HashMap<ItemSet,Integer>();
		ItemSet is=new ItemSet();
		Item it=new AgeItem(1990);
		is.items.add(it);
		
		hm.put(is,1);
		
		ItemSet is2=new ItemSet();
		Item it2=new AgeItem(1990);
		is2.items.add(it2);
		
		if(hm.containsKey(is2)){
			System.out.println("contains");
		}
		
	}
}
