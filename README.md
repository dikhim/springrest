# springrest

Rest service on Spring Boot 2.1

To build the application run the command 
```
mvn package
```

Before launch you should configure your datasource. Near the jar file there are three files:
* config.properties - contains properties for application configuration
* schema.sql - to create schema
* data.sql - to load initial data


To initialize the application for the first run run the following commands
```
sudo -u postgres psql -c 'create database mydb;'
sudo -u postgres psql -c 'grant all privileges on database test to myuser;'
sudo -u postgres psql -c '\i target/schema.sql'
sudo -u postgres psql -c '\i target/data.sql'   
```

Change the config file for your datasource (url, username, password)
```
server.servlet.context-path=/springrest
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=myuser
spring.datasource.password=mypass
```
Run the application
```
java -jar target/spring-rest-service-1.0-SNAPSHOT.jar  
```

To run application in test mode pass the 'test' profile parameter
```
java -jar target/spring-rest-service-1.0-SNAPSHOT.jar --spring.profiles.active=test
```
It'll use embeded database

