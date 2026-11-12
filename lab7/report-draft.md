# Lab 7 Spring Boot REST Controller

Name: Alexey V

Group: It302502 DS

## Purpose

Extend the Event Registration API with HTTP CRUD operations, title search, JSON request bodies and appropriate response status codes.

## Implementation

RegistrationController maps GET, POST, PUT and DELETE requests and validates title and location. EventService stores events in an ArrayList, assigns increasing IDs and performs list operations. EventInfo supports JSON input through a no-argument constructor and setters. The controller receives its service through constructor injection.

## Screenshots to insert

1. GET requests showing the list, title search and a missing ID.
2. POST creating an event with status 201.
3. PUT replacing both fields with status 200.
4. DELETE returning 204 followed by GET returning 404.
5. Invalid POST returning 400 and a new POST showing that a deleted ID is not reused.

## Conclusion

The API supports creating, reading, updating, deleting and searching events through HTTP. Path variables, query parameters and JSON bodies provide different ways to pass request data. The controller handles HTTP responses while the service manages the in-memory list and assigns IDs. Invalid input is rejected without changing data, and restarting the application restores the sample events.
