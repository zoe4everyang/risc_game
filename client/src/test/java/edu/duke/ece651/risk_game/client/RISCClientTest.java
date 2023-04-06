package edu.duke.ece651.risk_game.client;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RISCClientTest {
    private static final String SERVER_URL = "http://localhost:8080";
    private CloseableHttpClient httpClient;

    @BeforeEach
    public void setUp() {
        httpClient = HttpClients.createDefault();
    }

    @AfterEach
    public void tearDown() throws IOException {
        httpClient.close();
    }

    @Test
    public void testPostRequest() throws IOException {
        HttpPost postRequest = new HttpPost(SERVER_URL + "/start");
        HttpResponse postResponse = httpClient.execute(postRequest);
        assertEquals(HttpStatus.SC_OK, postResponse.getStatusLine().getStatusCode());
        assertEquals("success", EntityUtils.toString(postResponse.getEntity()));
    }


}
