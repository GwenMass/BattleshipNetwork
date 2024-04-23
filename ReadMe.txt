READ THIS FIRST
In order to run this program, you must have a MySQL database setup with a database called 'student_space' and a 'student' user with full permissions for this workspace.
You must then use the MySql Source command while using the database on the provided Battleship.SQL file in order to setup the database used for creating user accounts and logging in.
Ensure that the bat files are in the top-most directory, the same directory as the Client & Server folders.
In this order:
1. Launch the MySQL server with the database sourced from the attached SQL file.
2. Run battleshipServer.bat, ensure you are using the correct port, and then click the button labeled 'listen' in order to start the server.
3. Run battleshipClient.bat in order to launch the client (Note, if you are using multiple machines, you must edit GameGUI.java's 14th line to match the IPv4 of the hosting machine. For example "client.setHost("192.168.x.x");
4. Run battleshipClient.bat again to launch a second client, or do so on whatever machine you would like the client to run off of. Again, if it is a different machine than the server is running on, you must specify the IPv4 on GameGUI.java before running the bat.
