import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
 
class ReceiverInstance extends IlpServer{
  @Override 
  Hashtable<String, String> handle(Hashtable<String, String> req) throws IOException{
    Hashtable<String, String> res = new Hashtable<String, String>();
    res.put("fulfillment", "f");
    return res;
  }
}
