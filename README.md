# Spring boot Bank Application
Application with the purpose of managing a web bank platform.  
# Tech Stack
- Spring  
- Spring Data JPA  
- Spring Security  
- MySQL  
- Spring Mail  
# Endpoints
- public endpoints  
    - /api/v1/users/register  
        - request body required  
- authenticated user endpoints  
    - /api/v1/users/deposit  
        - param needed  
    - /api/v1/users/withdraw  
        - param needed  
    - /api/v1/users/transfer  
        - two params needed  
- authenticated user with role admin endpoints  
    - /api/v1/users/all  
# Required Setup
To start this application you will need a database with the following tables:
- user  
- authority  
- user_authority  
Database connection and mail configuration must be established in application.properties file  
Run mvn clean package command and start the jar file  

