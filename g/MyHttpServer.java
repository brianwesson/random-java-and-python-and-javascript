import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        String directory = "/path/to/directory"; // Replace with your directory path
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Server started on port " + port);

        server.createContext("/", new MyHandler(directory));
        server.setExecutor(null); // use the default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        private String directory;

        public MyHandler(String directory) {
            this.directory = directory;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            File file = new File(directory + path).getCanonicalFile();
            if (!file.getPath().startsWith(directory)) {
                // Requested file is outside the specified directory
                exchange.sendResponseHeaders(403, 0);
                exchange.close();
                return;
            }
            if (!file.exists()) {
                // Requested file does not exist
                exchange.sendResponseHeaders(404, 0);
                exchange.close();
                return;
            }
            if (file.isDirectory()) {
                // Requested file is a directory
                String indexFile = directory + path + "/index.html";
                file = new File(indexFile);
                if (file.exists()) {
                    // Serve the index file if it exists
                    path = path + "/index.html";
                } else {
                    // Otherwise, list the directory contents
                    exchange.getResponseHeaders().add("Content-Type", "text/html");
                    exchange.sendResponseHeaders(200, 0);
                    OutputStream os = exchange.getResponseBody();
                    os.write("<html><body><ul>".getBytes());
                    for (File f : file.listFiles()) {
                        String link = path + "/" + f.getName();
                        os.write(("<li><a href=\"" + link + "\">" + f.getName() + "</a></li>").getBytes());
                    }
                    os.write("</ul></body></html>".getBytes());
                    os.close();
                    return;
                }
            }
            String contentType = Files.probeContentType(file.toPath());
            exchange.getResponseHeaders().add("Content-Type", contentType);
            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            Files.copy(file.toPath(), os);
            os.close();
        }
    }
}
