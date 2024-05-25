/*
 * Class: DbFileOperations
 * Description: Final class taht implement logic for save/restore all system data(users,items).
 * 				Use Jackson-Core(JSON based) for work. Required Maven project structure. All dependency loacated in pom.xml
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */
package SmartLib;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class DbFileOper {
	 private static final String usrFile = "users.dat";
	 private static final String itemsFile = "items.dat";
	    private static final ObjectMapper mapper = new ObjectMapper();

	    public static UserList readUsersFromFile() {
	        try {
	            return mapper.readValue(new File(usrFile), UserList.class);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    
	 
	    public static void saveUsersToFile(UserList users) {
	        try {
	            mapper.writeValue(new File(usrFile), users);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    

	    public static LibItemList readItemsFromFile() {
	        try {
	            return mapper.readValue(new File(itemsFile), LibItemList.class);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    
	 
	    public static void saveItemsToFile(LibItemList items) {
	        try {
	            mapper.writeValue(new File(itemsFile), items);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
