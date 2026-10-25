# Lab 5 Event Registration

Alexey V — It302502 DS

A Java console application for managing event registrations. Records are stored in memory and disappear when the program exits.

## JDK

JDK 17 or later is required. Tested with OpenJDK 21; the project targets Java 17.
Gradle wrapper 8.14.3 is included and downloads Gradle on the first run.

## Run Main

From the repository root on Linux or macOS:

```sh
cd lab5
./gradlew --console=plain run
```

On Windows, enter the `lab5` folder and run:

```bat
gradlew.bat --console=plain run
```

In an IDE, open `lab5` as a Gradle project and run the `main` method in `registration.Main`.

## Classes

- `Registration`: represents one registration with five private fields, a constructor, getters and `toString()`.
- `RegistrationManager`: stores registrations in an ArrayList, validates input, assigns IDs, displays, searches, filters and deletes records.
- `Main`: displays the menu, reads input with Scanner, handles invalid numeric input and calls the manager methods.
