import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ContactManagerGUI extends JFrame implements ActionListener {
    
    private ArrayList<Contact> contacts = new ArrayList<>();
    private JTextField nameField, phoneField, emailField;
    //Display display = new Display();
    private JTextArea displayArea = new JTextArea(20, 50);
    
    public ContactManagerGUI() {
        super("Contact Manager");
        
        // Create and add components to the frame
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name: "));
        nameField = new JTextField(20);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Phone: "));
        phoneField = new JTextField(20);
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Email: "));
        emailField = new JTextField(20);
        inputPanel.add(emailField);

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(this);
        inputPanel.add(addButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.NORTH);
        
        JTextArea displayArea = new JTextArea(20, 50);
        displayArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
        
        // Configure the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 500);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Add Contact")) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            Contact contact = new Contact(name, phone, email);
            System.out.print(contact);

            contacts.add(contact);
            displayContacts();
            clearFields();
        } 
        else if (command.equals("Clear")) {
            clearFields();
        }
    }
    
    private void displayContacts() {
        displayArea.setText("");
        for (Contact contact : contacts) {
            displayArea.append(contact.toString() + "\n");
        }
    }
    
    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }    
}
