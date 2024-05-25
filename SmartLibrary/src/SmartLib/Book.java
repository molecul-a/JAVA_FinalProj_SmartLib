/*
 * Class: Book
 * Description: Base class that present Book object structure
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Book extends LibItem{
	@JsonProperty("isbn")
	private String ISBN;
	@JsonProperty("edition")
	private int edition;
	@JsonProperty("written_by")
	private String writenBy;
	
	public Book() {
		super();
	}
	
	public Book(int itemId, int totalCount, String name, String ISBN, int edition, String writenBy) {
		super(itemId,totalCount,name);
		this.ISBN = ISBN;
		this.edition = edition;
		this.writenBy = writenBy;
	}
	
	public String toString()
	{
		return super.toString() + "; ISBN: " + ISBN + "; Edition: " + edition + "; Writen by: " + writenBy;
	}
}
