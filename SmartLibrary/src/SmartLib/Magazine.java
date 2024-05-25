/*
 * Class: Magazine
 * Description: Base class that present Magazine object structure
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Magazine extends LibItem{
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date released;
	
	public Magazine() {
		super();
	}
	
	public Magazine(int itemId, int totalCount, String name, Date released) {
		super(itemId,totalCount,name);
		this.released = released;
	}
	
	public String toString()
	{
		return super.toString() + "; Released: " + released.toString();
	}
}
