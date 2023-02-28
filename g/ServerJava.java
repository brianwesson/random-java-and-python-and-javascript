import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ServerGUI extends JFrame {
    private JTextArea log;
    private JButton startBtn;
    private JButton stopBtn;
    private File rootDir;
    private ServerSocket serverSocket;
    private boolean isRunning;

    public ServerGUI() {
        initUI();
        initServer();
    }

    private void initUI() {
        setTitle("Simple Java Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create UI components
        log = new JTextArea();
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        startBtn = new JButton("Start");
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopBtn = new JButton("Stop");
        stopBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(startBtn);
        buttonPanel.add(stopBtn);

        // Add components to frame
        add(logScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initServer() {
        // Set root directory for server
        rootDir = new File(".");

        // Initialize server socket
        try {
            serverSocket = new ServerSocket(80);
        } catch (IOException e) {
            logMessage("Error initializing server socket: " + e.getMessage());
        }
    }

    private void startServer() {
        if (isRunning) {
            logMessage("Server is already running.");
            return;
        }

        isRunning = true;
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);

        logMessage("Server started.");

        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, rootDir);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                logMessage("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    private void stopServer() {
        if (!isRunning) {
            logMessage("Server is not running.");
            return;
        }

        isRunning = false;
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);

        try {
            serverSocket.close();
        } catch (IOException e) {
            logMessage("Error stopping server: " + e.getMessage());
        }

        logMessage("Server stopped.");
    }

    private void logMessage(String message) {
        log.append(message + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    public static void main(String[] args) {
        new ServerGUI();
    }
}
