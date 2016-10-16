=================================
||Read Me
||
||SS Leos Management System
||
||Carleton Engineering Competition 2016 - Programming
||October 15, 2016
||
||Team: SQUIRTLE SQUAD
||Team Members:
||Charles Bergeron
||Christopher Briglio
||David Briglio
||Daniel Sauvé
=================================

If you are looking for the user manual explaining how to install and operate the program, please refer to "User Manual.pdf".

--Program Description--

This program is an intuitive, user friendly management system made specifically to meet the needs of Leonardo's Lounge at Carleton University.
It has the ability to manage and track all inventory and volunteers of leo's in one simple interface. It includes features such as stock tracking,
product popularity tracking, automatic volunteer assignment, and an intuitive way to manually modify listings.

--Project Setup--

The project is available here: https://github.com/charberg/CEC2016
This project can be imported into and worked on in any Java IDE. The IDE used during its creation was primarily Eclipse.
We strongly recommend using the Eclipse IDE as it was what the program was first developed in. This project also relies on the external libraries
SQLite, Apache POI and JavaMail. In order to develop using SQLite libraries, you will need to install it from https://sqlite.org/download.html (we are using 
v 3.14.2.1). The JAR files are already included within the project, the developer only needs to ensure the JAR files in the lib directory 
is included in the class path in order to and run the program.

--External Libraries--
SQLite
Website: https://sqlite.org/
We are using SQLite to create and manage the database the program uses to keep track of all volunteer and product information.

Apache POI
Website: https://poi.apache.org/
We are using Apache POI in order to parse and import Microsoft suite of file types into a readable format for Java.

JavaMail API
Website: http://www.oracle.com/technetwork/java/javamail/index.html
We are using JavaMail to send email notification.