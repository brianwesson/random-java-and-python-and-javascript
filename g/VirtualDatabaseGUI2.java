import javax.swing.*;
import java.awt.*;

public class VirtualDatabaseGUI2 extends JFrame {
    private VirtualDatabase database;
    private JTextField rowField, colField, valueField;
    private JTextArea outputArea;
    
    public VirtualDatabaseGUI2() {
        super("Virtual Database");
        database = new VirtualDatabase();
        database.createDatabase(3, 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Row: "));
        rowField = new JTextField(10);
        inputPanel.add(rowField);
        inputPanel.add(new JLabel("Column: "));
        colField = new JTextField(10);
        inputPanel.add(colField);
        inputPanel.add(new JLabel("Value: "));
        valueField = new JTextField(10);
        inputPanel.add(valueField);
        
        // Create output panel
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputArea = new JTextArea();
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> insertValue());
        buttonPanel.add(insertButton);
        JButton getValueButton = new JButton("Get Value");
        getValueButton.addActionListener(e -> getValue());
        buttonPanel.add(getValueButton);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        buttonPanel.add(clearButton);
        
        // Add panels to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(outputPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
    
    private void insertValue() {
        int row = Integer.parseInt(rowField.getText());
        int col = Integer.parseInt(colField.getText());
        String value = valueField.getText();
        database.insert(row, col, value);
        outputArea.append("Value inserted at row " + row + ", column " + col + "\n");
        clearFields();
    }
    
    private void getValue() {
        int row = Integer.parseInt(rowField.getText());
        int col = Integer.parseInt(colField.getText());
        String value = database.getValue(row, col);
        outputArea.append("Value at row " + row + ", column " + col + ": " + value + "\n");
        clearFields();
    }
    
    private void clearFields() {
        rowField.setText("");
        colField.setText("");
        valueField.setText("");
    }
    
    public static void main(String[] args) {
        VirtualDatabaseGUI gui = new VirtualDatabaseGUI();
        gui.setVisible(true);
    }
}
