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
import services.ClearService;
import services.LoginService;
import services.LogoutService;
import services.RegisterService;

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
        catch(dataAccess.DataAccessException dae){
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
        catch(dataAccess.DataAccessException dae){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(4)
    @DisplayName("LoginTest")
    public void SuccessLoginFromRegister(){
        var r = new RegisterService();
        RegisterRes res = r.Register(new RegisterReq("c", "b", "a@gmail.com")); //Register a User not already in the DB

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
        catch(dataAccess.DataAccessException dae){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(5)
    @DisplayName("LoginTest")
    public void FailLogin(){
        //Try to log in user that doesn't exist
        var l = new LoginService();
        LoginRes res = l.Login(new LoginReq("c", "b"));

        //Check we got the right response back
        if(res.getMessage().equals(Response.FourOOne)){
            Assertions.assertTrue(true, "Successfully caught bad login");
        }
        else {
            Assertions.assertTrue(false, "Failed; logged in bad user");
        }
    }

    @Test
    @Order(7)
    @DisplayName("LogoutTest")
    public void SuccessLogout(){
        //Try to log out user that is logged in
        var r = new RegisterService();
        RegisterRes regRes = r.Register(new RegisterReq("c", "b", "a@gmail.com")); //Register a User not already in the DB

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
        catch(dataAccess.DataAccessException dae){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(8)
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

}

