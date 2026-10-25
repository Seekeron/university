package registration;

import java.util.ArrayList;

public class RegistrationManager {
    private ArrayList<Registration> registrations = new ArrayList<>();
    private int nextId = 1;

    public void addRegistration(String name, String email, String group, String eventTitle) {
        name = name.trim();
        email = email.trim();
        group = group.trim();
        if (name.length() < 3) {
            System.out.println("Name must contain at least 3 characters.");
            return;
        }
        int at = email.indexOf('@');
        if (at <= 0 || at == email.length() - 1) {
            System.out.println("Email must contain text before and after @.");
            return;
        }
        if (group.isEmpty()) {
            System.out.println("Student group must not be blank.");
            return;
        }
        if (!eventTitle.equals("Web Design Workshop") && !eventTitle.equals("JavaScript Quiz")
                && !eventTitle.equals("IT Career Talk")) {
            System.out.println("Choose an event from 1 to 3.");
            return;
        }
        Registration registration = new Registration(nextId, name, email, group, eventTitle);
        registrations.add(registration);
        System.out.println("Registration created. ID: " + nextId);
        nextId++;
    }

    public void showAll() {
        if (registrations.isEmpty()) {
            System.out.println("No registrations yet.");
            return;
        }
        for (Registration registration : registrations) {
            System.out.println(registration);
        }
    }

    public Registration findById(int id) {
        for (Registration registration : registrations) {
            if (registration.getId() == id) {
                return registration;
            }
        }
        return null;
    }

    public void deleteById(int id) {
        for (int i = 0; i < registrations.size(); i++) {
            if (registrations.get(i).getId() == id) {
                registrations.remove(i);
                System.out.println("Registration deleted.");
                return;
            }
        }
        System.out.println("Registration not found.");
    }

    public void showByEvent(String eventTitle) {
        boolean found = false;
        for (Registration registration : registrations) {
            if (registration.getEventTitle().equals(eventTitle)) {
                System.out.println(registration);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No registrations for this event.");
        }
    }
}
