import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileSystemJavaGUI {

    private FileSystemJava fileSystem;
    private JTextArea textArea;
    private JFileChooser fileChooser;

    public FileSystemJavaGUI() {
        fileSystem = new FileSystemJava("data.txt");
        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        fileChooser = new JFileChooser();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("File System GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400));

        JPanel panel = new JPanel();
        JButton saveButton = new JButton("Save File");
        JButton loadButton = new JButton("Load File");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showSaveDialog(panel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    fileSystem.saveFile(file.getName(), file);
                    textArea.append("Saved file: " + file.getName() + "\n");
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(panel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String content = fileSystem.loadFile(file.getName());
                    textArea.append("Loaded file: " + file.getName() + "\n");
                    textArea.append("Content: " + content + "\n");
                }
            }
        });

        panel.add(saveButton);
        panel.add(loadButton);

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        FileSystemJavaGUI fileSystemGUI = new FileSystemJavaGUI();
        fileSystemGUI.createAndShowGUI();
    }
}
