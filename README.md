# DesignTeam2

You must add the src folders to the build path: right click project -> properties -> java build path -> src -> add folder -> select src and Test -> yes -> okay. Configure the external jars for the project: right click project -> properties -> java build path -> remove all associated external jars outside of JRE system library and JUnit 4 -> add external jars -> navigate to the Jars folder -> add all jars in the folder. You will also have to add JRE system and JUnit4 libraries to the project.

Start MAMP on your computer. If your MAMP port is not 8889 (the default port): preferences -> ports -> set ports to default. We also assume that your default password/username = root. If not, you need to edit these values in DJDatabaseTest (lines 32-34) and NewsStoriesDatabaseTest (lines 31-33) and MySQLInitializer (lines 12-14). If the database "Omnipredictor" already exists on your MySQL server, you must delete it and then run the program.

You can now run the Test folder as a JUnit test. 

To begin the program, run the main method Controller.java in the controller package in src. When the program starts, both the start and end date must be between 2008-08-08 and 2016-07-01 and the dates must be three or more days apart and in the correct format: yyyy-mm-dd.

