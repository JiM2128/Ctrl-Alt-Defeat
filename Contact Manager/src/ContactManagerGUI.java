import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class ContactManagerGUI extends JFrame implements ActionListener, InputVarifier {
    
    private ContactManager contactManager = new ContactManager();
    private JTextField nameField, phoneField, emailField;
    private JTextArea displayArea;
    
    public ContactManagerGUI() {
        super("Contact Manager");
        
        // Create and add components to the frame

        // Input fields and labels
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel(" Name: "));
        nameField = new JTextField(20);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel(" Phone: "));
        phoneField = new JTextField(20);
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel(" Email: "));
        emailField = new JTextField(20);
        inputPanel.add(emailField);


        // Buttons
        JButton addButton = new JButton("Add Contact");
        addButton.setSize(20, 10);
        addButton.addActionListener(this);
        inputPanel.add(addButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        inputPanel.add(clearButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this);
        inputPanel.add(editButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);
        
        // Adding area for displaying contacts
        displayArea = new JTextArea(20, 50);
        displayArea.setEditable(false);        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 500);
        setVisible(true);

        // Calls method to display loaded contacts from .json file after logging in
        displayContacts();        
    }

    // Button behavoir
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Add Contact")) {

            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            checkInput(name, phone, email);            

        } 
        else if (command.equals("Clear")) {
            clearFields();
        }

        else if (command.equals("Edit")){
            new EditWindow(contactManager, this);
            displayContacts();
        }

        else if (command.equals("Search")){
            new SearchWindow(contactManager);
        }
    }
    
    void displayContacts() {
        displayArea.setText("");
        displayArea.append("---------------------------------------------------------------------------------------------\n");

        for (Contact contact : contactManager.getContacts()) {
            displayArea.append(contact.toString() + "\n");
            displayArea.append("---------------------------------------------------------------------------------------------\n");
        }
    }
    
    void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    // Method for checking if input data is correct
    // if(input correct) - contact gets added, else - does nothing 
    void checkInput(String name, String phone, String email){

        if(name.isEmpty() || phone.isEmpty() || email.isEmpty()){

            JOptionPane.showMessageDialog(this, "You must enter a value in every field, please try again.");

            return;
        }

        if(isName(name) && isPhoneNumber(phone) && isEmail(email)){

            Contact contact = new Contact(name, phone, email);

            contactManager.addContact(contact);
            contactManager.writeContactsToJsonFile("./Json_files/Contacts.json");

            displayContacts();
            clearFields();

            return;
        }

        else if(!isName(name)){
            JOptionPane.showMessageDialog(this, "Name must contain only alphabetical characters, please try again.");
            return;
        }

        else if(!isPhoneNumber(phone)){
            JOptionPane.showMessageDialog(this, "Phone number must contain only integers, please try again.");
            return;
        }

        else{
            JOptionPane.showMessageDialog(this, "Invalid email input, please try again.");
            return;
        }
    }
    
    public boolean isName(String name) {

         for(char c : name.toCharArray())
            if(!Character.isLetter(c) && c != ' ')
                return false;

        return true;
    }

    public boolean isPhoneNumber(String number) {
        
        for(char c : number.toCharArray())
            if(Character.isLetter(c))
                return false;

        return true;
    }

    public boolean isEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        
        Pattern pattern = Pattern.compile(emailRegex);
        
        return pattern.matcher(email).matches();
    }
}
