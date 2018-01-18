import java.io.IOException;
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
