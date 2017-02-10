# DesignTeam2

Configure the external jars for the project: right click project -> properties -> java build path -> remove all associated external jars outside of JRE system library and JUnit 4 -> add external jars -> navigate to the Jars folder -> add all jars in the folder

Start MAMP on your computer. If your MAMP port is not 8889 (the default port): preferences -> ports -> set ports to default. We also assume that your default password/username = root. If not, you need to edit these values in DJDatabaseTest (lines 32-34) and NewsStoriesDatabaseTest (lines 31-33) and MySQLInitializer (lines 12-14).

You can now run the Test folder as a JUnit test. 

