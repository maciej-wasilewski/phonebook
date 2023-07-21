import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String address;
    private String number;
    private String timeCreated;
    private String timeLastEdit;

    public Contact(String name, String address, String number, String timeCreated, String timeLastEdit) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.timeCreated = timeCreated;
        this.timeLastEdit = timeLastEdit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTimeLastEdit() {
        return timeLastEdit;
    }

    public void setTimeLastEdit(String timeLastEdit) {
        this.timeLastEdit = timeLastEdit;
    }

    public boolean containsQuery(String query) {
        String lowercaseQuery = query.toLowerCase();
        String contactData = (name + " " + address + " " + number).toLowerCase();

        return contactData.contains(lowercaseQuery);
    }
}