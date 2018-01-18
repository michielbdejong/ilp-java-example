import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;

public abstract class IlpServer{
  abstract Hashtable<String, String> handle(Hashtable<String, String> req) throws IOException;

  public void run(Integer port) throws IOException{
    ServerSocket listener = new ServerSocket(port);
    while(true){
      Socket sockIn = listener.accept();
      BufferedReader bisIn = new BufferedReader(new InputStreamReader(sockIn.getInputStream()));
      String inputLine;
      Hashtable<String, String> req = new Hashtable<String, String>();
      while ((inputLine = bisIn.readLine()) != null && inputLine.length() > 0) {
        System.out.println("Server " + port + " got line in " + inputLine);
        System.out.flush();
        String[] parts = inputLine.toLowerCase().split(": ");
        if (parts.length == 2 && parts[0].startsWith("ilp-")) {
          req.put(parts[0].substring("ilp-".length()), parts[1]);
        }
      }
      Hashtable<String, String> res = this.handle(req);
    
      PrintWriter pw = new PrintWriter(sockIn.getOutputStream(), true);
      pw.println("HTTP/1.0 200 OK");
      for (String key : res.keySet()){
        System.out.println("Server " + port + " sends " + key + " header " + res.get(key));
        pw.println("ilp-" + key + ": " + res.get(key));
      }
      pw.println();
      pw.println("Some data");
      sockIn.close();
    }
  }
}
