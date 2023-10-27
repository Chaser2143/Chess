package server;
import handlers.*;
import spark.*;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Map;

public class Server {
    private ArrayList<String> names = new ArrayList<>();
    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register a directory for hosting static files
        Spark.staticFiles.location("/public");

        // Register handlers for each endpoint using the method reference syntax

        Spark.delete("/db", this::clearAll);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
    }

    private Object clearAll(Request req, Response res){
        var myHandler = ClearHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    private Object register(Request req, Response res){
        var myHandler = RegisterHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    private Object login(Request req, Response res){
        var myHandler = LoginHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    private Object logout(Request req, Response res){
        var myHandler = LogoutHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    private Object listGames(Request req, Response res){
        var myHandler = ListGamesHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    private Object createGame(Request req, Response res){
        var myHandler = CreateGameHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    private Object joinGame(Request req, Response res){
        var myHandler = JoinGameHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }


}

