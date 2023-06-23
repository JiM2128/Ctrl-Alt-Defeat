import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class SearchWindow extends JFrame implements ActionListener{

    private JTextArea displayArea;
    private JTextField queryField;
    private ContactManager contactManager;

    private Color lightCyan = new Color(240,255,255);
    private Font textFont = new Font(Font.MONOSPACED, Font.BOLD, 14);


    public SearchWindow(ContactManager contactManager){

        super("Search Results");
        this.contactManager = contactManager;
        
        // Create and add components to the frame
        
        // Input fields and labels
        JPanel inputPanel = new JPanel(new GridLayout(3, 1));
        inputPanel.setBackground(lightCyan);

        JLabel queryLabel = new JLabel("Enter a search query:");
        queryLabel.setFont(textFont);
        inputPanel.add(queryLabel);
        
        queryField = new JTextField(20);
        queryField.setFont(textFont);
        inputPanel.add(queryField);

        JButton searButton = new JButton("Search");
        searButton.addActionListener(this);
        searButton.setFont(textFont);
        inputPanel.add(searButton);

        add(inputPanel, BorderLayout.NORTH);

        // Area for display search results
        displayArea = new JTextArea(20, 50);
        displayArea.setFont(textFont);
        displayArea.setBackground(lightCyan);
        displayArea.setEditable(false);        

        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(400, 500); 
        setVisible(true);
    }

    // Method for printing all results containing entered string
    public void dipslaySearchResults(ArrayList<Contact> contacts, String query){

        if(!query.isEmpty())
        {
            displayArea.setText("");
    
            for(int i = 0; i < contacts.size(); ++i)
            {
                if(contacts.get(i).getName().contains(query) || contacts.get(i).getPhone().contains(query) || contacts.get(i).getEmail().contains(query)){
                    displayArea.append("---------------------------------------------------------------------------------------------\n");
                    displayArea.append(contacts.get(i).toString() + "\n");
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query = queryField.getText();

        if(!query.isEmpty())
            dipslaySearchResults(contactManager.contacts, query);

        else
            JOptionPane.showMessageDialog(this, "Query you entered is empty, please try again.");
    }
}
