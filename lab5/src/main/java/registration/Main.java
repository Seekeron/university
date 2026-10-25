package registration;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        RegistrationManager manager = new RegistrationManager();
        boolean running = true;
        while (running) {
            showMenu();
            int option = readNumber("Choose an option: ");
            switch (option) {
                case 1:
                    addRegistration(manager);
                    break;
                case 2:
                    manager.showAll();
                    break;
                case 3:
                    int searchId = readNumber("Enter ID: ");
                    if (searchId <= 0) {
                        System.out.println("ID must be positive.");
                        break;
                    }
                    Registration registration = manager.findById(searchId);
                    if (registration == null) {
                        System.out.println("Registration not found.");
                    } else {
                        System.out.println(registration);
                    }
                    break;
                case 4:
                    int deleteId = readNumber("Enter ID: ");
                    if (deleteId <= 0) {
                        System.out.println("ID must be positive.");
                        break;
                    }
                    manager.deleteById(deleteId);
                    break;
                case 5:
                    String eventTitle = selectEvent();
                    if (eventTitle != null) {
                        manager.showByEvent(eventTitle);
                    }
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Choose an option from 0 to 5.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n1. Add registration");
        System.out.println("2. Show all registrations");
        System.out.println("3. Find registration by ID");
        System.out.println("4. Delete registration by ID");
        System.out.println("5. Show registrations for an event");
        System.out.println("0. Exit");
    }

    private static int readNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException error) {
                System.out.println("Enter a whole number.");
            }
        }
    }

    private static String selectEvent() {
        System.out.println("1. Web Design Workshop");
        System.out.println("2. JavaScript Quiz");
        System.out.println("3. IT Career Talk");
        int choice = readNumber("Select event: ");
        switch (choice) {
            case 1: return "Web Design Workshop";
            case 2: return "JavaScript Quiz";
            case 3: return "IT Career Talk";
            default:
                System.out.println("Choose an event from 1 to 3.");
                return null;
        }
    }

    private static void addRegistration(RegistrationManager manager) {
        System.out.print("Full name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Student group: ");
        String group = scanner.nextLine();
        String eventTitle = selectEvent();
        if (eventTitle != null) {
            manager.addRegistration(name, email, group, eventTitle);
        }
    }
}
