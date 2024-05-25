/*
 * Class: Worker
 * Description: Base class that present Worker object structure and internal operations.
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Worker extends User implements UserBaseOper
{
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startWorkDate;
	@JsonProperty("position")
	private WorkPosition position;
    //private String userType = "worker"; 
	
	public Worker() {
		super();
	}
	public Worker(String firstName, String lastName, String id, Date dateOfBirth, WorkPosition position) {
    	this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
        this.id = id;
        this.startWorkDate = new Date();
        this.password = "1234";
    }
	
	public Worker(String firstName, String lastName, String id, Date dateOfBirth, WorkPosition position, Date stratWorkDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
        this.position = WorkPosition.Librarian;
        this.startWorkDate = stratWorkDate;
        this.password = "1234";
    }
	
	public Worker(String firstName, String lastName, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.dateOfBirth = new Date();
        this.position = WorkPosition.Librarian;
        this.startWorkDate = new Date();
        this.password = "1234";
    }
	
	public String toString() {
		return super.toString() + " Position: " + position + " Start work at: " + startWorkDate.toString();
	}
	
	public WorkPosition GetWorkPosition()
	{
		return position;
	}
	
	public void SetNewPosition(WorkPosition newPosition) {
		this.position = newPosition;
	}
	
	public Date GetStartWorkDate()
	{
		return startWorkDate;
	}
}
