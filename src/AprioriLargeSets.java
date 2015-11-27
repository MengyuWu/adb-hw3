import items.Item;
import items.ItemSet;
import items.Rule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class AprioriLargeSets {
	
	// includes the set of items of size 1 that pass the min support threshold (frequent items)
	public static Set<Item> firstItemSet=new TreeSet<Item>();
	
	public static double MIN_SUP=0.1;
	public static double MIN_CONF=0.6;
	
	// rest of Apriori Algorithm
	public static void createLargeItemSetK(List<Set<ItemSet>> largeSets, double minSup, double minConf, int totalT){
		int k=largeSets.size(); 
		Set<ItemSet> Lprev=largeSets.get(k-1); // get the set of all large (k-1)-itemsets
		
		// add k item set to largeSets until the k-itemset is empty
		while(Lprev!=null){
			System.out.println("In level:"+(largeSets.size()+1));
			Set<ItemSet> Lnext=new HashSet<ItemSet>(); // this is L_k, start with it empty
			
			Set<ItemSet> Cknext=aprioriGen(Lprev,firstItemSet);
			
			// count the frequency of itemset from each transaction
			// if the transaction contains that itemset, count++
			HashMap<ItemSet,Integer> hm=new HashMap<ItemSet,Integer>();
			for(Transaction t: Transaction.TransactionsSet){
				for(ItemSet itemSet:Cknext){
					if(belongTo(itemSet,t)){
						if(hm.containsKey(itemSet)){
							hm.put(itemSet, hm.get(itemSet)+1);
						}else{
							hm.put(itemSet, 1);
						}
						
					}
				}
			}
			
			Set<ItemSet> itmeSets=hm.keySet();
			// if the itemset's support > the minimum support threshold, add it to the k-itemset
			for(ItemSet itemset: itmeSets){ 
				if(hm.get(itemset)>=minSup*totalT){
					
					//test
					for(Item i:itemset.items){
						System.out.print(i.itemName+" ");
					}
					System.out.print("count"+hm.get(itemset));
					System.out.println();
					
					//calculate the support for each itemset, and add to the k-itemset
					itemset.support=((double)hm.get(itemset))/totalT;
					Lnext.add(itemset);
				}
			}
			
			// algorithm termination condition: L_k = empty set
			if(Lnext.size()==0){
				break;
			}
			
			// union L_k with what we have so far
			largeSets.add(Lnext);
			Lprev=Lnext;
			
		}
	}
	
	
	
	public static boolean belongTo(ItemSet cset, Transaction t){
		
		Set<Item> TSet=t.Titemset.items;
		
		for(Item i:cset.items){
			if(!TSet.contains(i)){
				return false;
			}
		}
		
		return true;
	}
	
	/* 
	   Apriori Candidate Generation, which extends the frequent subsets one item
	   at a time; this function takes as argument L_k-1, the set of all large
	   (k-1)-itemsets and returns the superset of the set of all large k-itemsets.
	*/
	public static Set<ItemSet> aprioriGen(Set<ItemSet> prev, Set<Item> first){
		Set<ItemSet> next=new HashSet<ItemSet>();
		
		// join step, add all possible itemSet
		for(ItemSet set:prev){
			Set<Item> items=set.items;
			for(Item item:first){
				ItemSet nItemSet=new ItemSet();
				nItemSet.items.addAll(items);
				nItemSet.items.add(item);
				
				// Insert into C_k 
				if(nItemSet.items.size()==(set.items.size()+1)){
					next.add(nItemSet);
				}
				
			}
		}
		
		// prune step, delete all itemsets where some (k-1) subset of c is not in prev (L_k-1)
		Iterator<ItemSet> itr=next.iterator();
		while(itr.hasNext()){
			ItemSet iSet=itr.next();
			if(subsetNotIn(iSet, prev)){
				itr.remove();
			}		
		}
		return next;
	}

	public static List<Set<Item>> getSubSet(ItemSet iSet){
		Set<Item> items=iSet.items;
		List<Set<Item>> res=new ArrayList<Set<Item>>();
		
		for(Item i:items){
			TreeSet<Item> treeset=new TreeSet<Item>(items);
			treeset.remove(i);
			res.add(treeset);	
		}

	
		return res;
	}
	
	public static boolean subsetNotIn(ItemSet iSet, Set<ItemSet> prev){
		List<Set<Item>> subsets=getSubSet(iSet);
		//as long as one subset in subsets not belongs to prev return true
		for(Set<Item> subset: subsets){
			
			if(!belongTo(subset,prev)){
				return true;
			}
			
		}
		return false;
	}
	
	public static boolean belongTo(Set<Item> subset, Set<ItemSet> prev){
		//as long as one set does not belong to prev, it will return false;
       for(ItemSet iset:prev){
    	   Set<Item> itmes=iset.items;
    	   //test whether set == itmes
    	   if(setEquals(subset,itmes)){
    		   return true;
    	   }  
       }
       
       return false;
	}
	
	public static boolean setEquals(Set<Item> a, Set<Item> b){
		
		for(Item i:a){
			if(!b.contains(i)){
				return false;
			}
		}
		
		return true;
	}
	
	public static void createSupportSet(HashMap<String, Double> hm,TreeSet<ItemSet> ts, List<Set<ItemSet>> largeSets){
		for(int i=0; i<largeSets.size(); i++){
			Set<ItemSet> set=largeSets.get(i);
			for(ItemSet is:set){
				//System.out.println(is.getItemsString()+","+(int)(is.support*100)+"%");
				ts.add(is);
				hm.put(is.getItemsString(), is.support);
			}
		}
	}
	
	public static void getRules(HashMap<String, Double> hm,TreeSet<Rule> ts, List<Set<ItemSet>> largeSets, HashMap<String, Double> supportTable){
		for(int i=1; i<largeSets.size(); i++){
			Set<ItemSet> set=largeSets.get(i);
			for(ItemSet is:set){
				//create all possible rules, split the item into 2 parts LHS AND RHS
				Set<Item> items=is.items;
				List<Set<Item>> subsetlists=subsets(items);
				for(Set<Item> s: subsetlists){
					if(s.size()>=1 && s.size()<items.size()){
						Set<Item> LHS=s;
						Set<Item> RHS=new TreeSet<Item>(items);
						RHS.removeAll(s);
						
						String lhsStr=setItemToString(LHS);
						String rhsStr=setItemToString(RHS);
						String landrStr=setItemToString(items);
						
						double lhssup=supportTable.get(lhsStr);
						double rhssup=supportTable.get(rhsStr);
						
						double lAndrSup=supportTable.get(landrStr);
						double conf=lAndrSup/lhssup;

						// output only the rules that are greater than or equal to the min_conf						
						if(conf>=MIN_CONF){
							String ruleStr=lhsStr+" =>"+rhsStr;
							Rule r=new Rule(ruleStr,conf);
							ts.add(r);
							hm.put(ruleStr, lAndrSup);
						}
						
					}
					
					
					
				}
			}
		}
	
	}
	
	public static String setItemToString(Set<Item> set){
		String itemsStr="";
		int k=0;
		for(Item i:set){
			if(k==0){
				itemsStr+=i.itemName;
			}else{
				itemsStr+=","+i.itemName;
			}
			k++;
			
		}
		
		itemsStr="["+itemsStr+"]";
		
		return itemsStr;
	}
	
	public static List<Set<Item>> subsets(Set<Item> items){
		//System.out.println("subsets:"+items.size());
		List<Set<Item>> res=new ArrayList<Set<Item>>();
		if(items.isEmpty()){
			return res;
		}
		
		Iterator<Item> itr=items.iterator();
		while(itr.hasNext()){
			Item i=itr.next();
			Set<Item> sub=new TreeSet<Item>(items);
			sub.remove(i);
			
			List<Set<Item>> subres=subsets(sub);
		
			int size=subres.size();
			Set<Item> s=new TreeSet<Item>();
			s.add(i);
			subres.add(s);
			
			
			for(int k=0; k<size; k++){
				Set<Item> set=new TreeSet<Item>(subres.get(k));
				set.add(i);
				subres.add(set);
				
			}
			
			res=subres;
			
			break;
		}
		
		
		return res;
		
	}
	
	
	public static void main(String[] args) throws IOException {
		// input validation
		String filename=args[0];
		double minSup=0;
		double minConf=0;
		try {
			minSup=Double.parseDouble(args[1]);
			minConf=Double.parseDouble(args[2]);
		} catch (NumberFormatException e) {
			System.out.println("min_sup and min_conf should be doubles");
			System.exit(1);
		}

		if (minSup >= 1 || minSup <= 0 || minConf >=1 || minConf <= 0) {
			System.out.println("min_sup and/or min_conf beyond accepted range (between 0-1)");
			System.exit(1);
		}

		MIN_SUP = minSup;
		MIN_CONF = minConf;

		/* parse file record by record, create Transaction objects for each record and add
		   them to itemset */
		FileProcess.readFile(filename,Transaction.TransactionsSet);
		
		// General Apriori Algorithm Implementation
		// Find large 1-itemsets
		Set<ItemSet> keys=Transaction.itemSetMap.keySet();
		
		List<Set<ItemSet>> largeSets=new ArrayList<Set<ItemSet>>();
		
		//create large 1-itemsets;
		int totalTransactions=Transaction.TransactionsSet.size();
		Set<ItemSet> L1=new HashSet<ItemSet>();
		for(ItemSet key:keys){
			int count=Transaction.itemSetMap.get(key);
			// check if the count of the item exceeds the min support
			if(count>=minSup*totalTransactions){
				//add the item set to the first large item sets
				
				key.support=(double)count/(double)totalTransactions;
				
				for(Item i: key.items){
					System.out.print(i.itemName+"  ");
					firstItemSet.add(i);
				}
				
				System.out.println("count:"+count);
				L1.add(key);
			}
		}
		
		largeSets.add(L1);
		
		// remaining part of Apriori Algorithm, from 2-itemsets to k-itemsets
		createLargeItemSetK(largeSets,minSup,minConf,totalTransactions);
		
		//largeSets contains largest k-itemset
		//calculate conf for each possible rules, start from 2-itemset
		TreeSet<ItemSet> tsSup=new TreeSet<ItemSet>();
		HashMap<String, Double> supportTable=new HashMap<String,Double>();
		createSupportSet(supportTable, tsSup,  largeSets);
		
		TreeSet<Rule> tsConf=new TreeSet<Rule>();
		
		HashMap<String, Double> ruleSupportTable=new HashMap<String,Double>();
		
		getRules(ruleSupportTable,tsConf, largeSets, supportTable);
		FileProcess.writeSupportConfToFile(tsSup,tsConf,  ruleSupportTable);
		
	}

}
