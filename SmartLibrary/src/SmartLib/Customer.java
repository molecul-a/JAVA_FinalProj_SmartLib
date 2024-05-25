/*
 * Class: Customer
 * Description: Base class that present Customer object structure and internal operations.
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer extends User {
	@JsonProperty("product_ids")
    private ArrayList<Integer> productsID ;
	
	public Customer() {
		super();
	}
    // Constructor
    public Customer(String firstName, String lastName, String id, Date dateOfBirth){
    	this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.password = "1234";
        this.productsID = new ArrayList<Integer>();
    }
    
	
	public String toString() {
		return super.toString();
	}
	
	public void TakeItem(int id) {
		productsID.add(Integer.valueOf(id));
	}
	
	public void ReturnItem(int id) {
		productsID.remove(productsID.indexOf(Integer.valueOf(id)));
	}
	
	public String ShowTakenProduct()
	{
		LibItem temp;
		String output = "";
		if (productsID == null) {
			return "You don't have items!";
		}
		else {
			for (Integer productID : productsID)
			{
				temp = LibItemList.getInstance().FindItem(productID.intValue());
				output += (temp != null)?temp.toString() + "\n" : "";
			}
			return (output.equals(""))? "Not found any items" : output;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Integer> GetListOfProductsId()
	{
		return (productsID != null)?(ArrayList<Integer>)productsID.clone() : null;
	}
}
