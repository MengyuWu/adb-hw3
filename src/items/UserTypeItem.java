package items;

public class UserTypeItem extends Item {
	
	public String userType;
	public UserTypeItem(String type){
		this.userType=type;
		itemName="usertype"+userType;
		type="user";
	}
	
}
