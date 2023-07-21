import java.util.List;
import java.util.Scanner;

public class PhonebookApp {
    private Contacts contacts;
    private Scanner scanner;
    private String filename;

    public PhonebookApp(String filename) {
        this.contacts = Contacts.loadFromFile(filename);
        this.scanner = new Scanner(System.in);
        this.filename = filename;
    }

    public void run() {
        System.out.println("Phone Book App");
        System.out.println("Enter 'help' to see available commands.");

        while (true) {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "add":
                    addContact();
                    break;
                case "list":
                    listContacts();
                    break;
                case "search":
                    searchContacts();
                    break;
                case "count":
                    countContacts();
                    break;
                case "exit":
                    exitProgram();
                    break;
                case "help":
                    showHelp();
                    break;
                default:
                    System.out.println("Invalid command. Enter 'help' to see available commands.");
            }
        }
    }

    private void addContact() {
        System.out.println("\nAdding a new contact:");
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();

        System.out.print("Enter number: ");
        String number = scanner.nextLine().trim();

        System.out.print("Enter time created: ");
        String timeCreated = scanner.nextLine().trim();

        System.out.print("Enter time last edited: ");
        String timeLastEdit = scanner.nextLine().trim();

        Contact contact = new Contact(name, address, number, timeCreated, timeLastEdit);
        contacts.addContact(contact);
        contacts.saveToFile(filename);
        System.out.println("Contact added and saved.\n");
    }

    private void listContacts() {
        System.out.println("\nListing all contacts:");
        int index = 1;

        for (Contact contact : contacts.getContacts()) {
            System.out.println(index + ". " + contact.getName());
            index++;
        }

        System.out.println();
        String action = getRecordActionInput("[number], back");

        if (!action.equals("back")) {
            int recordIndex = Integer.parseInt(action) - 1;
            if (recordIndex >= 0 && recordIndex < contacts.getCount()) {
                Contact contact = contacts.getContacts().get(recordIndex);
                printContactDetails(contact);
                performRecordAction(contact);
            } else {
                System.out.println("Invalid contact number.");
            }
        }
    }

    private void searchContacts() {
        System.out.print("\nEnter search query: ");
        String query = scanner.nextLine().trim().toLowerCase();

        System.out.println("Search results:");
        List<Contact> searchResults = contacts.searchContacts(query);
        int index = 1;

        for (Contact contact : searchResults) {
            System.out.println(index + ". " + contact.getName());
            index++;
        }

        System.out.println();
        String action = getRecordActionInput("[number], back, again");

        if (action.equals("again")) {
            searchContacts();
        } else if (!action.equals("back")) {
            int recordIndex = Integer.parseInt(action) - 1;
            if (recordIndex >= 0 && recordIndex < searchResults.size()) {
                Contact contact = searchResults.get(recordIndex);
                printContactDetails(contact);
                performRecordAction(contact);
            } else {
                System.out.println("Invalid contact number.");
            }
        }
    }

    private void countContacts() {
        System.out.println("\nThe Phone Book has " + contacts.getCount() + " records.\n");
    }

    private void exitProgram() {
        contacts.saveToFile(filename);
        System.out.println("\nExiting Phone Book App. Contacts saved to " + filename);
        System.exit(0);
    }

    private void showHelp() {
        System.out.println("\nAvailable commands:");
        System.out.println("add    - Add a new contact");
        System.out.println("list   - List all contacts");
        System.out.println("search - Search contacts");
        System.out.println("count  - Count the number of contacts");
        System.out.println("exit   - Exit the program");
        System.out.println();
    }

    private void printContactDetails(Contact contact) {
        System.out.println("Organization name: " + contact.getName());
        System.out.println("Address: " + contact.getAddress());
        System.out.println("Number: " + contact.getNumber());
        System.out.println("Time created: " + contact.getTimeCreated());
        System.out.println("Time last edit: " + contact.getTimeLastEdit());
        System.out.println();
    }

    private void performRecordAction(Contact contact) {
        while (true) {
            String action = getRecordActionInput("edit, delete, menu");
            if (action.equals("menu")) {
                break;
            } else if (action.equals("edit")) {
                editContact(contact);
            } else if (action.equals("delete")) {
                contacts.removeContact(contact);
                contacts.saveToFile(filename);
                System.out.println("Contact deleted and saved.\n");
                break;
            } else {
                System.out.println("Invalid command. Enter 'menu' to return to the main menu.");
            }
        }
    }

    private void editContact(Contact contact) {
        System.out.println("\nEditing contact:");
        String field = getFieldInput("Select a field (name, address, number)");
        String newValue = getFieldInput("Enter " + field + ": ");

        switch (field) {
            case "name":
                contact.setName(newValue);
                break;
            case "address":
                contact.setAddress(newValue);
                break;
            case "number":
                contact.setNumber(newValue);
                break;
        }

        contact.setTimeLastEdit("Current time"); // Replace with your own logic to set the current time
        contacts.saveToFile(filename);
        System.out.println("Saved");
        printContactDetails(contact);
    }

    private String getRecordActionInput(String options) {
        String input;
        do {
            System.out.print("[record] Enter action (" + options + "): ");
            input = scanner.nextLine().trim().toLowerCase();
        } while (!isValidRecordActionInput(input, options));

        return input;
    }

    private boolean isValidRecordActionInput(String input, String options) {
        if (input.equals("back") || input.equals("again")) {
            return true;
        }

        try {
            int number = Integer.parseInt(input);
            if (number >= 1 && number <= contacts.getCount()) {
                return true;
            } else {
                System.out.println("Invalid contact number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a number or 'back' or 'again'.");
        }

        return false;
    }

    private String getFieldInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    public static void main(String[] args) {
        String filename;

        if (args.length > 0) {
            filename = args[0];
        } else {
            filename = "phonebook.db";
        }

        PhonebookApp app = new PhonebookApp(filename);
        app.run();
    }
}