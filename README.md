# Musically

This project, built as a school project with [Manne Emile KITSOUKOU](https://github.com/jarhead-killgrave) and [Jana ZEBIAN](https://github.com/JanaZebian) is a REST API to manage a music library, built with `Java` and `Spring`.

To test it out locally on your machine, you need Java 21, Maven 3.8 or compatible, Docker 24.0.6 or compatible, Docker Compose 2.23.0 or compatible.

Clone the repository:

```bash
git clone https://github.com/yousoumar/musically.git
```

Copy environment variables file:

```bash
cp .env.example .env
```

Run all services:

```bash
docker-compose up --build
```

This will spin up a SQL database (PostgresSQL) on [http://localhost:5432](http://localhost:5432), a database administration GUI (Adminer) on [http://localhost:8081](http://localhost:8081), the application with its Swagger (OpenAPI) documentation on [http://localhost:8080](http://localhost:8080).
