/*
 * Class: Menu
 * Description: Main operation class. Used for console menu presentation and implements basic logic for access to classes
 * Author: Artem Andreychenko 336229646
 * 		   Ronen Lobachev 203767249
 * 		   Arel Arphi 316391481
 * 
 */

package SmartLib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Menu 
{
	//Final declarations
	private final int MAX_PASS_RETRY = 3;
	//Enum
	enum MenuMode
	{
		LOGIN_MENU,
		WORKER_MENU,
		CUSTOMER_MENU,
		ADMIN_MENU
	}
	enum ClassType
	{
		ANY,
		WORKER,
		CUSTOMER
	}
	
	//Global variables
	private User curUser;
	private Scanner sc;
	private UserList userList;
	private LibItemList itemList;
	private MenuMode curMode;
	
	
	public Menu() {
		curMode = MenuMode.LOGIN_MENU;
		sc = new Scanner(System.in); 
		
		itemList = DbFileOper.readItemsFromFile();
		LibItemList.setInstance(itemList);
		userList = DbFileOper.readUsersFromFile();
		UserList.setInstance(userList);
		//System.out.println(DbFileOper.readUsersFromFile().toString());
		//System.out.println(DbFileOper.readItemsFromFile().toString());
	}
	
	public void MainMenu() {
		MenuMode newMode = curMode;
		while(true)	{
			switch(curMode)	{
				case LOGIN_MENU:
					System.out.println("********************\nLogin menu:");
					newMode = LoginMenu();
					System.out.println("********************");
					break;
					
				case WORKER_MENU:
					System.out.println("********************\nWorker menu:");
					newMode = WorkerMenu();
					System.out.println("********************");
					break;
					
				case CUSTOMER_MENU:
					System.out.println("********************\nCustomer menu:");
					newMode = CustomerMenu();
					System.out.println("********************");
					break;
				case ADMIN_MENU:
					System.out.println("********************\nAdmin menu:");
					newMode = AdminMenu();
					System.out.println("********************");
					break;
			}
			
			if(newMode != curMode)
			{
				System.out.println("Save all changes to DB");
				//Store all changes
				DbFileOper.saveUsersToFile(userList);
				DbFileOper.saveItemsToFile(itemList);
			}
			curMode = newMode;
		}
	}
	
	/**************************************************************/
	/****************LOGIN OPERATIONS******************************/
	/**************************************************************/
	
	private MenuMode LoginMenu() {
		int retry = MAX_PASS_RETRY;
		MenuMode iMode = MenuMode.LOGIN_MENU;
		String tmpUserId, tmpUserPass;
		User tmpObject;
		
		while((iMode == MenuMode.LOGIN_MENU) && ((retry > 0))) {
			System.out.print("Enter ID: ");
			tmpUserId = sc.nextLine();
			System.out.print("Enter passcode: ");
			tmpUserPass = sc.nextLine();
			
			//Try to find in workers first
			tmpObject=userList.FindUser(tmpUserId);
			if(tmpObject != null) {
				if(tmpObject.checkPassword(tmpUserPass)) {
					System.out.println("Login as " + tmpObject.getClass().getSimpleName());
					this.curUser = tmpObject;
					if(tmpObject instanceof Worker) {
						if (((Worker) tmpObject).GetWorkPosition()==WorkPosition.Admin) {
							iMode = MenuMode.ADMIN_MENU;
						}else {
							iMode = MenuMode.WORKER_MENU;
						}	
					}
					else {
						iMode = MenuMode.CUSTOMER_MENU;
					}
				}
				else {
					System.out.println("Wrong ID or Password");
				}
			}
		}
		
		return iMode;
	}
	
	/**************************************************************/
	/****************ADMIN OPERATIONS******************************/
	/**************************************************************/

	private MenuMode AdminMenu() {
		final int minOpt = 1, maxOpt = 11;
		int option;
		MenuMode curMode = MenuMode.ADMIN_MENU;
		
		System.out.print("1)Add User\n2)Show users\n" + 
						 "3)Find user\n4)Remove user\n" + 
						 "5)Change password\n6)Find item\n" +
						 "7)Add new item\n8)Show all items\n" + 
						 "9)Exit\n");
		
		while(true)	{
			if(sc.hasNextInt())	{
				option = sc.nextInt();
				if(!CheckInput(option, minOpt, maxOpt))	{
					sc.nextLine();
					System.out.println("Iligal option");
				}
				else {
					sc.nextLine();
					break;
				}
			}
		}
		
		switch(option) {
			case 1:
				int tmp_option;
				System.out.println("Enter type of USER:");
				System.out.print("1)Customer\n2)Worker\n" + 
						 "3)Exit\n");
				
				while(true)	{
					if(sc.hasNextInt())	{
						tmp_option = sc.nextInt();
						if(!CheckInput(tmp_option, 1, 3))	{
							sc.nextLine();
							System.out.println("Iligal option");
						}
						else {
							sc.nextLine();
							break;
						}
					}
				}
				
				switch(tmp_option) {
				case 1:
					PrintStatus(AddUserToList(ClassType.CUSTOMER));
					break;
				case 2:
					PrintStatus(AddUserToList(ClassType.WORKER));
					break;
				case 3:
					break;
				}
				
				break;
			case 2:
				int tmp_option1;
				System.out.println("Enter type of USER:");
				System.out.print("1)All Customers\n2)All Workers\n3)All Users\n" + 
						 "4)Exit\n");
				
				while(true)	{
					if(sc.hasNextInt())	{
						tmp_option1 = sc.nextInt();
						if(!CheckInput(tmp_option1, 1, 4))	{
							sc.nextLine();
							System.out.println("Iligal option");
						}
						else {
							sc.nextLine();
							break;
						}
					}
				}
				
				switch(tmp_option1) {
				case 1:
					System.out.println(userList.toString(ClassType.CUSTOMER));
					break;
				case 2:
					System.out.println(userList.toString(ClassType.WORKER));
					break;
				case 3:
					System.out.println(userList.toString());
					break;
				case 4:
					break;
				}
				
				
				break;
			case 3:
				
				PrintStatus(FindUser(ClassType.ANY));
				break;
			case 4:
				PrintStatus(RemoveUser(ClassType.ANY));
				break;
			case 5:
				PrintStatus(ChangeUserPassword(curUser));
				break;
			case 6:
				PrintStatus(FindItem());
				break;
			case 7:
				PrintStatus(AddNewItem());
				break;
			case 8:
				System.out.println(itemList.toString());
				break;
			case 9:
				curMode = MenuMode.LOGIN_MENU;
		}
		
		return curMode;
	}
	
	/**************************************************************/
	/***************WORKER OPERATIONS******************************/
	/**************************************************************/

	private MenuMode WorkerMenu() {
		final int minOpt = 1, maxOpt = 11;
		int option;
		MenuMode curMode = MenuMode.WORKER_MENU;
		
		System.out.print("1)Add customer\n2)Show customers\n" + 
						 "3)Find customer\n4)Remove customer\n" + 
						 "5)Change password\n6)Find item\n" +
						 "7)Add new item\n8)Show all items\n" + 
						 "9)Give item to customer\n10)Return Item\n" +
						 "11)Exit\n");
		
		option = MenuOptionChoiceRead(minOpt,maxOpt);
		
		switch(option) {
			case 1:
				PrintStatus(AddUserToList(ClassType.CUSTOMER));
				break;
			case 2:
				System.out.println(userList.toString(ClassType.CUSTOMER));
				break;
			case 3:
				PrintStatus(FindUser(ClassType.CUSTOMER));
				break;
			case 4:
				PrintStatus(RemoveUser(ClassType.CUSTOMER));
				break;
			case 5:
				PrintStatus(ChangeUserPassword(curUser));
				break;
			case 6:
				PrintStatus(FindItem());
				break;
			case 7:
				PrintStatus(AddNewItem());
				break;
			case 8:
				System.out.println(itemList.toString());
				break;
			case 9:
				PrintStatus(GiveItemToCustomer());
				break;
			case 10:
				PrintStatus(ReturnItem());
				break;
			case 11:
				curMode = MenuMode.LOGIN_MENU;
		}
		
		return curMode;
	}

	private boolean AddNewItem()
	{
		boolean ret = false, ok = false, flag = false;
		int option;
		int id = 0, total_cnt = 0,edition = 0;
		String name,isbn,writenby,university,subject,date;
		Date tmpDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		
		System.out.print("Choice type of item:\n" +
				 		 "1)Book\n2)Magazine\n3)Scientific Work\n");
		option = MenuOptionChoiceRead(1,3);
		
		while(!ok) {
			try {
				System.out.println("Enter ID: ");
				id = sc.nextInt();
				sc.nextLine();
				if(itemList.FindItem(id)!=null) {
					System.out.println("ID already exist, input other one");
				}
				ok = true;
			}
			catch (InputMismatchException e) {
			    System.err.println("Wrong input! Input only integer numbers please...");
			    sc.nextLine();
			}
		}
		ok = false;
		
		System.out.println();
		
		while(!ok) {
			try {
				System.out.print("Enter total count: ");
				total_cnt = sc.nextInt();
				sc.nextLine();
				ok = true;
			}
			catch (InputMismatchException e) {
			    System.err.println("Wrong input! Input only integer numbers please...");
			    sc.nextLine();
			}
		}
		ok = false;
		System.out.println();
		System.out.print("Name: ");
		name = sc.nextLine();
		System.out.println();
		switch(option) {
			case 1:
				System.out.print("ISBN: ");
				isbn = sc.nextLine();
				System.out.println();
				while(!ok) {
					try {
						System.out.print("Edition: ");
						edition = sc.nextInt();
						sc.nextLine();
						ok = true;
					}
					catch (InputMismatchException e) {
					    System.err.println("Wrong input! Input only integer numbers please...");
					    sc.nextLine();
					}
				}
				ok = false;
				
				System.out.println();
				System.out.print("Writen by: ");
				writenby = sc.nextLine();
				System.out.println();
				ret = itemList.AddToItems(new Book(id,total_cnt,name,isbn,edition,writenby));
				break;
			case 2:
				while(!flag)
				{
					try {
						System.out.println("Enter date in format dd/MM/yyyy");
						date = sc.nextLine();
						tmpDate = formatter.parse(date);
						flag = true;
					} catch (ParseException e) {
						ret = false;
						System.err.println("Error:Enter date in format dd/MM/yyyy");
						//e.printStackTrace();
					} 
				}
				ret = itemList.AddToItems(new Magazine(id,total_cnt,name,tmpDate));
				break;
			case 3:
				System.out.print("Writen by: ");
				writenby = sc.nextLine();
				System.out.println();
				System.out.print("University: ");
				university = sc.nextLine();
				System.out.println();
				System.out.print("Subject: ");
				subject = sc.nextLine();
				System.out.println();
				ret = itemList.AddToItems(new ScientificWork(id,total_cnt,name,writenby,university,subject));
				break;
		}
		
		return ret;
	}
	
	private boolean GiveItemToCustomer() {
		boolean ret = false, ok = false;
		int id = 0;
		String cusId;
		LibItem tmpItem;
		User tmpUser;
		
		while(!ok) {
			try {
				System.out.println("Enter item ID");
				id = sc.nextInt();
				sc.nextLine();
				ok = true;
			}
			catch (InputMismatchException e) {
			    System.err.println("Wrong input! Input only integer numbers please...");
			    sc.nextLine();
			}
		}
		ok = false;
		tmpItem = itemList.FindItem(id);
		if(tmpItem == null) {
			System.out.println("Item with given ID not found");
			return false;
		}
		System.out.println("Enter customer ID");
		cusId = sc.nextLine();
		tmpUser = userList.FindUser(cusId);
		if(tmpUser == null) {
			System.out.println("User with given id not found");
			return false;
		}
		
		ret = itemList.GiveItem(tmpItem, tmpUser);
		if(ret) {
			ret = userList.TakeItem(tmpUser, tmpItem.getItemId());
		}
		return ret;
	}
	
	private boolean ReturnItem() {
		boolean ret = false, ok = false;
		int id = 0;
		String cusId;
		LibItem tmpItem;
		User tmpUser;
		
		while(!ok) {
			try {
				System.out.println("Enter item ID");
				id = sc.nextInt();
				sc.nextLine();
				ok = true;
			}
			catch (InputMismatchException e) {
			    System.err.println("Wrong input! Input only integer numbers please...");
			    sc.nextLine();
			}
		}
		ok = false;
		tmpItem = itemList.FindItem(id);
		if(tmpItem == null) {
			System.out.println("Item with given ID not found");
			return false;
		}
		System.out.println("Enter customer ID");
		cusId = sc.nextLine();
		tmpUser = userList.FindUser(cusId);
		if(tmpUser == null) {
			System.out.println("User with given id not found");
			return false;
		}
		
		ret = itemList.ReturnItem(tmpItem, tmpUser);
		if(ret) {
			ret = userList.ReturnItem(tmpUser, tmpItem.getItemId());
		}
		return ret;
	}
	/**************************************************************/
	/****************CUSTOMER OPERATIONS***************************/
	/**************************************************************/
	
	private MenuMode CustomerMenu()	{
		final int minOpt = 1, maxOpt = 5;
		int option;
		MenuMode curMode = MenuMode.CUSTOMER_MENU;
		
		System.out.print("1)Check list of items\n2)Update inforamtion\n" +
						 "3)Find Item\n4)Change Password\n5)Exit\n");
		
		option = MenuOptionChoiceRead(minOpt,maxOpt);
		
		switch(option) {
			case 1:
				System.out.println(((Customer) curUser).ShowTakenProduct());
				break;
			case 2:
				updateInformation();
				break;
			case 3:
				FindItem();
				break;
			case 4:
				PrintStatus(ChangeUserPassword(curUser));
				break;
			case 5:
				curMode = MenuMode.LOGIN_MENU;
				break;
		}
		
		return curMode;
	}
	
	private void updateInformation()	{
		String fName,lName;
	
		System.out.println("Enter UPDATED first name:");
		fName = sc.nextLine();
		System.out.println("Enter UPDATED last name:");
		lName = sc.nextLine();
		
		curUser.setFirstName(fName);
		curUser.setLastName(lName);
		
		System.out.println("Your new name: " + curUser.getFirstName() + " " + curUser.getLastName());
	}
	
	/**************************************************************/
	/****************SERVICE OPERATIONS****************************/
	/**************************************************************/
	
	private boolean AddUserToList(ClassType classType)	{
		boolean ret = false, flag = false;
		String fName,lName,id, tmpDate;
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		
		System.out.println("Enter first name");
		fName = sc.nextLine();
		System.out.println("Enter last name");
		lName = sc.nextLine();
		System.out.println("Enter ID");
		id = sc.nextLine();
		
		while(!flag)
		{
			try {
				System.out.println("Enter date in format dd/MM/yyyy");
				tmpDate = sc.nextLine();
				date = formatter.parse(tmpDate);
				flag = true;
			} catch (ParseException e) {
				ret = false;
				System.err.println("Error:Enter date in format dd/MM/yyyy");
				//e.printStackTrace();
			} 
		}
		flag = false;
		
		if(classType==ClassType.CUSTOMER) {
			ret = userList.AddToUsers(new Customer(fName, lName, id, date));
		}
		else
		{
			int option;
			String tmpDateStartWork = null;
			Date dateStartWork = null;

			while(!flag)
			{
				try {
					System.out.println("Enter start work date (yyyy-MM-dd:");
				    tmpDateStartWork = sc.nextLine();
					dateStartWork = formatter.parse(tmpDateStartWork);
					flag = true;
				} catch (ParseException e) {
					ret = false;
					System.err.println("Error:Enter date in format dd/MM/yyyy");
					//e.printStackTrace();
				} 
			}
			flag = false;
			System.out.println("Enter worker position:");
			System.out.print("1)Manager\n2)Librarian\n" + 
					 "3)Admin\n4)Exit\n");
			
			while(true)	{
				if(sc.hasNextInt())	{
					option = sc.nextInt();
					if(!CheckInput(option, 1, 4))	{
						sc.nextLine();
						System.out.println("Iligal option");
					}
					else {
						sc.nextLine();
						break;
					}
				}
			}
			
			switch(option) {
			case 1:
				ret = userList.AddToUsers(new Worker(fName, lName, id, date, WorkPosition.Manager, dateStartWork));
				break;
			case 2:
				ret = userList.AddToUsers(new Worker(fName, lName, id, date, WorkPosition.Librarian, dateStartWork));
				break;
			case 3:
				ret = userList.AddToUsers(new Worker(fName, lName, id, date, WorkPosition.Admin, dateStartWork));
				break;
			case 4:
				break;
			}
		}
		
		return ret;
	}
		
	private boolean RemoveUser(ClassType classType) {
		boolean ret = false;
		String id;
		User tmp;
		
		System.out.println("Enter ID");
		id = sc.nextLine();
		tmp = userList.FindUser(id);
		if(classType==ClassType.CUSTOMER) {
			if(tmp instanceof Customer) {
				ret = userList.RemoveUser(id);
			}
			else {
				System.out.println("Not found user with provided ID");
			}
		}
		else if (classType==ClassType.WORKER){
			if(tmp instanceof Worker) {
				ret = userList.RemoveUser(id);
			}
			else {
				System.out.println("Not found user with provided ID");
			}
		}else {
			if(tmp instanceof User) {
				ret = userList.RemoveUser(id);
			}
			else {
				System.out.println("Not found user with provided ID");
			}
		}
		
		if(ret) {
			System.out.println("Customer " + tmp.getFirstName() + " " + tmp.getLastName() + " removed.");
		}
		
		return ret;
	}
	
	private boolean FindUser(ClassType classType) {
		boolean ret = true;
		boolean flag = false;
		int option;
		String fName,lName,id;
		User tmp;
		ArrayList<User> tmpArr;
		
		System.out.print("1)Find by ID\n2)Find by full name\n" +
						 "3)Find by First/Last name\n4)Exit\n");
		option = MenuOptionChoiceRead(1,4);
		
		switch(option) {
			case 1:
				System.out.println("Enter ID");
				id = sc.nextLine();
				tmp = userList.FindUser(id);
				if(tmp != null) {
					if(classType==ClassType.CUSTOMER) {
						if(tmp instanceof Customer) {
							System.out.println(tmp.toString());
							flag = true;
						}
					}
					else if (classType==ClassType.WORKER){
						if(tmp instanceof Worker) {
							System.out.println(tmp.toString());
							flag = true;
						}
					}else {
						System.out.println(tmp.toString());
						flag = true;	
					}
				}
				else {
					System.out.println("User not found");
					ret = false;
				}
				break;
			case 2:
				System.out.println("Enter first name");
				fName = sc.nextLine();
				System.out.println("Enter last name");
				lName = sc.nextLine();
				tmpArr = userList.FindUsers(fName, lName);
				if(tmpArr != null) {
					for(User usr : tmpArr) {
						if(classType==ClassType.CUSTOMER) {
							if(usr instanceof Customer) {
								System.out.println(usr.toString());
								flag = true;
							}
						}
						else if (classType==ClassType.WORKER){
							if(usr instanceof Worker) {
								System.out.println(usr.toString());
								flag = true;
							}
						}else {
							System.out.println(usr.toString());
							flag = true;	
						}
					}
				}
				else if (!ret || !flag) {
					System.out.println("User/s not found");
					ret = false;
				}
				break;
			case 3:
				System.out.println("Enter first/last name");
				fName = sc.nextLine();
				tmpArr = userList.FindUsers(fName);
				if(tmpArr != null) {
					for(User usr : tmpArr) {
						if(classType==ClassType.CUSTOMER) {
							if(usr instanceof Customer) {
								System.out.println(usr.toString());
								flag = true;
							}
						}
						else if (classType==ClassType.WORKER){
							if(usr instanceof Worker) {
								System.out.println(usr.toString());
								flag = true;
							}
						}else {
							System.out.println(usr.toString());
							flag = true;	
						}
					}
				}
				
				else if (!ret || !flag) {
					System.out.println("User/s not found");
					ret = false;
				}
				break;
			case 4:
				System.out.println("Exit.");
				break;
		}
		return ret;
	}
	
	private boolean FindItem() {
		boolean ret = true, ok = false;
		int option;
		String name;
		int id = 0;
		LibItem tmp;
		ArrayList<LibItem> tmpArr;
		
		System.out.print("1)Find by ID\n2)Find by name\n" +
						 "3)Find by type\n4)Exit");
		option = MenuOptionChoiceRead(1,4);
		
		switch(option) {
			case 1:
				
				while(!ok) {
					try {
						System.out.println("Enter ID");
						id = sc.nextInt();
						sc.nextLine();
						ok = true;
					}
					catch (InputMismatchException e) {
					    System.err.println("Wrong input! Input only integer numbers please...");
					    sc.nextLine();
					}
				}
				ok = false;
				tmp = itemList.FindItem(id);
				if(tmp != null) {
					System.out.println(tmp.toString());
				}
				else {
					System.out.println("Item not found");
					ret = false;
				}
				break;
			case 2:
				System.out.println("Enter item name");
				name = sc.nextLine();
				tmp = itemList.FindItem(name);
				if(tmp != null) {
					System.out.println(tmp.toString());
				}
				else {
					System.out.println("Item not found");
					ret = false;
				}
				break;
			case 3:
				System.out.print("1)Book\n2)Magazine\n" +
						 "3)Scientific Work\n");
				option = MenuOptionChoiceRead(1,3);
				tmpArr = itemList.GetAllItems();
				for(LibItem item : tmpArr) {
					switch(option) {
						case 1:
							if(item instanceof Book)
							{
								System.out.println(item.toString());
							}
							break;
						case 2:
							if(item instanceof Magazine)
							{
								System.out.println(item.toString());
							}
							break;
						case 3:
							if(item instanceof ScientificWork)
							{
								System.out.println(item.toString());
							}
							break;
					}
				}
				break;
			case 4:
				System.out.println("Exit.");
				break;
		}
		return ret;
	}
	
	private boolean CheckInput(int value, int min, int max) {
		boolean ret = false;
		
		if((value >= min) && (value <= max)) {
			ret = true;
		}
		return ret;
	}
	
	private boolean ChangeUserPassword(User user)	{
		boolean ret = false;
		String tmpPass1,tmpPass2;
		System.out.print("Enter current password");
		tmpPass1 = sc.nextLine();
		if(user.checkPassword(tmpPass1)) {
			System.out.print("Enter new password");
			tmpPass1 = sc.nextLine();
			System.out.print("Enter same password");
			tmpPass2 = sc.nextLine();
			if(tmpPass1.equals(tmpPass2)) {
				ret = user.setPassword(tmpPass1);
			}
			else {
				System.out.println("New password not match each other");
			}
		}
		else {
			System.out.println("Wrong password");
		}
		
		if(ret) {
			System.out.println("Password updated");
		}
		else {
			System.out.println("Password not changed");
		}
		return ret;
	}
	
	private void PrintStatus(boolean stat)	{
		if(stat) {
			System.out.println("Operation SUCCESS");
		}
		else {
			System.err.println("Operation FAIL");
		}
	}
	
	private int MenuOptionChoiceRead(int minOpt, int maxOpt)
	{
		int option;
		while(true)	{
			if(sc.hasNextInt())	{
				try {
					option = sc.nextInt();
					if(!CheckInput(option, minOpt, maxOpt)) {
						sc.nextLine();
						System.out.println("Iligal option");
					}
					else {
						sc.nextLine();
						break;
					}
				}
				catch (InputMismatchException e) {
				    System.err.println("Wrong input! Input only integer numbers please...");
				    sc.nextLine();
				}
				
			}
		}
		
		return option;
	}
	
}
