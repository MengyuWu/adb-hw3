package items;

public class GenderItem extends Item {
	public int gender;
	public GenderItem(int i){
		gender=i;
		itemName="gender"+i;
		type="gender";
	}
}
