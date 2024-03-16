# Musically

This project, built as a school project with [Manne Emile KITSOUKOU](https://github.com/jarhead-killgrave) and [Jana ZEBIAN](https://github.com/JanaZebian) is a REST API to manage a music library, built with Java, Spring, PostgreSQL, and Docker.

To test it out locally on your machine, you need Docker 24.0.6 or compatible, and Docker Compose 2.23.0 or compatible.

First clone the repository:

```bash
git clone https://github.com/yousoumar/musically.git
```

Then copy the environment variables file:

```bash
cp .env.example .env
```

Finally run all services:

```bash
docker-compose up --build
```

This will spin up a SQL database (PostgresSQL) on [http://localhost:5432](http://localhost:5432), a database administration GUI (Adminer) on [http://localhost:8081](http://localhost:8081), the application with its Swagger (OpenAPI) documentation on [http://localhost:8080](http://localhost:8080).
