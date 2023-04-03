package edu.duke.ece651.risk_game.client;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

public class RISCClient {

    HttpClient theClient;
    ObjectMapper jsonMapper;

    public RISCClient() {
        theClient = HttpClientBuilder.create().build();
        jsonMapper = new ObjectMapper();
    }

    public InitResponse sendStart() throws IOException {
        HttpPost request = new HttpPost("http://localhost:8080" + "/start");

        // set request headers
        request.setHeader("Content-Type", "application/json");

        // execute the request
        HttpResponse response = theClient.execute(request);

        // fetch response content
        String responseContent = EntityUtils.toString(response.getEntity());

        // map into a response object
        return jsonMapper.readValue(responseContent, InitResponse.class);
    }

    public Response sendCommand(HttpPost request, Message requestBody) throws IOException {
        // set request headers
        request.setHeader("Content-Type", "application/json");

        // set request body
        String json = jsonMapper.writeValueAsString(requestBody);
        request.setEntity(new StringEntity(json));

        // execute the request
        HttpResponse response = theClient.execute(request);

        // fetch response content
        String responseContent = EntityUtils.toString(response.getEntity());

        // map into a response object
        return jsonMapper.readValue(responseContent, Response.class);
    }
    public Response sendPlacement(PlacementRequest requestBody) throws IOException {
        HttpPost request = new HttpPost("http://localhost:8080" + "/place");

        return sendCommand(request, requestBody);
    }

    public Response sendMoveCommit(MoveCommitRequest requestBody) throws IOException {
        HttpPost request = new HttpPost("http://localhost:8080" + "/Movecommit");

        return sendCommand(request, requestBody);
    }
}
