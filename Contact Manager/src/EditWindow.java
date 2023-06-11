import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.*;

public class EditWindow extends JFrame implements ActionListener, InputVarifier{
    
    private JTextField nameField, phoneField, emailField;
    private JComboBox<Contact> contactList;
    private ContactManager contactManager;
    private ArrayList<Contact> contacts;
    private ContactManagerGUI contactManagerGUI;
    private int index;

    
    public EditWindow(ContactManager contactManager, ContactManagerGUI contactManagerGUI) {

        super("Edit Contact");
        this.contactManagerGUI = contactManagerGUI;
        this.contactManager = contactManager;
        contacts = contactManager.getContacts();

        // Panel for the dropdown menu
        JPanel selectionPanel = new JPanel(new GridLayout(2, 1));

        contactList = new JComboBox<>(contacts.toArray(new Contact[0]));        
        selectionPanel.add(new JLabel("Select Contact:"));
        selectionPanel.add(contactList);

        add(selectionPanel, BorderLayout.NORTH);
        add(new JLabel(""), BorderLayout.SOUTH);
        
        // Creating components
        JPanel inputPanel = new JPanel(new GridLayout(5, 1));
        inputPanel.add(new JLabel(" Name: "));
        nameField = new JTextField(20);
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel(" Phone: "));
        phoneField = new JTextField(20);
        inputPanel.add(phoneField);
        
        inputPanel.add(new JLabel(" Email: "));
        emailField = new JTextField(20);
        inputPanel.add(emailField);
        
        add(inputPanel, BorderLayout.CENTER);

        // Adding the buttons
        JButton addButton = new JButton("Save");
        addButton.setSize(20, 10);
        addButton.addActionListener(this);
        inputPanel.add(addButton);
        
        JButton clearButton = new JButton("Delete");
        clearButton.addActionListener(this);
        inputPanel.add(clearButton);
        
        // Configure the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(400, 250);
        setVisible(true);


        // Action listener for selecting a contact from the dropdown menu and adding information to be edited
        contactList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Contact selectedContact = (Contact) contactList.getSelectedItem();

                index = contactList.getSelectedIndex();

                if(selectedContact != null){
                    nameField.setText(selectedContact.getName());
                    phoneField.setText(selectedContact.getPhone());
                    emailField.setText(selectedContact.getEmail());
                }
            }
            
        });
    }

    private void updateComboBox(){
        contactList.setModel(new DefaultComboBoxModel<>(contacts.toArray(new Contact[0])));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (command.equals("Save")){

            String newName = nameField.getText();
            String newPhone = phoneField.getText();
            String newEmail = emailField.getText();

            //Checking if new input is correct
            if(!isName(newName))
                JOptionPane.showMessageDialog(this, "Name must contain only alphabet characters, please try again.");

            else if(!isPhoneNumber(newPhone))
                JOptionPane.showMessageDialog(this, "Phone number must contain only integers, please try again.");

            else if(!isEmail(newEmail))
                JOptionPane.showMessageDialog(this, "Invalid email input, please try again.");

            // Code for editing and saving selected user information with confirmation dialog
            else{
                
                int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to save this contact?", command, JOptionPane.YES_NO_OPTION);
        
                if(confirmation == JOptionPane.YES_NO_OPTION){
    
                    
                    contacts.get(index).setName(newName);
                    contacts.get(index).setPhone(newPhone);
                    contacts.get(index).setEmail(newEmail);
                    
                    contactManagerGUI.displayContacts();
                    contactManager.writeContactsToJsonFile("./Json_files/Contacts.json");
                    
                    updateComboBox();
                    JOptionPane.showMessageDialog(this, "Contact successfully saved");
                }
            }
        }

        // Code for deleting selected user information with confirmation dialog
        else if (command.equals("Delete")){
            
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this contact?", command, JOptionPane.YES_NO_OPTION);

            if(confirmation == JOptionPane.YES_NO_OPTION){
                
                contactManager.removeContact(index);
                contactManager.writeContactsToJsonFile("./Json_files/Contacts.json");

                contactManagerGUI.displayContacts();

                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");

                updateComboBox();

                JOptionPane.showMessageDialog(this, "Contact successfully deleted");
            }
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
