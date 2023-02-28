import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        try {
            try (ServerSocket serverSocket = new ServerSocket(8080)) {
                System.out.println("Server listening on port 8080...");
                
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                    
                    InputStream in = clientSocket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    
                    String line = reader.readLine();
                    if (line.startsWith("POST")) {
                        String contentLengthHeader = "";
                        while ((line = reader.readLine()) != null && line.length() > 0) {
                            if (line.startsWith("Content-Length:")) {
                                contentLengthHeader = line;
                            }
                        }
                        int contentLength = Integer.parseInt(contentLengthHeader.substring("Content-Length:".length()).trim());
                        char[] content = new char[contentLength];
                        reader.read(content, 0, contentLength);
                        String data = new String(content);
                        
                        FileWriter fileWriter = new FileWriter("data.txt", true);
                        fileWriter.write(data + "\n");
                        fileWriter.close();
                        
                        OutputStream out = clientSocket.getOutputStream();
                        PrintWriter writer = new PrintWriter(out);
                        writer.println("HTTP/1.1 200 OK");
                        writer.println("Content-Type: text/html");
                        writer.println();
                        writer.println("<html><body><h1>Data added to server!</h1></body></html>");
                        writer.flush();
                    } else {
                        OutputStream out = clientSocket.getOutputStream();
                        PrintWriter writer = new PrintWriter(out);
                        writer.println("HTTP/1.1 400 Bad Request");
                        writer.println("Content-Type: text/html");
                        writer.println();
                        writer.println("<html><body><h1>Bad Request</h1></body></html>");
                        writer.flush();
                    }
                    
                    clientSocket.close();
                    System.out.println("Client disconnected.");
                }
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
