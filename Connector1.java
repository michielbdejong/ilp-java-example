import java.io.IOException;

public class Connector1{
  public static void main(String[] args) throws IOException{
    ConnectorInstance ci = new ConnectorInstance("localhost", 8082);
    ci.run(8081);
  }
}
