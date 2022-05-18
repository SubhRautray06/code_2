package com.zeebe.demo.rest;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.security.RolesAllowed;
import java.time.Instant;

@RestController
@Slf4j
public class ZeebeController {
    @Autowired
    private ZeebeClient zeebeClient;

    //@RolesAllowed("test")
    @PutMapping("/startinstance")
    public ResponseEntity<String> startDemoProcess() {
        ProcessInstanceEvent processInstance = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId("DemoZeebeProcess")
                .latestVersion()
                .send().join();// blocking call!

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Started process instance " + String.valueOf( processInstance.getProcessInstanceKey() ));
    }
    //@RolesAllowed("test")
    @GetMapping("/get")
    public String getProcess() {

        return "success";
    }

    @ZeebeWorker( type = "log-input", autoComplete = true)
    public void showData(final JobClient client, final ActivatedJob job) throws Exception {
        ZeebeController.logJob(job);
    }


    private static void logJob(final ActivatedJob job) {
        log.info(
                "complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variables: {}]",
                job.getType(),
                job.getKey(),
                job.getElementId(),
                job.getProcessInstanceKey(),
                Instant.ofEpochMilli(job.getDeadline()),
                job.getCustomHeaders(),
                job.getVariables());
    }
}
