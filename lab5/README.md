# Lab 5 Event Registration

Alexey V — It302502 DS

A simple console application that stores event registrations in an ArrayList.
Data is kept in memory and disappears when the application exits.

## Requirements and running

JDK 17 or later. Tested with OpenJDK 21, targeting Java 17.
The project includes Gradle wrapper 8.14.3; the first run downloads Gradle.

From this folder on Linux or macOS:

```sh
./gradlew --console=plain run
```

On Windows:

```bat
gradlew.bat --console=plain run
```

In an IDE, open this folder as a Gradle project and run `registration.Main.main()`.
To compile and package the project: `./gradlew build`.

## Three classes

- `Registration`: stores one registration with five private fields, a constructor, getters and `toString()`.
- `RegistrationManager`: owns the ArrayList, validates new registrations, assigns unique IDs, shows, finds, filters and deletes records.
- `Main`: reads console input with Scanner, displays the menu and calls manager methods. It catches NumberFormatException when input is not an integer.

## Menu

1. Add registration
2. Show all registrations
3. Find registration by ID
4. Delete registration by ID
5. Show registrations for an event
0. Exit

Events: Web Design Workshop, JavaScript Quiz, IT Career Talk.
Names need at least 3 characters after trimming. Email needs text before and after `@`.
Group must not be blank. Invalid registrations are not stored.
IDs start at 1 and are never reused during the same run.

## Demonstration

1. Add Alexey V, alexey@example.com, It302502 DS, event 2.
2. Try adding a registration with the name Al; explain the validation message.
3. Search for ID 1 or filter by event 2.
4. Delete ID 1 and display the list.
5. Add another valid record and show that its ID is 2.

Example small code change: change the minimum name length from 3 to 4 in
`RegistrationManager.addRegistration`, update its error message, rebuild and try a three-character name.

## Submission

Include `src`, Gradle build files, `gradlew`, `gradlew.bat`, `gradle/wrapper`,
this README and `report.docx`. `.gitignore` excludes generated build output and local IDE settings.
