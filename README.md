# image-search-by-filter

WHAT IT IS:
My main aim was to create an image search by filter application (for which we will be providing a 2 page report).
Two kinds of the same application have been provided. One is for the administrators(Admin.java) while one is for clients(Client.java).

Both applications perform normal image search where a user can choose whether they want to download the images resulting from their search, and supports the following queries:
1.	Search by Photographer
2.	Search by Type
3.	Search by Model
4.	Search by Description
In addition to the queries mentioned above, the Admin application supports other queries such as:
1.	ALTER TABLE
2.	DROP TABLE
3.	UPLOAD NEW PHOTO
4.	SHOW TABLES
5.	CREATE TABLE/ENTER CUSTOM QUERY
_____________________________________________________________________________________

HOW TO MAKE IT WORK:

          - - - - - - - - - Database credentials need to be hardcoded into the application. - - - - - - - - -
Steps to Use Client Application:
1.	Compile java file Client.java usng ‘javac Client.java’ ( without the ‘ ’ )
2.	Use the following command to start the app:
java -classpath .:/usr/share/java/mysql-connector-java-5.1.28.jar Client
3.	UI is self-explanatory, proceed as required.
Steps to Use Admin Application:
1.	Compile java file Admin.java usng ‘javac Admin.java’ ( without the ‘ ’ )
2.	Use the following command to start the app:
java -classpath .:/usr/share/java/mysql-connector-java-5.1.28.jar Admin
3.	UI is self-explanatory, proceed as required.
