package serviceTests;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import org.junit.jupiter.api.*;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;
import reqRes.*;
import services.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class serverTests {
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_FORBIDDEN = 403;
    
    private static TestModels.TestUser existingUser;
    private static TestModels.TestUser newUser;
    private static TestModels.TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private String existingAuth;

    @Test
    @Order(1)
    @DisplayName("ClearServiceTest")
    public void successClear(){
        var c = new ClearService();
        c.clear();

        try {
            Assertions.assertTrue(AuthDAO.getInstance().getAll().isEmpty(), "Auth DB not clear");
            Assertions.assertTrue(UserDAO.getInstance().getAll().isEmpty(), "User DB not clear");
            Assertions.assertTrue(GameDAO.getInstance().getAll().isEmpty(), "Game DB not clear");
        }
        catch(dataAccess.DataAccessException dae){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(2)
    @DisplayName("RegisterTest")
    public void successRegister(){
        var r = new RegisterService();
        r.Register(new RegisterReq("c", "b", "a@gmail.com")); //Register a User not already in the DB

        try {
            var UserDAOInst = UserDAO.getInstance();
            var u = UserDAOInst.getUser("c","b","a@gmail.com");
            if(u != null){
                Assertions.assertTrue(true, "Successfully registered a user");
            }
            else{
                Assertions.assertTrue(false, "Failed in registering user");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(3)
    @DisplayName("RegisterTest")
    public void failRegister(){
        var r = new RegisterService();
        r.Register(new RegisterReq("", "b", "a@gmail.com")); //Register a User with incomplete fields

        try {
            var UserDAOInst = UserDAO.getInstance();
            var u = UserDAOInst.getUser("","b","a@gmail.com");
            if(u == null){
                Assertions.assertTrue(true, "Didn't add user because of incomplete field");
            }
            else{
                Assertions.assertTrue(false, "Something went wrong, Added user but shouldn't have");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(4)
    @DisplayName("LoginTest")
    public void SuccessLoginFromRegister(){
        var r = new RegisterService();
        RegisterRes res = r.Register(new RegisterReq("a", "b", "a@gmail.com")); //Register a User not already in the DB

        //Check that these are in the DB
        try {
            var AuthDAOInst = AuthDAO.getInstance();

            //Check that I get back an AT and Username
            if(!res.getAuthToken().isEmpty() && !res.getUsername().isEmpty()){
                if(AuthDAOInst.getAuthToken(res.getAuthToken()) != null){
                    Assertions.assertTrue(true, "Valid Response, AuthToken in DB");
                }
                else{
                    Assertions.assertTrue(false, "AuthToken not found in DB");
                }
            }
            else {
                Assertions.assertTrue(false, "Invalid Response");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(5)
    @DisplayName("LoginTest")
    public void FailLogin(){
        //Try to log in user that doesn't exist
        var l = new LoginService();
        LoginRes res = l.Login(new LoginReq("z", "z"));

        //Check we got the right response back
        if(res.getMessage().equals(Response.FourOOne)){
            Assertions.assertTrue(true, "Successfully caught bad login");
        }
        else {
            Assertions.assertTrue(false, "Failed; logged in bad user");
        }
    }

    @Test
    @Order(6)
    @DisplayName("LogoutTest")
    public void SuccessLogout(){
        //Try to log out user that is logged in
        var r = new RegisterService();
        RegisterRes regRes = r.Register(new RegisterReq("d", "b", "a@gmail.com")); //Register a User not already in the DB

        var l = new LogoutService();

        LogoutRes res = l.Logout(new LogoutReq(regRes.getAuthToken()));

        var ADB = AuthDAO.getInstance();
        //Check we got the right response back
        try {
            if (res.getMessage() == null && null == ADB.getAuthToken(regRes.getAuthToken())) {
                Assertions.assertTrue(true, "Good request, successfully logged out");
            } else {
                Assertions.assertTrue(false, "Error request or failed log out");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(7)
    @DisplayName("LogoutTest")
    public void FailLogout(){
        //Try to log out user that doesn't exist
        var l = new LogoutService();
        LogoutRes res = l.Logout(new LogoutReq("abc"));

        //Check we got the right response back
        if(res.getMessage().equals(Response.FourOOne)){
            Assertions.assertTrue(true, "Successfully caught bad logout");
        }
        else {
            Assertions.assertTrue(false, "Failed; logged out bad user");
        }
    }

    @Test
    @Order(8)
    @DisplayName("CreateGameTest")
    public void SuccessfulCreateGame(){
        //Try to create a game
        var r = new RegisterService();
        RegisterRes regRes = r.Register(new RegisterReq("e", "b", "a@gmail.com")); //Register a User not already in the DB

        var c = new CreateGameService();
        var res = c.CreateGame(new CreateGameReq(regRes.getAuthToken(), "newgame"));

        //Check we got the right response back
        if(res.getMessage() == null){
            Assertions.assertTrue(true, "Successful create game");
        }
        else {
            Assertions.assertTrue(false, "Failed to create game");
        }
    }

    @Test
    @Order(9)
    @DisplayName("CreateGameTest")
    public void FailCreateGame(){
        //Try to create a game
        var r = new RegisterService();
        RegisterRes regRes = r.Register(new RegisterReq("f", "b", "a@gmail.com")); //Register a User not already in the DB

        var c = new CreateGameService();
        var res = c.CreateGame(new CreateGameReq(regRes.getAuthToken(), "")); //No Game Name

        //Check we got the right response back
        if(res.getMessage() != null){
            Assertions.assertTrue(true, "Caught bad request");
        }
        else {
            Assertions.assertTrue(false, "Created bad request");
        }
    }

    @Test
    @Order(10)
    @DisplayName("JoinGameTest")
    public void SuccessJoinGame(){
        //Try to create a game
        var r = new RegisterService();
        RegisterRes regRes = r.Register(new RegisterReq("g", "b", "a@gmail.com")); //Register a User not already in the DB

        var c = new CreateGameService();
        var createres = c.CreateGame(new CreateGameReq(regRes.getAuthToken(), "a")); //No Game Name

        var res = new JoinGameService().JoinGame(new JoinGameReq(regRes.getAuthToken(), "WHITE", createres.getGameID()));

        //Check we got the right response back
        if(res.getMessage() == null){
            Assertions.assertTrue(true, "Joined game successfully");
        }
        else {
            Assertions.assertTrue(false, "Failed to join game");
        }
    }

    @Test
    @Order(11)
    @DisplayName("JoinGameTest")
    public void FailJoinGame(){
        //Try to create a game
        var r = new RegisterService();
        RegisterRes regRes = r.Register(new RegisterReq("h", "b", "a@gmail.com")); //Register a User not already in the DB

        var c = new CreateGameService();
        var createres = c.CreateGame(new CreateGameReq(regRes.getAuthToken(), "a"));

        var res = new JoinGameService().JoinGame(new JoinGameReq(regRes.getAuthToken(), "white", createres.getGameID())); //Bad Capitalization

        //Check we got the right response back
        if(res.getMessage() != null){
            Assertions.assertTrue(true, "Caught bad request");
        }
        else {
            Assertions.assertTrue(false, "Joined with bad request");
        }
    }

    @Test
    @Order(12)
    @DisplayName("ListGamesTest")
    public void SuccessListGames(){
        //Try to create a game
        var r = new RegisterService();
        RegisterRes regRes = r.Register(new RegisterReq("h", "d", "a@gmail.com")); //Register a User not already in the DB

        var c = new CreateGameService();
        c.CreateGame(new CreateGameReq(regRes.getAuthToken(), "a"));
        c.CreateGame(new CreateGameReq(regRes.getAuthToken(), "b"));

        var res = new ListGamesService().ListGames(new ListGamesReq(regRes.getAuthToken()));

        //Check we got the right response back
        if(res.getMessage() == null){
            Assertions.assertTrue(true, "Listed games successfully");
        }
        else {
            Assertions.assertTrue(false, "Failed to list games");
        }
    }

    @Test
    @Order(13)
    @DisplayName("ListGamesTest")
    public void FailListGames(){
        //Try to create a game
        var r = new RegisterService();
        RegisterRes regRes = r.Register(new RegisterReq("j", "b", "a@gmail.com")); //Register a User not already in the DB

        //Create a game
        var c = new CreateGameService();
        c.CreateGame(new CreateGameReq(regRes.getAuthToken(), "a"));

        var l = new LogoutService();
        l.Logout(new LogoutReq(regRes.getAuthToken()));

        //Try to list games with invalid auth
        var res = new ListGamesService().ListGames(new ListGamesReq(regRes.getAuthToken()));

        //Check we got the right response back
        if(res.getMessage() != null){
            Assertions.assertTrue(true, "Caught bad request");
        }
        else {
            Assertions.assertTrue(false, "Joined with bad auth");
        }
    }

    @Test
    @Order(14)
    @DisplayName("ClearServiceTest")
    public void endClear(){
        var c = new ClearService();
        c.clear();

        try {
            Assertions.assertTrue(AuthDAO.getInstance().getAll().isEmpty(), "Auth DB not clear");
            Assertions.assertTrue(UserDAO.getInstance().getAll().isEmpty(), "User DB not clear");
            Assertions.assertTrue(GameDAO.getInstance().getAll().isEmpty(), "Game DB not clear");
        }
        catch(dataAccess.DataAccessException dae){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }
}

