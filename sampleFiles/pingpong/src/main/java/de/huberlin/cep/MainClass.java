package de.huberlin.cep;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import io.zeebe.client.api.worker.JobWorker;

public class MainClass {

  public static void main(String[] args) {
    Connector connector = new Connector();
    System.out.println("Connected : \n" + connector.getClient().getConfiguration());
    connector.deployWorkflow("process.bpmn");
    JobWorker w = connector.deployLoggingWorker();
    System.out.println("Write \"quit\" to quit or a number to generate a new message");
    try (Scanner s = new Scanner(System.in)) {
      boolean run = true;
      while (run) {
        String value = s.next().trim();
        switch (value) {
          case "quit":
            run = false;
            break;
          default:
            Map<String, Object> params = new HashMap<>();
            params.put("key", Integer.parseInt(value));
            connector.createWorkflowInstance("ping-pong", params);
            break;
        }
      }
    } catch (Exception e) {
      throw e;
    }
    w.close();

  }
}
