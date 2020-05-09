package de.huberlin.cep;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import java.util.Map;

public class Connector {

  private ZeebeClient client;

  public ZeebeClient getClient() {
    return client;
  }

  public Connector(){
    this.client = ZeebeClient.newClientBuilder().brokerContactPoint("localhost:26500").build();
  }

  public Connector(String address){
    this.client = ZeebeClient.newClientBuilder().brokerContactPoint(address).build();
  }

  public void deployWorkflow(String id){
    final DeploymentEvent deployment = client.newDeployCommand()
      .addResourceFromClasspath(id)
      .send()
      .join();
    final int version = deployment.getWorkflows().get(0).getVersion();
    System.out.println("Workflow deployed. Version: " + version);
  }

  public void createWorkflowInstance(String id, Map<String, Object> variables ){
    final WorkflowInstanceEvent wfInstance = client.newCreateInstanceCommand()
      .bpmnProcessId(id)
      .latestVersion()
      .variables(variables)
      .send()
      .join();

    final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();
  }
}
