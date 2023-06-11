import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContactManager {

    ArrayList<Contact> contacts = new ArrayList<>();

    public ContactManager(){
        loadContascts("./Json_files/Contacts.json");
    }

    public ArrayList<Contact> getContacts(){
        return contacts;
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void removeContact(int index){
        contacts.remove(index);
    }

    public void searchContacts(ContactManager contactManager){
        new SearchWindow(contactManager);
    }

    // Methods to load from and write to the .json file

    public void loadContascts(String filePath){

        ObjectMapper mapper = new ObjectMapper();

        try {
            contacts = mapper.readValue(new File(filePath), new TypeReference<ArrayList<Contact>>() {});

            System.out.println("Contacts loaded from JSON file successfully.");
        } 

        catch (IOException e) {
            System.out.println("Error occurred while reading contacts from JSON file: " + e.getMessage());
        }
    }

    public void writeContactsToJsonFile(String filePath) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(filePath), this.contacts);
            System.out.println("Contacts written to JSON file successfully.");
        } 

        catch (IOException e) {
            System.out.println("Error occurred while writing contacts to JSON file: " + e.getMessage());
        }
    }    
}
