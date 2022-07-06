package services;

import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestService {

    private final String server = "http://193.196.54.32:3000";
    private String authToken;
    private Gson gson;
    private String loggedinPlayername = "";

    public RequestService() {
        this.gson = new Gson();
    }

    public boolean isLoggedIn() {
        return authToken != null;
    }

    public void updatePlayerStats(int highscore, int knowledge) {
        HttpPut put = new HttpPut(server + "/api/player");
        put.setHeader("Authorization", this.authToken);

        JSONObject body = new JSONObject();
        body.put("highscore", highscore);
        body.put("knowledge", knowledge);

        put.setEntity(new StringEntity(body.toString(), ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(put)) {
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.err.println("HTTP Error: " + EntityUtils.toString(response.getEntity()));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(String playername, String password) throws IOException {
        HttpPost post = new HttpPost(server + "/api/player/login");

        JSONObject body = new JSONObject();
        body.put("playername", playername);
        body.put("password", password);

        post.setEntity(new StringEntity(body.toString(), ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
                this.authToken = jsonResponse.get("token").toString();
                this.loggedinPlayername = playername;
            } else {
                System.err.println("HTTP Error: " + EntityUtils.toString(response.getEntity()));
            }
        }

    }

    public void register(String playername, String password) throws IOException {
        HttpPost post = new HttpPost(server + "/api/player");

        JSONObject body = new JSONObject();
        body.put("playername", playername);
        body.put("password", password);

        post.setEntity(new StringEntity(body.toString(), ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
                this.authToken = jsonResponse.get("token").toString();
                this.loggedinPlayername = playername;
            } else {
                System.err.println("HTTP Error: " + EntityUtils.toString(response.getEntity()));
            }
        }

    }

    public PlayerStats getPlayer() throws IOException {
        HttpGet get = new HttpGet(server + "/api/player");
        get.setHeader("Authorization", this.authToken);

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(get)) {
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String jsonResponseString = EntityUtils.toString(response.getEntity());
                PlayerStats player = gson.fromJson(jsonResponseString, PlayerStats.class);
                return player;
            } else {
                System.err.println("HTTP Error: " + EntityUtils.toString(response.getEntity()));
            }
            return null;
        }

    }

    public LeaderboardEntry[] getLeaderboard() throws IOException {
        HttpGet get = new HttpGet(server + "/api/leaderboard");
        get.setHeader("Authorization", this.authToken);

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(get)) {
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String jsonResponseString = EntityUtils.toString(response.getEntity());
                LeaderboardEntry[] leaderboardEntries = gson.fromJson(jsonResponseString, LeaderboardEntry[].class);
                return leaderboardEntries;
            } else {
                System.err.println("HTTP Error: " + EntityUtils.toString(response.getEntity()));
            }
            return null;
        }

    }

    public String getLoggedinPlayername() {
        return loggedinPlayername;
    }

}
