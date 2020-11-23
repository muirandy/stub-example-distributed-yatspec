package com.github.muirandy.docs.yatspec.distributed.example.stub;

import com.github.muirandy.docs.yatspec.distributed.DiagramLogger;
import com.github.muirandy.docs.yatspec.distributed.Log;
import com.github.muirandy.docs.yatspec.distributed.NoOpDiagramLogger;
import com.github.muirandy.docs.yatspec.distributed.SequenceDiagramFacade;
import com.github.muirandy.docs.yatspec.distributed.log.kafka.KafkaConfiguration;
import com.github.muirandy.docs.yatspec.distributed.log.kafka.KafkaLogger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Path("/reverse")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StubResource {

    private DiagramLogger diagramLogger;
    private SequenceDiagramFacade sequenceDiagramFacade;
    private String sourceSystem = "";
    private String thisSystemName = "";

    public StubResource() {
        diagramLogger = new NoOpDiagramLogger();
        sequenceDiagramFacade = new SequenceDiagramFacade(diagramLogger);
    }

    @POST
    @Path("/configure")
    public void configureLogger(KafkaConfiguration kafkaConfiguration) {
        diagramLogger = new KafkaLogger(kafkaConfiguration.kafkaHost, kafkaConfiguration.kafkaPort);
        sequenceDiagramFacade = new SequenceDiagramFacade(diagramLogger);
        sourceSystem = kafkaConfiguration.sourceSystemName;
        thisSystemName = kafkaConfiguration.thisSystemName;
    }

    @DELETE
    @Path("/configure")
    public void deleteLogger() {
        diagramLogger = new NoOpDiagramLogger();
        sequenceDiagramFacade = new SequenceDiagramFacade(diagramLogger);
        sourceSystem = "";
        thisSystemName = "";
    }

    @POST
    public String reverseString(String input) {
        sequenceDiagramFacade.log(createRequestLog(input));
        List<String> letters = Arrays.asList(input.split(""));
        Collections.reverse(letters);
        String response = letters.stream().collect(Collectors.joining(""));
        sequenceDiagramFacade.log(createResponseLog(response));
        return response;
    }

    private Log createRequestLog(String input) {
        return new Log(buildRequestMessage(), input);
    }

    private Log createResponseLog(String input) {
        return new Log(buildResponseMessage(), input);
    }

    private String buildRequestMessage() {
        return "Request from " + sourceSystem + " to " + thisSystemName;
    }

    private String buildResponseMessage() {
        return "Response from " + thisSystemName + " to " + sourceSystem;
    }
}
