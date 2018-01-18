import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;

class ConnectorInstance extends IlpServer{
  IlpClient ilpClient;
  ConnectorInstance() throws IOException{
    this.ilpClient = new IlpClient("localhost", 8090);
  }
 
  @Override 
  Hashtable<String, String> handle(Hashtable<String, String> req) throws IOException{
    Integer incomingAmount = Integer.parseInt(req.get("amount"));
    Integer outgoingAmount = incomingAmount-1;
    req.put("amount", Integer.toString(outgoingAmount));
    return this.ilpClient.request(req);
  }
}

public class Connector{
  public static void main(String[] args) throws IOException{
    ConnectorInstance ci = new ConnectorInstance();
    ci.run(8080);
  }
}
