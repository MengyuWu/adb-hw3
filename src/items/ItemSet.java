package items;

import java.util.Set;
import java.util.TreeSet;

public class ItemSet implements Comparable {
	public Set<Item> items;
	public double support;
    
	public ItemSet(){
		items=new TreeSet<Item>();
		support=0;
	}
	
	public String getItemsString(){
		String itemsStr="";
		int k=0;
		for(Item i:items){
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
	
	@Override
	public int compareTo(Object o) {
		ItemSet iset=(ItemSet)o;
		double res=iset.support-this.support;
		if(res==0){
			return 0;
		}else if(res>0){
			return 1;
		}else {
			return -1;
		}
	}

	
	@Override
	public int hashCode()
	{
		String hashStr="";
		for(Item i:items){
			hashStr+=i.itemName+" ";
		}
		//System.out.println(hashStr+" code:"+hashStr.hashCode());
	    return hashStr.hashCode();
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		ItemSet is=(ItemSet)o;
		if(is.items.size()!=this.items.size()){
			return false;
		}
		for(Item i:items){
			if(!is.items.contains(i)){
				return false;
			}
		}
		
		return true;
		
	}

	

	


	
}
