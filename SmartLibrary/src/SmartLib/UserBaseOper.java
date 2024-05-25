/*
 * Class: UserBaseOper
 * Description: Interface that present basic operation that must be applied by child classes(User,Worker,Customer,...)
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;

import java.util.Date;

public interface UserBaseOper {
	public String getFirstName();
	public void setFirstName(String firstName);
	public String getLastName();
	public void setLastName(String lastName);
	public Date getDateOfBirth();
	public void setDateOfBirth(Date dateOfBirth);
	public String getID();
	public void getID(String id);
}
