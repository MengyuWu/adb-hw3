import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import items.*;

public class Transaction {
	
	public static HashMap<ItemSet , Integer> itemSetMap=new HashMap<ItemSet , Integer>();
	public static HashSet<Transaction> TransactionsSet=new HashSet<Transaction>();
	
	 int tripduration; //(seconds)
	 int startStationId;
	 String startStationName;
	 int endStationId;
	 String endStationName;
	 String usertype; //Subscriber(Annual User), Customer(7 day pass)
	 int birthYear;
	 int gender;  //(Zero=unknown; 1=male; 2=female)
	
	 int TID;
	 ItemSet Titemset;
	 
	 
	 public Transaction(int tripduration, int startStationId,
				String startStationName, int endStationId, String endStationName,
				String usertype, int birthYear, int gender, int tID) {
			super();
			this.tripduration = tripduration;
			this.startStationId = startStationId;
			this.startStationName = startStationName;
			this.endStationId = endStationId;
			this.endStationName = endStationName;
			this.usertype = usertype;
			this.birthYear = birthYear;
			this.gender = gender;
			TID = tID;
			
			Titemset=new ItemSet();
			createItemSet();
			
		}

	
	 public void createItemSet(){
		 
		 
		 if(gender!=0){
			 Item genderItem=new GenderItem(gender); 
			 Titemset.items.add(genderItem);
			 addToSingleItemSetMap(itemSetMap,genderItem);
			 
		 }
		 
	     if(birthYear!=0){
	    	 Item ageItem=new AgeItem(birthYear);
	    	 Titemset.items.add(ageItem);
	    	 //System.out.println("age:"+ (ageItem).itemName); 
	    	 addToSingleItemSetMap(itemSetMap,ageItem);
	     }
		 
		
		 //Item userTypeItem=new UserTypeItem(usertype);
		 //addToSingleItemSetMap(itemSetMap,userTypeItem);
		 
		 Item tripDurationItem=new TripDurationItem(tripduration);
		 addToSingleItemSetMap(itemSetMap,tripDurationItem);
		 
		 //userType is not that intersting, just ignore it
		 //Titemset.items.add(userTypeItem);
		 
		 Titemset.items.add(tripDurationItem);
		 
		 //System.out.println("tripDuration:"+ (tripDurationItem).itemName);
	
	 }
	 
	 public void addToSingleItemSetMap(HashMap<ItemSet, Integer> itemSetMap, Item item){
		 
		 ItemSet is=new ItemSet();
		 is.items.add(item);
		 
		 if(itemSetMap.containsKey(is)){
			 itemSetMap.put(is, itemSetMap.get(is)+1);
		 }else{
			 itemSetMap.put(is, 1);
		 }
	 }
	
}
