# falcon_assessment
Test project for assessment from Falcon

## Tasks
* The task is to implement [testssddddddd] a data processing pipeline in the cloud.
  * Set up a running environment aligned with the technologies mentioned below
  * A Readme file containing information you deem useful for someone getting to know your code and want to try the system out
  * Develop the application in Java 8, using either DropWizard or Spring Boot as the foundation
  * A REST endpoint is taking a dummy JSON input, and the server puts the REST payload on Redis or another tool you think is well suited for the task
  * A Consumer is running in the application, taking the freshly received message and persists it in a database of your choice
  * A REST endpoint is implemented for retrieving all the messages persisted in JSON format from the database
  * The message should also be pushed through Websockets for listening browser clients at the time the message was received on the REST endpoint
  * A simple HTML page is implemented to show the real-time message delivery
  
## Scenario
* This application designed for storing json data of apache access log to mysql database
* When we generate and attach a fake apache access log using fake_apache_log_generator.py for each seconds at '/var/log/fake_access.log'
* I use parsing the apache logs to json data and send the json data to RESTful API using Logstash
* Storing and retrieving of json data interface is RESTful API
* REST API URL for storing data is 'http://localhost:8080/falcon/save'
* REST API URL for retrieving data is 'http://localhost:8080/falcon/key/seq' or 'http://localhost:8080/falcon/key/logdatetime'
* Also I made a simple page for retrieving data, URL is 'http://localhost:8080/falcon/view'
* In this page I support using STOMP protocol for retrieving whole data

## Requirements-aaaaaaa-22
* python >= 2.6
* Java 8
* Tomcat >= 7
* Maria Database
* [Fake Apache Log Generator](https://github.com/kiritbasu/Fake-Apache-Log-Generator)
* [Logstash](https://www.elastic.co/products/logstash)

## Description
* scripts
  * db : Stored ddl file for table
  * fake_apache_log_generator : Stored install files for fake apache log generator
  * logstash : Stored logstash configure file
  * sh : Stored install bash script for fake apache generator and execute for log generator

## How can we setup?
1. Install Maria Database in localhost
2. Create new database which name is '*falcon*'
3. Create new user which can access falcon database
4. Change the information about user for accessing database in application-production.xml
5. Generate table for storing data in falcon database using '*./scripts/db/ddl.sql*'
6. Execute install script for log generator
   1. cd ./scripts/sh
   2. sh install_fake_apache_log_gen.sh
7. Install logstash

## How can we run!!!
1. Build application
   * sh deploy.sh
2. Execute Spring application
3. Execute fake log generator
   1. cd ./scripts/sh
   2. sudo sh ./run_log_generator.sh
4. Execute Logstash
   * logstash -f ${LOGSTASH_CONF_FILE_PATH} 
