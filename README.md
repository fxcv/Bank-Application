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
        - debit_cards
        - user_authority  
    Database connection and mail configuration must be established in application.properties file  
# Endpoints
    - /api/v1/users/add  
        adds an user to the database, body is needed  
    - /api/v1/users/all  
        fetches all the existing users  
    - /api/v1/users/deposit  
        deposits money to the user, param needed  
    - /api/v1/users/withdraw  
        withdraws money from the user, param needed  
    - /api/v1/users/transfer  
        transfers money from user to user, two params needed  