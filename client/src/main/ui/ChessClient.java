package ui;

import java.util.ArrayList;
import java.util.Arrays;

import chess.CBoard;
import chess.CGame;
import chess.ChessBoard;
import chess.ChessGameAdapter;
import com.google.gson.GsonBuilder;
import exception.ResponseException;
import models.Game;
import reqRes.ListGamesRes;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    public State state = State.SIGNEDOUT;

    private ArrayList<Game> gameList = new ArrayList<>();

    private String currentAuthToken; //Store the Authtoken String here somewhere...

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "logout" -> logout();
                case "create" -> createGame(params);
                case "list" -> listGames();
                case "join" -> joinGame(params);
                case "observe" -> joinGame(params);
                case "clearall" -> clearDB();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return String.join("\n", "An error occurred, please try again with valid parameters.", ex.getMessage(), help()); //This will basically throw an error whenever something doesn't work...
        }
    }

    /**
     * Clears the DB, Hidden test function
     */
    public String clearDB() throws ResponseException{
        assertSignedIn(); //Add this in later
        server.clearDB();
        return "Successfully Cleared the Database!";
    }

    /**
     * Prompts the user to input login information. Calls the server login API to login the user. When successfully logged in, the client should transition to the Postlogin UI.
     */
    public String login(String... params) throws ResponseException{
        var response = server.login(params[0], params[1]);
        if(!response.isEmpty()){
            currentAuthToken = (String) response.get("authToken");
            System.out.println("Set new AuthToken : " + currentAuthToken);
            state = State.SIGNEDIN; //Set the state
            return help();
        }
        else{
        return "Something went wrong. Please try again...";
        }
    }

    /**
     * Prompts the user to input registration information. Calls the server register API to register and login the user. If successfully registered, the client should be logged in and transition to the Postlogin UI.
     */
    public String register(String... params) throws ResponseException{
        var response = server.register(params[0], params[1], params[2]);
        if(!response.isEmpty()){
            currentAuthToken = (String) response.get("authToken");
            System.out.println("Set new AuthToken : " + currentAuthToken);
            state = State.SIGNEDIN; //Set the state
            return help();
        }
        else{
            return "Something went wrong. Please try again...";
        }
    }

    /**
     * 	Logs out the user. Calls the server logout API to logout the user. After logging out with the server, the client should transition to the Prelogin UI.
     */
    public String logout() throws ResponseException{
        assertSignedIn();
        server.logout(currentAuthToken);
        currentAuthToken = null; //Get rid of current Auth
        state = State.SIGNEDOUT; //Set the state
        return help();
    }

    /**
     * Lists all the games that currently exist on the server.
     * Calls the server list API to get all the game data, and displays the games in a numbered list, including the game name and players (not observers) in the game.
     * The numbering for the list should be independent of the game IDs.
     */
    public String listGames() throws ResponseException{ //STUCK!
        assertSignedIn();
        ListGamesRes response = server.listGames(currentAuthToken);
        gameList = response.getGames();
        StringBuilder stringResponse = new StringBuilder();
        int gameCount = 0;
        for(var g : response.getGames()){
            stringResponse.append(gameCount + "| GameID : " + g.getGameID() + "|GameName : " + g.getGameName() + "| White Player : " + g.getWhiteUsername() + "| Black Player : " + g.getBlackUsername() + "\n");
            gameCount++;
        }
        return stringResponse.toString();
    }

    public String joinGame(String... params) throws ResponseException{
        assertSignedIn();
        if(params.length == 1){ //Observing a game
            server.joinGame(currentAuthToken, "", Integer.valueOf(params[0]));
        }
        else{ //Joining a game
            server.joinGame(currentAuthToken, params[1].toUpperCase(), Integer.valueOf(params[0]));
        }
        CBoard b = new CBoard();
        b.resetBoard();
//        drawBoard.main(b);//Draw the default board in both directions
        return "Enter game state here";
    }

    /**
     * Allows the user to input a name for the new game. Calls the server create API to create the game. This does not join the player to the created game; it only creates the new game in the server.
     */
    public String createGame(String... params) throws ResponseException{
        assertSignedIn();
        var response = server.createGame(currentAuthToken, params[0]);
        if(!response.isEmpty()){
            var gameIDDouble = (double) response.get("gameID");
            int gameID = (int) Math.round(gameIDDouble);
            System.out.println("Created a new game with ID : " + gameID);
            return help();
        }
        else{
            return "Something went wrong. Please try again...";
        }
    }

//
//    public String signIn(String... params) throws ResponseException {
//        if (params.length >= 1) {
//            state = State.SIGNEDIN;
//            visitorName = String.join("-", params);
//            ws = new WebSocketFacade(serverUrl, notificationHandler);
//            ws.enterPetShop(visitorName);
//            return String.format("You signed in as %s.", visitorName);
//        }
//        throw new ResponseException(400, "Expected: <yourname>");
//    }
//
//    public String rescuePet(String... params) throws ResponseException {
//        assertSignedIn();
//        if (params.length >= 2) {
//            var name = params[0];
//            var type = PetType.valueOf(params[1].toUpperCase());
//            var friendArray = Arrays.copyOfRange(params, 2, params.length);
//            var friends = new ArrayFriendList(friendArray);
//            var pet = new Pet(0, name, type, friends);
//            pet = server.addPet(pet);
//            return String.format("You rescued %s. Assigned ID: %d", pet.name(), pet.id());
//        }
//        throw new ResponseException(400, "Expected: <name> <CAT|DOG|FROG> [<friend name>]*");
//    }
//
//    public String listPets() throws ResponseException {
//        assertSignedIn();
//        var pets = server.listPets();
//        var result = new StringBuilder();
//        var gson = new Gson();
//        for (var pet : pets) {
//            result.append(gson.toJson(pet)).append('\n');
//        }
//        return result.toString();
//    }
//
//    public String adoptPet(String... params) throws ResponseException {
//        assertSignedIn();
//        if (params.length == 1) {
//            var id = Integer.parseInt(params[0]);
//            var pet = getPet(id);
//            if (pet != null) {
//                server.deletePet(id);
//                return String.format("%s says %s", pet.name(), pet.sound());
//            }
//        }
//        throw new ResponseException(400, "Expected: <pet id>");
//    }
//
//    public String adoptAllPets() throws ResponseException {
//        assertSignedIn();
//        var buffer = new StringBuilder();
//        for (var pet : server.listPets()) {
//            buffer.append(String.format("%s says %s%n", pet.name(), pet.sound()));
//        }
//
//        server.deleteAllPets();
//        return buffer.toString();
//    }
//
//    public String signOut() throws ResponseException {
//        assertSignedIn();
//        ws.leavePetShop(visitorName);
//        ws = null;
//        state = State.SIGNEDOUT;
//        return String.format("%s left the shop", visitorName);
//    }
//
//    private Pet getPet(int id) throws ResponseException {
//        for (var pet : server.listPets()) {
//            if (pet.id() == id) {
//                return pet;
//            }
//        }
//        return null;
//    }
//
    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    login <USERNAME> <PASSWORD> - to play chess
                    quit - terminates program
                    help - with possible commands
                    """;
        }
        return """
                create <NAME> - a game (Make sure your game name is one word)
                list - games
                join <ID> [WHITE|BLACK|<empty>] - a game
                observe <ID> - a game
                logout - when you are done
                quit - terminates program
                help - with possible commands
                """;
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(400, "You must sign in");
        }
    }
}