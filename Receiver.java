import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
 
public class Receiver{
  public static void main(String[] args) throws IOException{
    ServerSocket listener = new ServerSocket(8090);
    while(true){
      Socket sock = listener.accept();
      BufferedReader bis = new BufferedReader(new InputStreamReader(sock.getInputStream()));
      String inputLine;
      String destination = null;
      String condition = null;
      String expiry = null;
      String amount = null;
      String fulfillment = null;
      while ((inputLine = bis.readLine()) != null && inputLine.length() > 0) {
         String[] parts = inputLine.split(": ");
         if (parts.length >= 2) {
           if (parts[0].matches("ILP-Destination")) { destination = parts[1]; continue; }
           if (parts[0].matches("ILP-Condition")) { condition = parts[1]; continue; }
           if (parts[0].matches("ILP-Expiry")) { expiry = parts[1]; continue; }
           if (parts[0].matches("ILP-Amount")) { amount = parts[1]; continue; }
         }
      }
      if (destination != null && condition != null && expiry != null && amount != null) {
        new PrintWriter(sock.getOutputStream(), true).
                  println("HTTP/1.0 200 OK\r\nILP-Fulfillment: f\r\n\r\nSome data");
        sock.close();
      }
    }
  }
}


