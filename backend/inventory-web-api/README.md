#inventory-web-api
A simple web-based inventory service that exposes a REST API.  Allows any anonymous user to view the inventory and view inventory items, and authenticated users can buy items from the inventory.
 
 

## Table of Contents:
#### 1. How to build
#### 2. How to test
#### 3. How to run
#### 4. How to use
#### 5. How to setup development in Eclipse
#### 6. How to setup development in IDEA IntelliJ
#### Appendix A. Design Decisions

----------------------------------------------------------------------
#### 1. How to build:
1. Build using the Gradle Wrapper
   - cmdline> ./gradlew build
2. Build Without using the Gradle Wrapper
   - Download and install Gradle 2.3+ manually
   - cmdline> gradle build

#### 2. How to test:
1. Using the Gradle Wrapper
   - cmdline> ./gradlew test
2. Without using the Gradle Wrapper
   - cmdline> gradle test
 
#### 3. How to run:
1. Using the Gradle Wrapper
   - cmdline> ./gradlew bootRun
2. Without using the Gradle Wrapper
   - cmdline> gradle bootRun

	
#### 4. How to use
1. REST API
      
|Operation | HTTP Method | Relative URL Path  | Requires Authentication | Requires Admin Role |
|:---------|:-------------:|:------------------|:------------------------:|:---------------------------:|
Get hypermedia links to top-level resources provided by this service |GET| /api | No | No |
Check if service is available |GET| /health | No | No |
Get the inventory listing |GET| /api/inventory-item | No | No |
Add an item to the inventory |POST| /api/inventory-item | No | No |
Get a specific inventory item |GET| /api/inventory-item/{id} | No | No |
Create an authentication token (login)|POST| /auth/token | No | No |
Buy an item from the inventory |POS | /api/inventory-item/{refId}/purchase | Yes | No |
Update the inventory item with refId={refId} |PUT| /api/inventory-item/{refId} | Yes | Yes |

		
#### 5. How to setup development in Eclipse
1. Install "Buildship Gradle Integration for Eclipse" from Eclipse Marketplace
2. open project in Eclipse as "Existing Gradle Project"
 

#### 6. How to setup development in IDEA IntelliJ
1. cmdline> gradle idea
2. open project in IntelliJ
		
 

#### Appendix A. Design Decisions:
1. Language: Java 8 - a good multi-purpose modern language
2. Frameworks:
   1. Spring Boot - provides a good application framework for cross-cutting application concerns
   2. Spring Security - provides security framework
   3. Spring MVC - provides web API framework

3. Development Environment Tools
   1. Build tool: Gradle
   2. Eclipse (Neon or later)
   3. Buildship - Gradle Integration for Eclipse
