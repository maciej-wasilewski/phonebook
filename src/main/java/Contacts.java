import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Contacts implements Serializable {
    private List<Contact> contacts;

    public Contacts() {
        contacts = new ArrayList<>();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public int getCount() {
        return contacts.size();
    }

    public List<Contact> searchContacts(String query) {
        List<Contact> searchResults = new ArrayList<>();

        for (Contact contact : contacts) {
            if (contact.containsQuery(query)) {
                searchResults.add(contact);
            }
        }

        return searchResults;
    }

    public void saveToFile(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
            fileOut.close();
            System.out.println("Contacts saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving contacts to file: " + e.getMessage());
        }
    }

    public static Contacts loadFromFile(String filename) {
        Contacts contacts = null;

        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            contacts = (Contacts) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Contacts loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading contacts from file: " + e.getMessage());
        }

        return contacts != null ? contacts : new Contacts();
    }
}