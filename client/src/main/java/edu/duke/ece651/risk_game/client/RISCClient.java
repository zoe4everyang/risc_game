package edu.duke.ece651.risk_game.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.duke.ece651.risk_game.shared.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class is used to send requests to the server and receive responses from the server.
 * It is used by the RISCFront class.
 */
public class RISCClient {

    private final CloseableHttpClient theClient;
    private final ObjectMapper jsonMapper;
    private final String serverURL = "http://localhost:8080";

    /**
     * This constructor is used to create a RISCClient object.
     * It creates a CloseableHttpClient object and an ObjectMapper object.
     * It also sets the server URL.
     * It is used by the RISCFront class.
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
                .build();
        jsonMapper = new ObjectMapper();
    }


    private String sendRequest(HttpPost request, String json) throws IOException {
        request.setHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = theClient.execute(request);
        String responseContent = "";
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseContent = EntityUtils.toString(entity);
            }
        } finally {
            response.close();
        }
        System.out.println(response);
        return responseContent;
    }

    public ActionStatus sendLogin(String username, String password) throws IOException {
        HttpPost request = new HttpPost(serverURL + "/login");
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", password);
        return jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), ActionStatus.class);
    }

    public HashSet<Integer> getRoomList(String username) throws IOException {
        HttpPost request = new HttpPost(serverURL + "/roomId");
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", username);
        String response = sendRequest(request, jsonMapper.writeValueAsString(requestBody));
        HashSet<Integer> set = jsonMapper.readValue(response, new TypeReference<>(){});
        return set;
    }

    public Response joinRoom(String username, Integer roomID) throws IOException {
        System.out.println("join room username" + username);
        //HttpPost request = new HttpPost(serverURL + "/join/{" + roomID + "}");
        HttpPost request = new HttpPost(serverURL + "/join/" + roomID);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", username);
        String response = sendRequest(request, jsonMapper.writeValueAsString(requestBody));
        return jsonMapper.readValue(response, Response.class);
    }

    public Response sendPlacement(Integer roomID, PlacementRequest requestBody) throws IOException {
        //HttpPost request = new HttpPost(serverURL + "/place/{" + roomID + "}");
        HttpPost request = new HttpPost(serverURL + "/place/" + roomID);
        return jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), Response.class);
    }

    public ActionStatus sendUpgradeTech(Integer roomID, Integer playerID, boolean upgradeTech) throws IOException {
        //HttpPost request = new HttpPost(serverURL + "/act/upgrade_tech/{" + roomID + "}");
        HttpPost request = new HttpPost(serverURL + "/act/upgradeTech/" + roomID);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("playerID", playerID);
        requestBody.put("upgradeTech", upgradeTech);
        return jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), ActionStatus.class);
    }

    public ActionStatus sendUpgradeUnit(Integer roomID, UpgradeUnitRequest requestBody) throws IOException {
        //HttpPost request = new HttpPost(serverURL + "/act/upgrade_unit/{" + roomID + "}");
        HttpPost request = new HttpPost(serverURL + "/act/upgradeUnit/" + roomID);
        return jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), ActionStatus.class);
    }

    public ActionStatus sendMove(Integer roomID, ActionRequest requestBody) {
        //HttpPost request = new HttpPost(serverURL + "/act/move/{" + roomID + "}");
        HttpPost request = new HttpPost(serverURL + "/act/move/" + roomID);
        ActionStatus status = null;
        try {
            System.out.println("Please input move");

            status = jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), ActionStatus.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    public ActionStatus sendAttack(Integer roomID, ActionRequest requestBody) {
        HttpPost request = new HttpPost(serverURL + "/act/attack/" + roomID);
        ActionStatus status = null;
        try {
            System.out.println("Please input attack");
            status = jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), ActionStatus.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    public ActionStatus sendUpgradeSpy(Integer roomID, Integer playerID, Integer territoryID, Integer unitLevel) throws IOException {
        HttpPost request = new HttpPost(serverURL + "/act/upradeSpy/" + roomID);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("playerID", playerID);
        requestBody.put("territoryID", territoryID);
        requestBody.put("unitLevel", unitLevel);
        return jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), ActionStatus.class);
    }

    public ActionStatus sendMoveSpy(Integer roomID, Integer playerID, Integer territoryID) throws IOException {
        HttpPost request = new HttpPost(serverURL + "/act/moveSpy/" + roomID);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("playerID", playerID);
        requestBody.put("territoryID", territoryID);
        return jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), ActionStatus.class);
    }

    public ActionStatus sendUpgradeCloak(Integer roomID, Integer playerID) throws IOException {
        HttpPost request = new HttpPost(serverURL + "/act/upgradeCloak/" + roomID);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("playerID", playerID);
        return jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), ActionStatus.class);
    }

    public ActionStatus sendAddCloak(Integer roomID, Integer playerID, Integer territoryID) throws IOException {
        HttpPost request = new HttpPost(serverURL + "/act/setCloak/" + roomID);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("playerID", playerID);
        requestBody.put("territoryID", territoryID);
        return jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), ActionStatus.class);
    }

    public Response sendCommit(Integer roomID, int playerID) throws IOException {
        //HttpPost request = new HttpPost(serverURL + "/act/commit/{" + roomID + "}");
        HttpPost request = new HttpPost(serverURL + "/act/commit/" + roomID);
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("playerID", playerID);
        return jsonMapper.readValue(sendRequest(request, jsonMapper.writeValueAsString(requestBody)), Response.class);
    }

}



