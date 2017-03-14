# simple-inventory-app
A simple web-based inventory application that exposes a REST API for customers to integrate into their own applications and a web-based UI for users to interact with for casual use.  Allows any anonymous user to view the inventory and view inventory items, and authenticated users can buy items from the inventory.

## Backend
### [inventory-web-api](./backend/inventory-web-api/README.md)

## Frontend
### [inventory-web-ui](./backend/inventory-web-ui/README.md)


#### A couple questions to consider:
- Q1: How do we know if a user is authenticated?
  - A1: In the backend *inventory-web-api* service we use a stateless security model.  For a user to be authenticated they must first request a JWT (JSON Web Token) authentication token by providing a valid 'username' and 'password' in a form-data POST request.  To access protected resources a client must provide the authentication token in an 'X-AUTH-TOKEN' HTTP header. The *inventory-web-api* service looks for a valid token which is cryptographically signed with the 'username' and an expiry date which is configurable at a service-wide level.

- Q1: Is it always possible to buy an item?
  - A1: No, if the item's 'quantityInStock' is zero then the item has gone "out of stock" and buying an item is not possible for any user authenticated or not.
