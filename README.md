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
    cp .env.dev .env
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Run all services:

    ```bash
    docker compose up -d
   ```
   The following services will be available:
    - PostgreSQL: [http://localhost:5432](http://localhost:5432)
    - Adminer: [http://localhost:8081](http://localhost:8081)(for database administration)
   
5. Run the application:

    ```bash
    mvn spring-boot:run
    ```

6. Open the application in your browser at [http://localhost:8080](http://localhost:8080)

## Usage

## Authors

- [Youssouf OUMAR](https://gitlab.com/yousoumar)
- [Manne Emile KITSOUKOU](https://gitlab.com/jarhead-killgrave)
- [Jana ZEBIAN](https://gitlab.com/JanaZebian)
