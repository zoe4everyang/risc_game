package edu.duke.ece651.risk_game.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.risk_game.shared.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * This class is used to send requests to the server and receive responses from the server.
 */
public class RISCClient {

    private final CloseableHttpClient theClient;
    private final ObjectMapper jsonMapper;
    private final String serverURL = "http://localhost:8080";

    /**
     * This constructor is used to create a RISCClient object.
     */
    public RISCClient() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setValidateAfterInactivity(-1);
        theClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .disableCookieManagement()
                .disableRedirectHandling()
                .disableAuthCaching()
                .disableAutomaticRetries()
                .disableConnectionState()
                .build();;
        jsonMapper = new ObjectMapper();
    }

    /**
     * This method is used to send a start request to the server.
     *
     * @return the response from the server
     * @throws IOException if the request cannot be sent
     */
    public InitResponse sendStart() throws IOException {
        HttpPost request = new HttpPost(serverURL + "/start");

        // set request headers
        request.setHeader("Content-Type", "application/json");

        // execute the request
        HttpResponse response = theClient.execute(request);

        // fetch response content
        String responseContent = EntityUtils.toString(response.getEntity());

        // map into a response object
        return jsonMapper.readValue(responseContent, InitResponse.class);
    }

    /**
     * This method is used to send a placement request to the server.
     *
     * @param requestBody the request body
     * @return the response from the server
     * @throws IOException if the request cannot be sent
     */
    public Response sendCommand(HttpPost request, Message requestBody) throws IOException {
        // set request headers
        request.setHeader("Content-Type", "application/json");

        // set request body
        String json = jsonMapper.writeValueAsString(requestBody);
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        // execute the request
        CloseableHttpResponse response = theClient.execute(request);

        // fetch response content
        String responseContent = "";
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseContent = EntityUtils.toString(entity);
            }
        } finally {
            response.close();
        }

        // map into a response object
        return jsonMapper.readValue(responseContent, Response.class);
    }

    /**
     * This method is used to send a placement request to the server.
     *
     * @param requestBody the request body
     * @return the response from the server
     * @throws IOException if the request cannot be sent
     */
    public Response sendPlacement(PlacementRequest requestBody) throws IOException {
        HttpPost request = new HttpPost(serverURL + "/place");

        return sendCommand(request, requestBody);
    }

    /**
     * This method is used to send an action request to the server.
     *
     * @param requestBody the request body
     * @return the response from the server
     * @throws IOException if the request cannot be sent
     */
    public Response sendAction(ActionRequest requestBody) throws IOException {
        HttpPost request = new HttpPost(serverURL + "/act");

        return sendCommand(request, requestBody);
    }

    public String sendEnd() throws IOException {
        RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(1)
                    .setSocketTimeout(1)
                    .build();

        HttpGet request = new HttpGet(serverURL + "/gameover");
        request.setConfig(config);
        request.setHeader("Content-Type", "application/json");
        theClient.execute(request);
        return "success";
    }
}
