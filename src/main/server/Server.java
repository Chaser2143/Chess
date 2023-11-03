package server;
import handlers.*;
import spark.*;
import dataAccess.Database;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Main Spark Server
 */
public class Server {
    public static Database DB = new Database(); //The DB can now be globally referenced

    public static Logger logger = initLogger(); //Initialize a logger for the server stuff

    public static void main(String[] args) {
        new Server().run();
    }

    private void run(){

        // Specify the port you want the server to listen on
        Spark.port(8080);

        //Initialize the DB if it doesn't already exist
        try {
            DB.initDB();
        }
        catch(SQLException s){
            logger.severe("The DB Failed to initialize" + s);//Configure Logging...
        }

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

    /**
     * Passes the request to the handler and retrives a response
     * Calls the Clear Handler, which facilitates the clear service
     */
    private Object clearAll(Request req, Response res){
        var myHandler = ClearHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    /**
     * Passes the request to the handler and retrives a response
     * Calls the Register Handler, which facilitates the register service
     */
    private Object register(Request req, Response res){
        var myHandler = RegisterHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    /**
     * Passes the request to the handler and retrives a response
     * Calls the Login Handler, which facilitates the login service
     */
    private Object login(Request req, Response res){
        var myHandler = LoginHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    /**
     * Passes the request to the handler and retrives a response
     * Calls the Logout Handler, which facilitates the logout service
     */
    private Object logout(Request req, Response res){
        var myHandler = LogoutHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    /**
     * Passes the request to the handler and retrives a response
     * Calls the List Games Handler, which facilitates the list games service
     */
    private Object listGames(Request req, Response res){
        var myHandler = ListGamesHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    /**
     * Passes the request to the handler and retrives a response
     * Calls the Create Game Handler, which facilitates the Create Game service
     */
    private Object createGame(Request req, Response res){
        var myHandler = CreateGameHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    /**
     * Passes the request to the handler and retrives a response
     * Calls the Join Game Handler, which facilitates the Join Game service
     */
    private Object joinGame(Request req, Response res){
        var myHandler = JoinGameHandler.getInstance();
        spark.Response myRes = myHandler.processSparkRequest(req, res);
        return myRes.body();
    }

    private static Logger initLogger() {
        Logger logger = Logger.getLogger("ChessLog");
        try {
            FileHandler fileHandler = new FileHandler("ChessLog.txt", true);
            logger.addHandler(fileHandler);
        }
        catch(java.io.IOException ioException){
            System.out.println("File Handler for the Logger is broken...");
        }
        return logger;
    }


}

