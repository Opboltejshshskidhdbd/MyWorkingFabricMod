
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class DownloadHelper {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/download", (exchange -> {
            File zipFile = new File("nitrofps_project.zip");
            if (!zipFile.exists()) {
                String response = "File not found. Please create the ZIP file first.";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }
            
            exchange.getResponseHeaders().set("Content-Type", "application/zip");
            exchange.getResponseHeaders().set("Content-Disposition", "attachment; filename=nitrofps_project.zip");
            exchange.sendResponseHeaders(200, zipFile.length());
            
            try (FileInputStream fis = new FileInputStream(zipFile);
                 OutputStream os = exchange.getResponseBody()) {
                byte[] buffer = new byte[4096];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
            }
        }));
        
        server.start();
        System.out.println("Download server started at http://0.0.0.0:8080/download");
        System.out.println("Use this link to download your ZIP file.");
    }
}
