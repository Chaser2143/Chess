package serviceTests;

import chess.ChessGame;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import org.junit.jupiter.api.*;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;
import services.ClearService;

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


//    @BeforeAll
//    public static void init() {
//        existingUser = new TestModels.TestUser();
//        existingUser.username = "Joseph";
//        existingUser.password = "Smith";
//        existingUser.email = "urim@thummim.net";
//
//        newUser = new TestModels.TestUser();
//        newUser.username = "testUsername";
//        newUser.password = "testPassword";
//        newUser.email = "testEmail";
//
//        createRequest = new TestModels.TestCreateRequest();
//        createRequest.gameName = "testGame";
//
//        serverFacade = new TestServerFacade("localhost", TestFactory.getServerPort());
//    }


//    @BeforeEach
//    public void setup() {
//        serverFacade.clear();
//
//        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
//        registerRequest.username = existingUser.username;
//        registerRequest.password = existingUser.password;
//        registerRequest.email = existingUser.email;
//
//        //one user already logged in
//        TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
//        existingAuth = regResult.authToken;
//    }


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

}

