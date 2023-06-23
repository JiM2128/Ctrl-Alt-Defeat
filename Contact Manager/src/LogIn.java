import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LogIn extends JFrame implements ActionListener{

    private JTextField usernameField;
    private JPasswordField passwordField;

    // Create a new Color for forest green and a new Font for the text
    private Color lightCyan = new Color(240,255,255);
    private Font textFont = new Font(Font.MONOSPACED, Font.BOLD, 14);

    public LogIn(){

        super("Login");

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBackground(lightCyan);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(textFont);
        inputPanel.add(usernameLabel);

        usernameField = new JTextField(20);
        usernameField.setFont(textFont);
        inputPanel.add(usernameField);

        JLabel PasswordLabel = new JLabel("Password: ");
        PasswordLabel.setFont(textFont);
        inputPanel.add(PasswordLabel);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(textFont);
        inputPanel.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setFont(textFont);
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
