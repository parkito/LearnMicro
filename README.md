# Repository for microservices lecture in T-Systems java school

Lecture and practice exercises give overview of current state of enterprise distributed computing a.k.a microservices

## System architecture

<h3 align="center">
  <br>
   <img src="https://s8.hostingkartinok.com/uploads/images/2018/06/deb55bd609a3208d9a69fc0fc4ad9447.jpg" title="system architecture" />
  <br>
</h3>

## CONFIG_SERVER

**Stack** 
*Java 8, Spring Boot 2.0, Spring cloud config server*

**Description** Dockerized config server for accessing configs for microservices
Configs are stored in https://github.com/parkito/LearnMicro repository.

**Address** http://localhost:9090/

**Communications** - Rest


## SERVICE_DISCOVERY

**Stack** 
*Java 8, Spring Boot 2.0, Spring cloud Netflix eureka*

**Description** Dockerized discovery service based on Netflix eureka. It registers microservices and gets their status
Configs are stored in https://github.com/parkito/LearnMicro repository.

**Address** http://localhost:9091/

**Communications** - Rest


## USER_SERVICE

**Stack** 
*Kotlin 1.2, Spring Boot 2.0, Spring data, H2 in memory data base, Netflix eureka client, Spring cloud config client
Lombok, Liquibase, Swagger, Spring retry*  

**Description** Dockerized client application.
 1) Fetches configs form config server
 2) Contains in memory database with predefine data (migrated by Liquibase)
 3) Rest API documented by Swagger (**http://localhost:2144/swagger-ui.html**)
 4) Contains internal RDBS **http://localhost:2144/h2**, **username:** *root*, **password:** *toor* (see configs)
 5) Internal data base created working files in home directory 
 6) Repository based on Spring data
 7) Domain model - represent service for user management

**Address** http://localhost:2144/

**Communications** - Rest

## DOCUMENT_SERVICE

**Stack** 
*Java 8, Spring Boot 2.0, Spring data, H2 in memory data base, Netflix eureka client, Spring cloud config client
Lombok, Liquibase, Swagger, Spring retry*  

**Description** Dockerized client application.
 1) Fetches configs form config server
 2) Contains in memory database with predefine data (migrated by Liquibase)
 3) Rest API documented by Swagger (**http://localhost:2143/swagger-ui.html**)
 4) Contains internal RDBS **http://localhost:2143/h2**, **username:** *root*, **password:** *toor* (see configs)
 5) Internal data base created working files in home directory 
 6) Repository based on Spring data
 7) Domain model - represent service for user's document maintaining

**Address** http://localhost:2143/

**Communications** - Rest

## POST_SERVICE

**Stack** 
*Java 8, Spring Boot 2.0, Spring data, H2 in memory data base, Netflix eureka client, Spring cloud config client
Lombok, Liquibase, Swagger, Spring retry*  

**Description** Dockerized client application.
 1) Fetches configs form config server
 2) Contains in memory database with predefine data (migrated by Liquibase)
 3) Rest API documented by Swagger (**http://localhost:2142/swagger-ui.html**)
 4) Contains internal RDBS **http://localhost:2142/h2**, **username:** *root*, **password:** *toor* (see configs)
 5) Internal data base created working files in home directory 
 6) Repository based on Spring data
 7) Domain model - represent post service for parcels managment

**Address** http://localhost:2142/

**Communications** - Rest

## Docker compose

All application pushed on Docker hub. Docker-compose script pulls images and runs them. After script executing system is ready to be used. 


## Contributing 

Your questions and suggestions will be welcomed by author via artem.karnov@t-systems.ru 

## License

[MIT](https://github.com/parkito/LearnMicro/blob/master/LICENSE)
