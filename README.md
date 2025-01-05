## Hospital Inventory Management

This project was built to complete the requirements of "CSYE6200 - Object Oriented Design" course which I took at Northeastern University as part of my Master's degree. The project uses Java for backend & JavaFX for the front end. MySQL database is used for storing the data.

<kbd>![hospInvMgmt](https://github.com/user-attachments/assets/da25bc97-55e5-45ec-9a2e-fdadcf610303)</kbd>

## Setup
#### Database:

1. Download & install XAMPP from https://www.apachefriends.org.
2. Once XAMPP is successfully installed, open the XAMPP application. 
3. Now, on the "Manage servers" tab, start all the servers using "Start All" button.
4. Once all the servers are running, go to http://localhost/phpmyadmin in a browser.
5. Click the import tab.
6. Select the file "localhost.sql" which is present in the "res" sub-folder of the project folder.
7. Once the file is selected, scroll down & click the "Import" button.
8. The database called "stock" will be created. This database will contain "item" table along with the necessary columns & rows.

#### Project Setup using Eclipse IDE:

1. Right click the project folder from the project explorer view.
2. Select Build Path > Configure Build Path option from the menu.
3. Under the Java Build Path option, go to "Libraries" tab.
4. On clicking the "Modulepath" option, select the "Add External JARs" button present on the right side in the dialog box.
5. Navigate to the "lib" sub-folder & select "mysql-connector-j-8.3.0.jar" & "Credentials.jar" file.
6. Click "Open" button.
7. Now, again clicking the "Modulepath" option, select the "Add Library" button present on the right side in the dialog box.
8. Select USer Library > Java FX > Next > Apply & Close.

#### Running the project:

1. Right click the project folder from the project explorer view.
2. Select Run As > Run Configurations....
3. Create a new configuration by clicking the "New launch configuration" icon present in the left side menu on the top.
4. Now on the right side of the new configuration the got created, give a name to the configuration.
5. On the "Main" tab, the selected Project should be selected as hacker-huskies. If the correct project is not selected, use the "Browse..." 
   button to select the correct project.
6. For the "Main class", use the "Search..." button & select "Main - application" and click "OK".
7. Now, on the "Arguments" tab, un-select the selected check boxes & add "--add-modules javafx.controls,javafx.fxml" to "VM Arguments" text area.
8. Click "Apply" & then "Run".
9. The project should now be running and the login screen should be visible. Enter the username & password as set in the "Credentials.jar" file & click "Login".
10. The home page will be visible on successful login.
