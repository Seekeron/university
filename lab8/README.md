# Lab 8 — Spring Data JPA and PostgreSQL

Alexey V — It302502 DS

Lab 7 API extended with persistent PostgreSQL storage. JDK 21, Spring Boot 4.1.2, Gradle wrapper 9.8.2. PostgreSQL 18 was used for verification.

## Local setup is ready

On this computer, run:

```sh
cd /home/alexseyka/learn/web/lab8
./run.sh
```

The script starts PostgreSQL 17 through Docker Compose, waits until it is ready and starts the API on port 8080. Open http://localhost:8080/swagger-ui.html.
Database: `event_db`, local port: `55438`. Data is stored in a persistent Docker volume. The database container restarts automatically when Docker starts.
Local credentials are already configured in ignored `.env` and `application-local.properties` files. Do not publish these files. The application loads the local properties automatically when run from the lab8 directory, including `./gradlew bootRun`.
Stop the application with Ctrl+C. To stop the database, run `docker compose stop`. Start both again with `./run.sh`.
To inspect rows without entering a password:

```sh
docker compose exec database psql -U event_app -d event_db -c 'SELECT id, title, location FROM events ORDER BY id;'
```

## Setup on another computer

With Docker Compose, Python 3 and JDK 21 installed, run `./run.sh` from the lab8 directory. It generates local credentials on first use and starts the database and API. The manual PostgreSQL alternative is below.

## Manual PostgreSQL setup


Start your local PostgreSQL server. As a database administrator, run in psql:

```sql
CREATE USER event_app WITH PASSWORD 'replace_with_your_local_password';
CREATE DATABASE event_db OWNER event_app;
```

Use a new local password. Do not publish it or include it in screenshots.
Hibernate creates the `events` table when the application starts. `ddl-auto=update` is used for this isolated laboratory database.

## Run

```sh
cd lab8
export DB_USER=event_app
read -rs 'DB_PASSWORD?Database password: '; export DB_PASSWORD
./gradlew --console=plain bootRun
```

The password prompt above is for zsh. Alternatively set `DB_USER` and `DB_PASSWORD` in your IDE run configuration, then run `kz.iitu.registration.EventRegistrationApplication`.

`DB_URL` is optional; its default is `jdbc:postgresql://localhost:5432/event_db`.
Configuration placeholders are in `src/main/resources/application.properties`. No real credentials are stored in the project.

Stop other labs using port 8080 before starting. Build and tests also require the database and environment variables:

```sh
./gradlew --console=plain build
```

Open [Swagger UI](http://localhost:8080/swagger-ui.html). OpenAPI JSON: `/v3/api-docs`.

## Endpoints

| Method | Path | Result |
| --- | --- | --- |
| GET | /api/events | 200, all events |
| GET | /api/events?title=quiz | 200, case-insensitive partial search; no matches gives [] |
| GET | /api/events/{id} | 200, event; 404 if missing |
| POST | /api/events | 201, created event with database-generated ID |
| PUT | /api/events/{id} | 200, updated event with the same ID; 404 if missing |
| DELETE | /api/events/{id} | 204, empty response; 404 if missing |
| GET | /api/hello | Welcome message |
| GET | /api/info | Course and application information |

POST and PUT use `Content-Type: application/json`:

```json
{"title":"Java Workshop","location":"Room 303"}
```

Null, missing or blank title/location returns 400 without changing the database. Client-provided IDs are ignored. Both fields are required for PUT. The database starts empty; there are no automatic sample records. Data survives application restarts, and deleting an event does not reset its ID sequence.

## Classes

- `EventRegistrationApplication` starts Spring Boot.
- `RegistrationController` receives requests, validates input and returns HTTP statuses.
- `EventService` performs CRUD through an injected repository.
- `EventRepository` extends `JpaRepository<Event, Long>` and declares title search.
- `Event` maps to the `events` table; `id` is a generated Long primary key.

See [GUIDE.md](GUIDE.md) for demonstration steps and explanations. Source files and wrapper belong in GitHub; exclude build output, IDE settings and local credentials.

## Submission

[Report with four screenshots](report.docx): creation, SQL verification, GET after application restart, and validation error.
