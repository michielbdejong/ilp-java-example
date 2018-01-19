import java.io.IOException;
import java.util.Hashtable;

class ConnectorInstance extends IlpServer{
  IlpClient ilpClient;
  ConnectorInstance(String onwardHost, Integer onwardPort) throws IOException{
    this.ilpClient = new IlpClient(onwardHost, onwardPort);
  }
 
  @Override 
  Hashtable<String, String> handle(Hashtable<String, String> req) throws IOException{
    Integer incomingAmount = Integer.parseInt(req.get("amount"));
    Integer outgoingAmount = incomingAmount-1;
    req.put("amount", Integer.toString(outgoingAmount));
    return this.ilpClient.request(req);
  }
}

