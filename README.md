# Trafilea-coffee-shop-api
Trafilea tech challenge
tecnologi
Language: Java 17 - springboot
Database: H2

# Assumtions
  - A user can only have one active cart.
  - If the order is generated the cart is closed.
  - The discount is general for spend more than 70 dollars on category Accessories.
  - Always send the userID.
  - The shipping is 10 Dolars.
  - Items can be created when the cart is created

# How to run
  - Docker build -t coffee-shop .
  - Docker run -p 9091:9091 -d coffee-shop


Note: If you want to check the database info you can access to http://localhost:9091/h2-console
Note: In src/main/resources I add a postman file to test
