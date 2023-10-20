package server;
import handlers.ClearHandler;
import handlers.Handler;
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

//        Spark.post("/name/:name", this::addName);
//        Spark.get("/", this::defaultHtml);
//        Spark.get("/name", this::listNames);
//        Spark.delete("/name/:name", this::deleteName);
    }

    private Object clearAll(Request req, Response res){
        Handler myHandler = ClearHandler.getInstance();
        return myHandler.processSparkRequest(req);
    }

//    //OLD Example Functions
//    private Object addName(Request req, Response res) {
//        names.add(req.params(":name"));
//        return listNames(req, res);
//    }
//
//    private Object defaultHtml(Request req, Response res){
//        return "index.html";
//    }
//
//    private Object listNames(Request req, Response res) {
//        res.type("application/json");
//        return new Gson().toJson(Map.of("name", names));
//    }
//
//    private Object deleteName(Request req, Response res) {
//        names.remove(req.params(":name"));
//        return listNames(req, res);
//    }
}

