import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class ContactManagerGUI extends JFrame implements ActionListener, InputVarifier {
    
    private ContactManager contactManager = new ContactManager();
    private JTextField nameField, phoneField, emailField;
    private JTextArea displayArea;

    // Create a new Color for forest green and a new Font for the text
    private Color lightCyan = new Color(240,255,255);
    //private Font textFont = new Font("Arial", Font.BOLD, 14);
    private Font textFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
    private LineBorder blackBorder = new LineBorder(Color.BLACK, 3);
    
    public ContactManagerGUI() {
        super("Contact Manager");
        
        // Create and add components to the frame

        // Input fields and labels
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.setBackground(lightCyan);
        inputPanel.setBorder(blackBorder);

        JLabel nameLabel = new JLabel(" Name: ");
        nameLabel.setFont(textFont);
        inputPanel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setFont(textFont);
        inputPanel.add(nameField);

        JLabel phoneLabel = new JLabel(" Phone: ");
        phoneLabel.setFont(textFont);
        inputPanel.add(phoneLabel);

        phoneField = new JTextField(20);
        phoneField.setFont(textFont);
        inputPanel.add(phoneField);

        JLabel emailLabel = new JLabel(" Email: ");
        emailLabel.setFont(textFont);
        inputPanel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setFont(textFont);
        inputPanel.add(emailField);

        JButton addButton = new JButton("Add Contact");
        addButton.setFont(textFont);
        addButton.setSize(20, 10);
        addButton.addActionListener(this);
        inputPanel.add(addButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(textFont);
        clearButton.addActionListener(this);
        inputPanel.add(clearButton);

        JButton editButton = new JButton("Edit");
        editButton.setFont(textFont);
        editButton.addActionListener(this);
        inputPanel.add(editButton);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(textFont);
        searchButton.addActionListener(this);
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea(20, 50);
        displayArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));

        displayArea.setFont(textFont);
        displayArea.setBackground(lightCyan);
        displayArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBackground(lightCyan);
        add(scrollPane, BorderLayout.CENTER);


        // Configure the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 500);
        setVisible(true);

        // Calls method to display loaded contacts from .json file after logging in
        displayContacts();        
    }

    // Button behaviour
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
        displayArea.append("-----------------------------------------------------------------\n");

        for (Contact contact : contactManager.getContacts()) {
            displayArea.append(contact.toString() + "\n");
            displayArea.append("-----------------------------------------------------------------\n");
        }
    }
    
    void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    // Method for checking if input data is correct
    // if(input correct) - contact gets added, else - shows error message 
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
