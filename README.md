# Spring boot Bank Application
    Application with the purpose of managing a web bank platform.  
# Tech Stack
    - Spring  
    - Spring Data JPA  
    - Spring Security  
    - MySQL  
    - Spring Mail  
# Required Setup
    To start this application a mysql database on localhost:3306 with the following tables is needed:  
        - user  
        - authority
        - user_authority  
    Database connection and mail configuration must be established in application.properties file  
# Endpoints
    - /api/v1/users/add  // no auth required  
        adds an user to the database, body is needed  

    - /api/v1/users/all  // admin role required  
        fetches all the existing users  

    - /api/v1/users/deposit  // user role required  
        deposits money to the user, param needed  

    - /api/v1/users/withdraw  // user role required  
        withdraws money from the user, param needed  

    - /api/v1/users/transfer  // user role required  
        transfers money from user to user, two params needed  