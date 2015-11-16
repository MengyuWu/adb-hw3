package items;

import java.util.Calendar;

public class AgeItem extends Item {
	public int ageRange;
	
	
	public AgeItem(int birthYear){
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		this.ageRange=((currentYear-birthYear)/10)*10;
		itemName="age"+ageRange;
		type="age";
	}

	
}
