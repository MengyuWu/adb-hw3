package items;

public class TripDurationItem extends Item {
	public int minRange;
	
	public TripDurationItem(int seconds){
		minRange=((seconds/60)/10)*10;
		itemName="tripMin"+minRange;
		type="tripDuration";
	}
}
