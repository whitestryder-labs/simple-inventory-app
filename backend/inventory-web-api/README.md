#inventory-web-api
A simple web-based inventory service that exposes a REST API.  Allows any anonymous user to view the inventory and view inventory items, and authenticated users can buy items from the inventory.
 
 

## Table of Contents:
### 1. How to build
### 2. How to test
### 3. How to run
### 4. How to use
### 5. How to setup development in Eclipse
### 6. How to setup development in IDEA IntelliJ
### Appendix A. Design Decisions


###1. How to build:
      1. Build using the Gradle Wrapper
         - cmdline> ./gradlew build
      2. Build Without using the Gradle Wrapper
         - Download and install Gradle 2.3+ manually
         - cmdline> gradle build

###2. How to test:
      1. Using the Gradle Wrapper
         - cmdline> ./gradlew test
      2. Without using the Gradle Wrapper
         - cmdline> gradle test

 
###3. How to run:
      1. Using the Gradle Wrapper
         - cmdline> ./gradlew bootRun
      2. Without using the Gradle Wrapper
         1. cmdline> gradle bootRun

	
###4. How to use
      1. REST API
      
| Operation | HTTP Method | Relative URL Path  | Requires Authentication |
|:---------:|:-------------:| ------------------:|------------------------:|
| Check if service is available         | GET| /health | No |
| Create an authentication token (login)| POST | /auth/token                          | Yes                     |
| Get the inventory listing             | GET | /api/inventory-item                  | No                      |
| Add an item to the inventory          | POST | /api/inventory-item                  | No                      |
| Get the inventory item with id={id}   | GET | /api/inventory-item/{id}             | No                      |
| Buy an item from the inventory        | POST | /api/inventory-item/{id}/purchase    | Yes                     |

		
### 5. How to setup development in Eclipse
		5.1. Install "Buildship Gradle Integration for Eclipse" from Eclipse Marketplace
		5.2. open project in Eclipse as "Existing Gradle Project"
 

###6. How to setup development in IDEA IntelliJ
   		6.1. cmdline> gradle idea
		6.2. open project in IntelliJ
		
 

###Appendix A. Design Decisions:
	A.1. Language: Java 8 - a good multi-purpose modern language
	A.2. Frameworks:
		A.2.1 Spring Boot - provides a good application framework for cross-cutting application concerns
		A.2.2 Spring Security - provides security framework
		A.2.3 Spring MVC - provides web API framework
	A.3. Development Environment Tools
		A.3.1 Build tool: Gradle
		A.3.2 Eclipse (Neon or later)
		A.3.3 Buildship - Gradle Integration for Eclipse
