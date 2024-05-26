# JAVA_FinalProj_SmartLib

## How to open cloned project:
* Open Eclipce 2023.09
* Choose WS, **WS should not be in the same folder as project**
* In Eclipce: File->Import->General->Exsiting Project into Workspace
* In Eclipce: In root directory choose cloned directory. You should choose all directory and not only project dir.
* In Eclipce: Click Finish
* For run:
  * Right click on *_SmartLib/src_* folder and click Run as Java application. If failed right click on root project and clikc "Maven/Install Maven" or "Maven/Update MavenProject" 


# SmartLib: Java Library Management System

## Description
SmartLib is a Java program designed for managing a library system. It provides functionalities for users, including admins, workers, and customers, to interact with the library's resources. The system allows users to perform various operations such as adding/removing users and items, searching for items, checking out and returning items, and managing user accounts.

## Structure Overview

### Header Comments
The file starts with a block comment providing essential information about the class, including its purpose, authors, and any other relevant details.

### Package Declaration
The code is organized within the `SmartLib` package, indicating it's part of a larger library management system.

### Imports
The code imports necessary Java classes like Scanner, ArrayList, and Date for handling user input, collections, and date manipulation, respectively.

### Class Declaration
The `Menu` class is declared, which serves as the main operational class for the library system.

### Enums
Two enums are defined: `MenuMode` and `ClassType`, providing a convenient way to represent different menu modes and user types within the system.

### Global Variables
Global variables such as `curUser`, `sc`, `userList`, `itemList`, and `curMode` are declared to manage the current user, input scanner, user and item lists, and the current menu mode, respectively.

### Constructor
The class constructor initializes various variables and reads user and item data from files using `DbFileOper`.

### Menu Methods
The `MainMenu` method is the main entry point, presenting different menus based on the current mode. It contains a loop to continuously display the menu until the user chooses to exit.

### Login Operations
Methods like `LoginMenu` handle user authentication by prompting for ID and password and validating them against stored user data.

### Admin, Worker, and Customer Operations
Separate methods handle operations specific to admins, workers, and customers, including adding users/items, removing users/items, finding users/items, changing passwords, etc.

### Input Handling
Methods like `MenuOptionChoiceRead` handle user input by ensuring it's within specified ranges and catching input mismatch exceptions.

### Service Operations
Methods like `AddUserToList`, `RemoveUser`, `FindUser`, and `FindItem` provide various services related to users and items.

### Error Handling and Status Printing
The code includes error handling mechanisms to deal with invalid input and failed operations. It also prints status messages indicating whether operations succeed or fail.

### Other Operations
There are methods for updating user information, giving and returning items, and checking the list of items taken by customers.

## Conclusion
The code concludes with a well-structured and organized presentation, separating different functionalities into methods and providing comments for clarity and understanding.

## Usage
To run the SmartLib system, compile the Java files and execute the `Menu` class.

```bash
javac Menu.java
java Menu

