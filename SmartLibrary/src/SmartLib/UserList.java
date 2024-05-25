/*
 * Class: UserList
 * Description: Class for hold all users during online operation. Implemented as singleton
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import SmartLib.Menu.ClassType;
public class UserList 
{
	private static UserList curListUsr = null;
	@JsonProperty("users")
	private ArrayList<User> usersDb;
	
	public static UserList getInstance(){
		if(curListUsr == null) {
			curListUsr = new UserList();
		}
		return curListUsr;
	}
	
	//Workaround for set newer pointer after restore from db
	public static void setInstance(UserList p)	{
		if(p != null)
		{
			curListUsr = p;
		}
	}
	
	private UserList() {
		usersDb = new ArrayList<User>();
	}
	
	public boolean AddToUsers(User newUSer) {
		boolean ret = false;
		if(newUSer != null){
			if(FindUser(newUSer.getID()) == null )
				ret = usersDb.add(newUSer);
		}
		return ret;
	}
	
	public User FindUser(String id) {
		 for (User user : usersDb) {
		        if ((user != null) && (user.getID().equals(id))) {
		            return user;
		        }
		    }
		 return null;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<User> FindUsers(String fName, String lName) {
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : usersDb) {
			if((user != null) && (user.getFirstName().equals(fName) && user.getLastName().equals(lName))) {
				tempList.add(user);
			}
		}
		return (ArrayList<User>) tempList.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<User> FindUsers(String name) {
		ArrayList<User> tempList = new ArrayList<User>();
		for (User user : usersDb) {
			if((user != null) && (user.getFirstName().equals(name) || user.getLastName().equals(name))) {
				tempList.add(user);
			}
		}
		return (ArrayList<User>) tempList.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<User> GetAllUsers() {
		return (ArrayList<User>) usersDb.clone();
	}
	
	public boolean RemoveUser(String id) {
		boolean bRet = false;
		User tempUser = FindUser(id);
		if(tempUser != null) {
			bRet = usersDb.remove(tempUser);
		}
		return bRet;
	}
	
	public boolean TakeItem(User user, int itemId) {
		boolean ret = false, found = false;
		Customer cus;
		ArrayList<Integer> temp;
		int index;
		
		if(user != null) {
			if(user instanceof Customer) {
				cus = (Customer)user;
				index = usersDb.indexOf(user);
				temp = cus.GetListOfProductsId();
				if(temp != null) {
					for(Integer id : temp) {
						if(id.intValue() == itemId ) {
							found = true;
						}
					}
				}
				
				if(found) {
					System.out.println("Customer already take this item");
				}
				else {
					cus.TakeItem(itemId);
					usersDb.set(index,cus);
					ret = true;
				}
			}
			else {
				System.out.println("Only users can take items");
			}
		}
		return ret;
	}
	
	public boolean ReturnItem(User user, int itemId) {
		boolean ret = false;
		Customer cus;
		//ArrayList<Integer> temp;
		//int index;
		
		if(user != null) {
			if(user instanceof Customer) {
				cus = (Customer)user;
				//index = usersDb.indexOf(user);
				cus.ReturnItem(itemId);
				ret = true;
			}
			else {
				System.out.println("Only users holds items");
			}
		}
		return ret;
	}
	
	public String toString() {
		String output = "";
		
		for (User user : usersDb) {
			output += user.toString() + "\n";
		}
		
		return output;
	}
	
	@SuppressWarnings("exports")
	public String toString(ClassType classType) {
		String output = "";
		
		for (User user : usersDb) {
			if(classType==ClassType.CUSTOMER && (user instanceof Customer))
				output += user.toString() + "\n";
			if(classType==ClassType.WORKER && ((user instanceof Worker)))
				output += user.toString() + "\n";
		}
		
		return output;
	}
}
