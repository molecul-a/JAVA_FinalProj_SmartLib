/*
 * Class: ScientificWork
 * Description: Base class that present ScientificWork object structure
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */

package SmartLib;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScientificWork extends LibItem {
	@JsonProperty("written_by")
	private String writenBy;
	@JsonProperty("inUniversity")
	private String inUniversity;
	@JsonProperty("subject")
	private String subject;
	
	public ScientificWork() {
		super();
	}
	
	public ScientificWork(int itemId, int totalCount, String name,String writenBy,String inUniversity,String subject) {
		super(itemId,totalCount,name);
		this.writenBy = writenBy;
		this.inUniversity = inUniversity;
		this.subject = subject;
	}
	public String toString()
	{
		return super.toString() + "; Subject: " + subject + "; Writen by: " + writenBy + "; in University: " + inUniversity;
	}
}
