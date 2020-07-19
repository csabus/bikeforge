# Getting Started

### Run jar file
- create a folder named 'in-out' next to bikeforge-1.0.jar
- put the input.csv in 'in-out' folder
- run the following command:
java -jar bikeforge-1.0.jar input.csv megrendelesek.csv munkarend.csv
- the ouput files (megrendelesek.csv, munkarend.csv) will be created in 'in-out' folder

### Run from docker
- copy input.csv to an empty folder
- run the following command from this folder (on Windows use PowerShell): 
docker run -v ${PWD}:/usr/src/app/in-out csabus/bikeforge:latest input.csv megrendelesek.csv munkarend.csv
- the ouput files (megrendelesek.csv, munkarend.csv) will be created next to input.csv
