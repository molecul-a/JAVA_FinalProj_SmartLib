/*
 * Class: LibItem
 * Description: Abstract class that represent structure of all items in system(Book,Magazine,Scientific Work,...)
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "itemType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Book.class, name = "book"),
    @JsonSubTypes.Type(value = Magazine.class, name = "magazine"),
    @JsonSubTypes.Type(value = ScientificWork.class, name = "scientific_work")
})
public abstract class LibItem {
	@JsonProperty("item_id")
	private int itemId;
	@JsonProperty("total_count")
	private int totalCount;
	@JsonProperty("name")
	private String name;
	@JsonProperty("takenById")
	private ArrayList<String> takenById;
	
	public LibItem() {
	}
	
	public LibItem(int itemId, int totalCount, String name)
	{
		takenById = new ArrayList<String>();
		this.itemId = itemId;
		this.totalCount = totalCount;
		this.name = name;
	}
	
 	public boolean ItemTakeBy(String usrId)
	{
		boolean ret = false;
		if(takenById == null)	{
			takenById = new ArrayList<String>();
		}
		
		if((!takenById.contains(usrId)) && (takenById.size() < totalCount) && (usrId != null) && (!usrId.equals(""))) {
			takenById.add(usrId);
			ret = true;
		}
		return ret;
	}
	
	
	public boolean ReturnItem(String usrId) {
		boolean ret = false;
		int idx = 0;
		if((takenById != null) && (usrId != null) && (!usrId.equals("")))	{
			idx=takenById.indexOf(usrId);
			if(idx != -1) {
				takenById.remove(idx);
				ret = true;
			}
		}
		
		return ret;
	}
	
	
	public String toString()
	{
		return "Item id:" + itemId + "; Name: " + name  + "; Available amount:" + (totalCount-takenById.size());
	}


	public int getItemId() {
		return itemId;
	}


	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public String getItemName() {
		return name;
	}

	public void setItemName(String name) { 
		this.name = name;
	}

	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
