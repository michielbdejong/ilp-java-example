import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
 
public class Connector{
  public static void main(String[] args) throws IOException{
    ServerSocket listener = new ServerSocket(8080);
    while(true){
      Socket sockIn = listener.accept();


      BufferedReader bisIn = new BufferedReader(new 
          InputStreamReader(sockIn.getInputStream()));
      String inputLine;
      String destination = null;
      String condition = null;
      String expiry = null;
      String amount = null;
      while ((inputLine = bisIn.readLine()) != null && inputLine.length() > 0) {
        System.out.println("got line in " + inputLine);
        System.out.flush();
        String[] parts = inputLine.split(": ");
        if (parts.length >= 2) {
          if (parts[0].matches("ILP-Destination")) { destination = parts[1]; continue; }
          if (parts[0].matches("ILP-Condition")) { condition = parts[1]; continue; }
          if (parts[0].matches("ILP-Expiry")) { expiry = parts[1]; continue; }
          if (parts[0].matches("ILP-Amount")) { amount = parts[1]; continue; }
        }
      }
      if (destination != null && condition != null && expiry != null && amount != null) {
        Socket sockOut = new Socket("localhost", 8090);
        PrintWriter pw = new PrintWriter(sockOut.getOutputStream());
        pw.println("GET / HTTP/1.0");
        pw.println("ILP-Destination: " + destination);
        pw.println("ILP-Condition: " + condition);
        pw.println("ILP-Expiry: " + expiry);
        pw.println("ILP-Amount: " + new Integer(Integer.parseInt(amount) - 1).toString());
        pw.println();
        pw.flush();
        BufferedReader bisOut = new BufferedReader(new 
            InputStreamReader(sockOut.getInputStream()));
        String fulfillment = null;
        while ((inputLine = bisOut.readLine()) != null && inputLine.length() > 0) {
          System.out.println("got line out " + inputLine);
          System.out.flush();
          String[] parts = inputLine.split(": ");
          if (parts.length >= 2) {
            if (parts[0].matches("ILP-Fulfillment")) {
              fulfillment = parts[1];
              sockOut.close();
              new PrintWriter(sockIn.getOutputStream(), true).
                        println("HTTP/1.0 200 OK\r\nILP-Fulfillment: " + fulfillment + "\r\n\r\nSome data");
              sockIn.close();
            }
          }
        }
      }
    }
  }
}
