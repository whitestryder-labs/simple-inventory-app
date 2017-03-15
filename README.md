# simple-inventory-app
A simple web-based inventory application that exposes a REST API for customers to integrate into their own applications and a web-based UI for users to interact with for casual use.  Allows any anonymous user to view the inventory and view inventory items, and authenticated users can buy items from the inventory.

## Backend
### [inventory-web-api](./backend/inventory-web-api/README.md)

## Frontend (TBD)
### [inventory-web-ui](./frontend/inventory-web-ui/README.md)


### A couple questions to consider:
- Q1: How do we know if a user is authenticated?
  - A1: In the backend *inventory-web-api* service we use a stateless security model.  For a user to be authenticated they must first request a JWT (JSON Web Token) authentication token by providing a valid 'username' and 'password' in a form-data POST request to the service.  To access protected resources a client must provide the authentication token in an 'X-AUTH-TOKEN' HTTP header. The service looks for a valid token which is cryptographically signed with the 'username' and an expiry date which is configurable at a service-wide level, therefore the token cannot be modified without detection.  If a token is decrypted and found to be valid (i.e. not expired and contains an existing user) then SecuritySessionHolder is entrusted with an authentication object but only for the life of the request.  There is no session retained in the service after the request returns.  The service relies on security rules configured in the Spring Security framework in order to unforce which API resources require authentication.  So we know a user is authenticated if they have a valid JWT authentication token as determined by the service.

- Q2: Is it always possible to buy an item?
  - A2: No, if the item's 'quantityInStock' is zero then the item has gone "out of stock" and buying an item is not possible for any user authenticated or not.
  
  
### Deliverables
#### 1. *Runnable System*: The backend service is built with Java 8 and the Spring Framework, more specifically Spring Boot, Spring Data JPA, Spring MVC, Spring HATEOAS, and Spring Security.  Instructions for running can be found at [inventory-web-api README](./backend/inventory-web-api/README.md) 


#### 2. *API Requests*: The backend service supports the following API requests via HTTP including the two API requirements
   - Retrieve the current inventory (i.e. list of items)
   - Buy an item (user must be authenticated)
   - Retrieve a single inventory item
   - Login (i.e. create an authentication token)
   - Add an inventory item (user must be authenticated with admin role)
   - Update an inventory item (user must be authenticated with admin role)
     - supports update of 'description', 'price', and 'quantityInStock' attributes


#### 3. *Unit and Integration Tests*
   - Unit tests are provided for:
     - Domain Entities: IventoryItem, InventoryItemAccess
   - Integration tests are provided for:
     - API operations are tested using RestTemplate
     - Surge Pricing Model
     - the Spring Data JPA queries and repositories
   - Test technologies used: JUnit, Spring Test, Spring Dev Tools, and RestTemplate
     
     
#### 4. *Explanations*:
##### a. *Application*:
 - How it is set up:
  - Began from various Spring Boot Gradle-based sample applications and setup a skeleton application with working Gradle build configuration
  - Imported Gradle project into Eclipse (Neon) and further broken down the application into different software layers
 - How it was built:
  - How the Surge Pricing Model was designed:
  - The type of architecture chosen:
   - Roughly follows ["The Clean Architecture"](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) AKA Onion Architecture
   - for simplicity the layers are manifested in different packages initially, not separate Java libraries
   - Sofware Layers in order of dependency:
             
|Software Layer|Industry Name|Responsibility|
|:-------------|:-------------|:-------------|
|*config*|Frameworks and Drivers|The outermost layer generally composed of frameworks and tools such as the Database, the Web Framework, configuration|
|*api*|Iterface Adapters|The software in this layer is a set of adapters that convert data from the format most convenient for the use cases and entities, to the format most convenient for some external agency such as the Database or the Web|
|*app*|Use Cases|Contains application specific business rules and encapsulates and implements all of the use cases of the system|
|*core*|Entities|Encapsulate Enterprise-wide/core business rules|


##### b. *Data Format*: JSON
- Example Request/Response:
 - Request:
  - HTTP Method: GET
  - Relative URL Path: /api/inventory-item
 - Response:

```javascript
{
"items": [
{
    "externalReferenceId": "a84ad207-330d-453d-9172-1a2dcfda6e5b",
    "name": "Soccer ball 1",
    "description": "This is a very good match ball",
    "price": 160,
    "quantityInStock": 0,
    "_links": {
        "self": {
            "href": "http://localhost:8181/api/inventory-item/a84ad207-330d-453d-9172-1a2dcfda6e5b"
        },
        "related x-buy": {
            "href": "http://localhost:8181/api/inventory-item/a84ad207-330d-453d-9172-1a2dcfda6e5b/purchase"
        }
    }
},
{
    "externalReferenceId": "614b6eac-a505-40de-9e19-b1afad13bb2d",
    "name": "Soccer ball 2",
    "description": "This is a very good match ball",
    "price": 160,
    "quantityInStock": 1,
    "_links": {
        "self": {
            "href": "http://localhost:8181/api/inventory-item/614b6eac-a505-40de-9e19-b1afad13bb2d"
        },
        "related x-buy": {
            "href": "http://localhost:8181/api/inventory-item/614b6eac-a505-40de-9e19-b1afad13bb2d/purchase"
        }
    }
}
],
"_links": {
"self": {
"href": "http://localhost:8181/api/inventory-item"
}
}
```

##### c. *Authentication Mechanism*: Stateless JWT (JSON Web Token) Authentication
- Stateless token-based authentication worked as follows:
 - User Requests Access with Username / Password
 - Application validates credentials
  - Application provides a signed token to the client
  - Client stores that token and sends it along with every request
  - Server verifies token and responds with data
   - Why was this authentication mechanism chosen?
    1. Scalability: This mechanism uses much less memory on the server because no user session is kept between requests, therefore an increase in the number of users logged in does not lead to a linear increase in server memory per user and hence this type of authentication is more scalable than stateful session-based approaches 
    2. Simplicity: you don't need to worry about where the user is 'logged-in'
    3. Reusability: It is transportable: you can pass it along to a 3rd party application, for example, a mobile application
    4. Security:
       - since there is no cookie being sent containing a session ID, this helps to prevent CSRF attacks
       - tokens expire after a certain configurable amount of time as determined by the server
       - specific tokens or groups of tokens with the same Authorization grant can be revoked
    5. Standards-based: See ([JSON Web Token draft standard](http://self-issued.info/docs/draft-ietf-oauth-json-web-token.html))
    6. I wanted to learn something new
  - Reference: [The Ins and Outs of Token Based Authentication](https://scotch.io/tutorials/the-anatomy-of-a-json-web-token)
