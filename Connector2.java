import java.io.IOException;

public class Connector2{
  public static void main(String[] args) throws IOException{
    ConnectorInstance ci = new ConnectorInstance("localhost", 8090);
    ci.run(8082);
  }
}
