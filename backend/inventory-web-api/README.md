A simple web-based inventory service that exposes a REST API.  Allows any anonymous user to view the inventory and view inventory items, and authenticated users can buy items from the inventory.
 
 README Author: Stephen Brooke
 Date: Mar 09, 2017
 

 #Table of Contents:
   1. How to build
   2. How to test
   3. How to run
   4. How to use
   5. How to setup development in Eclipse
   6. How to setup development in IDEA IntelliJ
   
   Appendix A. Design Decisions


 #1. How to build:
		1.1. Using the Gradle Wrapper
			1.1.1. cmdline> ./gradlew build
		1.2. Without using the Gradle Wrapper
			1.2.1. Download and install Gradle 2.3+ manually
			1.2.1. cmdline> gradle build

 ==============================================================================

 2. How to test:
		2.1. Using the Gradle Wrapper
			2.1.1. cmdline> ./gradlew test
		2.2. Without using the Gradle Wrapper
			2.2.1. cmdline> gradle test

 ==============================================================================

 3. How to run:
		3.1. Using the Gradle Wrapper
			3.1.1. cmdline> ./gradlew bootRun
		3.2. Without using the Gradle Wrapper
			3.2.1. cmdline> gradle bootRun

==============================================================================
		
 4. How to use
		4.1. REST API
			4.1.1. Check if service is available:		GET /health
			4.1.2. Authenticate:						GET /auth/token
			4.1.3. Get the inventory listing:			GET /api/inventory
			4.1.4. get the inventory item with id={id}:	GET /api/inventory/{id}
			4.1.5. Buy an item from the inventory:		GET /api/inventory/{id}/purchase	(authorized uses only)

==============================================================================
		
 5. How to setup development in Eclipse
		5.1. Install "Buildship Gradle Integration for Eclipse" from Eclipse Marketplace
		5.2. open project in Eclipse as "Existing Gradle Project"
 
==============================================================================

 6. How to setup development in IDEA IntelliJ
   		6.1. cmdline> gradle idea
		6.2. open project in IntelliJ
		
 ==============================================================================

 Appendix A. Design Decisions:
	A.1. Language: Java 8 - a good multi-purpose modern language
	A.2. Frameworks:
		A.2.1 Spring Boot - provides a good application framework for cross-cutting application concerns
		A.2.2 Spring Security - provides security framework
		A.2.3 Spring MVC - provides web API framework
	A.3. Development Environment Tools
		A.3.1 Build tool: Gradle
		A.3.2 Eclipse (Neon or later)
		A.3.3 Buildship - Gradle Integration for Eclipse
