# Musically

## Description

This is a simple API REST to manage a music library.

## Installation

### Requirements

- Java 21
- Maven 3.8 or compatible
- Docker 24.0.6 or compatible
- Docker Compose 2.23.0 or compatible

### Steps

1. Clone the repository:
    
    ```bash
    git clone https://gitlab.com/yousoumar/musically.git
    ```

2. Copy environment variables file:

    ```bash
    cp .env.example .env
    ```


3. Run all services:

    ```bash
    docker-compose up --build
   ```
   The following services will be available:
    - PostgresSQL (the database):  [http://localhost:5432](http://localhost:5432)
    - Adminer (database administration GUI): [http://localhost:8081](http://localhost:8081)
    - Musically (the application): [http://localhost:8080](http://localhost:8080) 
    - Swagger (the API documentation): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 


4. Open the application in your browser at [http://localhost:8080](http://localhost:8080)


## Authors

- [Youssouf OUMAR](https://gitlab.com/yousoumar)
- [Manne Emile KITSOUKOU](https://gitlab.com/jarhead-killgrave)
- [Jana ZEBIAN](https://gitlab.com/JanaZebian)
