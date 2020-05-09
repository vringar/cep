package de.huberlin.cep;

import java.util.HashMap;
import java.util.Map;

public class MainClass {
  public static void main(String[] args){
    Connector connector = new Connector();
    System.out.println("Connected : \n" + connector.getClient().getConfiguration());
    if(args.length == 0) {
      System.out.println("WTF");
      connector.deployWorkflow("process.bpmn");
      return;
    } 
    Map<String, Object> params = new HashMap<>();
    params.put("name", "pong");
    params.put("foo", "bar");
    params.put("key", Integer.parseInt(args[0]));
    connector.createWorkflowInstance("ping-pong", params);
  }
}
