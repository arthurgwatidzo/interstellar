

Author Details:			
=====================================================================
Developer:					Arthur Tapiwa Gwatidzo
Email:						Arthur.Gwatidzo@gmail.com
Cell:						076-898-3930

Artifact Generated: 		WAR file


Tools Used:
-------------------------------------------------------------
JDK:							Java 8
Database:						DERBY
Framework:						SpringBoot
ORM Tool:						Hibernate 5
Unit Testing Framework:			JUnit, Mockito, Hamcrest
Front End Framework:			Thymeleaf
Dependency Management Tool:		Maven
Source Code Control:			Git

IDE Used:						Eclipse Java EE IDE for Web Developers.
Eclipse							Version: Oxygen.3a Release (4.7.3a)
Eclipse							Build id: 20180405-1200
Web Server:						Embedded Tomcat Web Server through SpringBoot



From Eclipse IDE, when this app fails please click "Project" and then click "Clean", therafter click "Refresh", then execute:
mvn clean install, OR
mvn clean compile
...depending on what needs to be achieved using the maven lifecycle


Some Enterprise Architecture Design Patterns & Coding Principles Used in this Project
=====================================================================================
1. DTO (Data Transfer Object)
2. DAO (Data Access Object)
3. Services
4. Data Mappers
5. Structural Design Pattern which use interface



Outstanding Issues
1. Fix broken unit tests, the ones marked with ignore
2. Unit Test more classes, that is, services, DTOs etc
3. Panel beat and fine-tune thymeleaf templates
4. Add logging to classes
5. log exceptions using log4j
6. Use custom exceptions where necessary, e.g. the InterstellarApplicationException
7. Add Lombok jar dependency to reduce boilerplate code like on getters/setters
8. Add more negative unit testing scenarios in Unit Tests
9. Unit Test areas where exceptions are being thrown

