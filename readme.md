# Usage

Due to spring jpa configurations will try to connect to a mysql running in 3306 if the tests fail to connect to the db
try running the schema.sql in src\test\resources once using a command line


Required env variable:
- KEY=jwt secret
- RASPBERRY_URL=url of raspberry api
- RASPBERRY_CLOSE_ROOM= close room end point of raspberry
- TEAM_URL = the url of the MS TEAM webpage that the user can redirect ot
- AD_MICROSERV= http://localhost:8000