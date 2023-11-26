package ui;

import com.google.gson.Gson;
import exception.ResponseException;
import reqRes.*;


import java.io.*;
import java.net.*;
import java.util.Map;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public void clearDB() throws ResponseException{
        var path = "/db";
        this.makeRequest("DELETE", path, null, null, null);
    }

    public Map register(String username, String password, String email) throws ResponseException {
        var path = "/user";
        var req = new RegisterReq(username, password, email);
        return this.makeRequest("POST", path, req, Map.class, null);
    }

    public Map login(String username, String password) throws ResponseException {
        var path = "/session";
        var req = new LoginReq(username, password);
        return this.makeRequest("POST", path, req, Map.class, null);
    }

    public Map logout(String Auth) throws ResponseException {
        var path = "/session";
        return this.makeRequest("DELETE", path, null, Map.class, Auth);
    }

    public Map listGames(String Auth) throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, null, Map.class, Auth);
    }

    public Map createGame(String Auth, String GameName) throws ResponseException {
        var path = "/game";
        var req = new CreateGameReq(Auth, GameName);
        return this.makeRequest("POST", path, req, Map.class, Auth);
    }

    public Map joinGame(String Auth, String playerColor, int gameID) throws ResponseException {
        var path = "/game";
        var req = new JoinGameReq(Auth, playerColor, gameID);
        return this.makeRequest("PUT", path, req, Map.class, Auth);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String Auth) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if(Auth != null && !Auth.isEmpty()){
                http.addRequestProperty("authorization", Auth);
            }

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}