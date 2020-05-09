package de.huberlin.cep;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.ZeebeClientBuilder;
import io.zeebe.client.api.worker.JobWorker;

import java.util.HashMap;
import java.util.Map;

public class Worker {

  public Worker(){

  }

  public void loadTruck(ZeebeClient client){
    //final ZeebeClient client = ZeebeClient.newClientBuilder().brokerContactPoint("localhost:26500").build();
    final JobWorker jobWorker = client.newWorker()
      .jobType("load-truck")
      .handler((jobClient, activatedJob) -> {
        System.out.println("Loading the truck");
        final Map<String,Object> result = new HashMap<>();
        result.put("transportID", 1);
        result.put("truckInformation", "Mercedes-Banger-5");
        result.put("load", "5kg cocaine");
        result.put("destination", "Berlin");
        //Map<String,Object> input = activatedJob.getVariablesAsMap();

        jobClient.newCompleteCommand(activatedJob.getKey())
          .variables(result)
          .send()
          .join();
      })
      .open();
  }
}
