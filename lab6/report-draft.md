# Lab 6 Introduction to Spring Boot and Dependency Injection

**Name:** Alexey V

**Group:** It302502 DS

## Purpose

Create a Spring Boot backend for event registration, return JSON through HTTP endpoints, and use constructor-based dependency injection to connect a controller and a service.

## Implementation

EventRegistrationApplication starts the application. RegistrationController handles requests under `/api`. EventService provides a welcome message and three events stored in memory. EventInfo holds the ID, title and location of an event. The controller receives EventService through its constructor, and `@Value` reads application settings from `application.properties`.

## Screenshots to insert

1. `/api/hello`: JSON welcome message.
2. `/api/events`: JSON array containing Web Design Workshop, JavaScript Quiz and IT Career Talk.
3. `/api/info`: application name, course and semester from configuration.
4. Optional: startup console showing port 8080.

## Conclusion

The application uses Spring Boot to run a web server and handle HTTP requests. The controller returns JSON while the service provides the application data. Constructor injection allows Spring to supply the service to the controller. Application properties store settings separately from Java code, and the event list is kept in memory.
