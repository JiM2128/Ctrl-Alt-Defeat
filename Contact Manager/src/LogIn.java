import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class LogIn extends JFrame implements ActionListener{

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LogIn(){

        super("Login");

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        
        inputPanel.add(new JLabel("Username: "));
        usernameField = new JTextField(20);
        inputPanel.add(usernameField);
        
        inputPanel.add(new JLabel("Password: "));
        passwordField = new JPasswordField(20);
        inputPanel.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setSize(20, 100);

        add(loginButton, BorderLayout.SOUTH);        
        add(inputPanel, BorderLayout.CENTER);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    // Method for authenticating user
    private boolean authentication(String username, char[] password){
        
        String correctUsername = "Admin";
        String correctPassword = "Password123";

        return username.equals(correctUsername) && String.valueOf(password).equals(correctPassword);
    }

    private void OpenContactManager(){
        new ContactManagerGUI();
    }

    private void clearFields(){
        usernameField.setText("");
        passwordField.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if(authentication(username, password)){
            OpenContactManager();
            dispose();
        }

        else{
            JOptionPane.showMessageDialog(this, "Invalid username or password, please try again.");
            clearFields();
        }

    }
}
