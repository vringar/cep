package de.huberlin.cep;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.client.api.worker.JobWorker;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connector {

  private final ZeebeClient client;
  private final Logger logger;

  public ZeebeClient getClient() {
    return client;
  }

  public Connector(){
    this("localhost:26500");
  }

  public Connector(String address){
    this.client = ZeebeClient.newClientBuilder().brokerContactPoint(address).build();
    this.logger = LoggerFactory.getLogger(this.getClass());
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

  public JobWorker deployLoggingWorker(){
    return client.newWorker()
      .jobType("message-logger")
      .handler((jobClient, activatedJob) -> {
        
        final Map<String,Object> variables = activatedJob.getVariablesAsMap();

        logger.info("Got response from Siddhi for key {}. This was the {} time a message was sent", variables.get("key"), variables.get("count"));
        
        
        jobClient.newCompleteCommand(activatedJob.getKey())
          .variables(variables)
          .send()
          .join();
      })
      .open();
  }
}
