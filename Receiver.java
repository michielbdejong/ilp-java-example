import java.io.IOException;
 
public class Receiver{
  public static void main(String[] args) throws IOException{
    ReceiverInstance ri = new ReceiverInstance();
    ri.run(8090);
  }
}
