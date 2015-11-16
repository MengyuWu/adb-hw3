package items;

public abstract class Item implements Comparable {
	
	public String itemName="";
	public String type="";
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Item item=(Item)o;
		return this.itemName.compareTo(item.itemName);
	}
	
	@Override
	public int hashCode()
	{
	    return itemName.hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
		String objName=((Item)o).itemName;
	    return this.itemName.equals(objName);
	}
	
	
}
