import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;
 
public class IlpClient{
  Socket sockOut;
  IlpClient(String host, Integer port) throws IOException {
    this.sockOut = new Socket(host, port);
  }
  Hashtable<String, String> request(Hashtable<String, String> req) throws IOException{
    PrintWriter pw = new PrintWriter(this.sockOut.getOutputStream());
    pw.println("GET / HTTP/1.0");
    for (String key : req.keySet()){
      System.out.println("client sends " + key + " header " + req.get(key));
      pw.println("ilp-" + key + ": " + req.get(key));
    }
    pw.println();
    pw.flush();
    BufferedReader bisOut = new BufferedReader(new InputStreamReader(sockOut.getInputStream()));
    String inputLine;
    Hashtable<String, String> res = new Hashtable<String, String>();
    while ((inputLine = bisOut.readLine()) != null && inputLine.length() > 0) {
      System.out.println("client receives " + inputLine);
      System.out.flush();
      String[] parts = inputLine.toLowerCase().split(": ");
      if (parts.length == 2 && parts[0].startsWith("ilp-")) {
        res.put(parts[0].substring("ilp-".length()), parts[1]);
      }
    }
    sockOut.close();
    return res;
  }
}
