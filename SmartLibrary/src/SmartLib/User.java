/*
 * Class: User
 * Description: Abstract class that used as model for Worker and Customer
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "userType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Customer.class, name = "customer"),
    @JsonSubTypes.Type(value = Worker.class, name = "worker")
})
public abstract class User implements UserBaseOper
{
	@JsonProperty("first_name")
	protected String firstName;
	@JsonProperty("last_name")
	protected String lastName;
	@JsonProperty("id")
	protected String id;
	@JsonFormat(pattern = "yyyy-MM-dd")
	protected Date dateOfBirth;
	@JsonProperty("password")
	protected String password;
		
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		if((firstName != null) && (!firstName.equals(""))) {
			this.firstName = firstName;
		}
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		if((lastName != null) && (!lastName.equals(""))) {
			this.lastName = lastName;
		}
	}
	
	public Date getDateOfBirth()
	{
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth)
	{
		if(dateOfBirth != null) {
			this.dateOfBirth = dateOfBirth;
		}
	}
	
	public String getID()
	{
		return id;
	}
	
	public void getID(String id)
	{
		this.id = id;
	}
	
	public boolean setPassword(String password)
	{
		boolean ret = false;
		if(password != null)
		{
			this.password = password;
			ret = true;
		}
		return ret;
	}
	
	public boolean checkPassword(String password)
	{
		boolean ret = false;
		if(password != null)
		{
			if(this.password.equals(password))
			{
				ret=true;
			}
		}
		return ret;
	}
	
	public void resetPassword()
	{
		setPassword("1234");
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public String toString()
	{
		return "Name: " + firstName + " " + lastName + " ID: " + id + " Birth date: " + dateOfBirth.toString();
	}

}
