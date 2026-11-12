# Lab 7 Event Registration REST API

Alexey V — It302502 DS

Lab 6 extended with in-memory CRUD and title search. Requires JDK 21.
Spring Boot 4.1.2; Gradle wrapper 9.8.2.

## Run

From the repository root:

```sh
cd lab7
./gradlew --console=plain bootRun
```

Windows: `gradlew.bat --console=plain bootRun` inside `lab7`.
IDE: run `kz.iitu.registration.EventRegistrationApplication`.
Build: `./gradlew build`. Stop with Ctrl+C. Run only one lab on port 8080 at a time.

## Endpoints

Base URL: `http://localhost:8080`.

| Method and path | Result |
| --- | --- |
| GET /api/events | 200, all events |
| GET /api/events?title=quiz | 200, case-insensitive substring search; no match returns [] |
| GET /api/events/{id} | 200, event; missing ID returns 404 |
| POST /api/events | 201, new event with server-assigned ID |
| PUT /api/events/{id} | 200, replaces title and location, preserves ID; missing ID returns 404 |
| DELETE /api/events/{id} | 204, empty body; missing ID returns 404 |
| GET /api/hello | Welcome message from Lab 6 |
| GET /api/info | Configuration values from Lab 6 |

POST and PUT require `Content-Type: application/json` and this body:

```json
{"title":"Java Workshop","location":"Room 303"}
```

Missing, null or blank title/location returns 400 without changing the list.
The server ignores client IDs when creating or updating records. IDs start at 4 after the three sample events and are never reused during a run.
Restarting restores the sample events and resets the counter.

## Classes

- `EventRegistrationApplication`: starts Spring Boot.
- `RegistrationController`: maps requests, validates bodies and chooses HTTP status codes.
- `EventService`: searches and modifies the in-memory list, assigns IDs.
- `EventInfo`: event fields, constructors, getters and title/location setters for JSON input.

The service is injected through the controller constructor. No database or frontend is used.
See [GUIDE.md](GUIDE.md) for a short explanation and copyable curl commands for demonstration and screenshots.
