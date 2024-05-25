/*
 * Class: LibItemList
 * Description: Class for hold all items during online operation. Implemented as singleton
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LibItemList {
	
	@JsonProperty("items")
	private  ArrayList<LibItem> itemsDb;
	private static LibItemList curList = null; 
	
	public static LibItemList getInstance()	{
		if(curList == null)
		{
			curList = new LibItemList();
		}
		return curList;
	}
	
	//Workaround for set newer pointer after restore from db
	public static void setInstance(LibItemList p)	{
		if(p != null)
		{
			curList = p;
		}
	}
	
	private LibItemList() {
		itemsDb = new ArrayList<LibItem>();
	}
	
	public boolean AddToItems(LibItem newItem) {
		boolean ret = false;
		if(newItem != null){
			if(!itemsDb.contains(newItem))
			{
				ret = itemsDb.add(newItem);
			}
		}
		return ret;
	}
	
	public LibItem FindItem(int id) {
		 for (LibItem item : itemsDb) {
		        if ((item != null) && (item.getItemId() == id)) {
		            return item;
		        }
		    }
		 return null;
	}
	
	public boolean GiveItem(LibItem item, User user) {
		int index = itemsDb.indexOf(item);
		boolean ret = item.ItemTakeBy(user.getID());
		itemsDb.set(index, item);
		return ret;
	}
	
	public boolean ReturnItem(LibItem item, User user) {
		int index = itemsDb.indexOf(item);
		boolean ret = item.ReturnItem(user.getID());
		itemsDb.set(index, item);
		return ret;
	}
	
	public LibItem FindItem(String name) {
		 for (LibItem item : itemsDb) {
		        if ((item != null) && (item.getItemName().equals(name))) {
		            return item;
		        }
		    }
		 return null;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<LibItem> GetAllItems()	{
		return (ArrayList<LibItem>)itemsDb.clone();
	}
	
	public String toString() {
		String output = "";
		
		for (LibItem item : itemsDb)
		{
			output += item.toString() + "\n";
		}
		
		return output;
	}
}
