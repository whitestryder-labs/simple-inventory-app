# simple-inventory-app
A simple web-based inventory application that exposes a REST API for customers to integrate into their own applications and a web-based UI for users to interact with for casual use.  Allows any anonymous user to view the inventory and view inventory items, and authenticated users can buy items from the inventory.

## Backend
### [inventory-web-api](./backend/inventory-web-api/README.md)

## Frontend
### [inventory-web-ui](./backend/inventory-web-ui/README.md)


#### A couple questions to consider:
- Q1: How do we know if a user is authenticated?
  - A1: In the backend *inventory-web-api* service we use a stateless security model.  For a user to be authenticated they must first request a JWT (JSON Web Token) authentication token by providing a valid 'username' and 'password' in a form-data POST request to the service.  To access protected resources a client must provide the authentication token in an 'X-AUTH-TOKEN' HTTP header. The service looks for a valid token which is cryptographically signed with the 'username' and an expiry date which is configurable at a service-wide level, therefore the token cannot be modified without detection.  If a token is decrypted and found to be valid (i.e. not expired and contain an existing user) then SecuritySessionHolder is entrusted with an authentication object but only for the life of the request.  There is no session retained in the service after the request returns.  The service relies on security rules configured in the Security framework

- Q2: Is it always possible to buy an item?
  - A2: No, if the item's 'quantityInStock' is zero then the item has gone "out of stock" and buying an item is not possible for any user authenticated or not.
  
  
#### Deliverables
1. The backend service is build with Java 8 and the Spring Framework, more specifically Spring Boot.  Instructions for running can be found at [inventory-web-api README](./backend/inventory-web-api/README.md) 

2. The backend service supports the following API requests via HTTP including the two API requirements
   - Retrieve the current inventory (i.e. list of items)
   - Buy an item (user must be authenticated)
   - Retrieve a single inventory item
   - Login (i.e. create an authentication token)
   - Add an inventory item (user must be authenticated with admin role)
   - Update an inventory item (user must be authenticated with admin role)
     - supports update of 'description', 'price', and 'quantityInStock' attributes

3. 
