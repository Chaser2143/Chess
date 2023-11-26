import exception.ResponseException;
import models.User;
import org.junit.jupiter.api.*;
import reqRes.*;
import ui.ServerFacade;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class clientTests {
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_FORBIDDEN = 403;

    public ServerFacade SF = new ServerFacade("http://localhost:8080");


    @Test
    @Order(1)
    @DisplayName("ClearTest")
    public void successClear(){
        try {
            SF.clearDB();
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(2)
    @DisplayName("RegisterTest")
    public void successRegister(){
        try {
            var Response = SF.register("a", "b", "c");
            System.out.println(Response.toString());
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(3)
    @DisplayName("RegisterTest")
    public void failedRegister(){
        //Bad Credentials
        Assertions.assertThrows(ResponseException.class, () -> SF.register("", "", ""));
    }

    @Test
    @Order(4)
    @DisplayName("LoginTest")
    public void successLogin(){
        try {
            //NOTE : Re-Login for registered user in last test
            var Response = SF.login("a","b");
            System.out.println(Response.toString());
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(5)
    @DisplayName("LoginTest")
    public void failedLogin(){
        Assertions.assertThrows(ResponseException.class, () -> SF.login("x","z")); //Bad Credentials
    }

    @Test
    @Order(6)
    @DisplayName("LogoutTest")
    public void successLogout(){
        try {
            var existingAuth = (String) SF.register("c", "a", "t").get("authToken");
            var Response = SF.logout(existingAuth);
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(7)
    @DisplayName("LogoutTest")
    public void failedLogout(){ //Already logged out
        Assertions.assertThrows(ResponseException.class, () -> SF.logout("")); //Bad Credentials
    }

    @Test
    @Order(8)
    @DisplayName("ListGamesTest")
    public void successListGames(){
        try {
            var existingAuth = (String) SF.register("d", "g", "t").get("authToken");
            var Response = SF.listGames(existingAuth);
            System.out.println(Response);
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(9)
    @DisplayName("ListGamesTest")
    public void failedListGames(){ //Bad Auth
        Assertions.assertThrows(ResponseException.class, () -> SF.listGames("")); //Bad Credentials
    }

    @Test
    @Order(10)
    @DisplayName("CreateGameTest")
    public void successCreateGame(){
        try {
            var existingAuth = (String) SF.register("e", "f", "q").get("authToken");
            var Response = SF.createGame(existingAuth, "newGame");
            System.out.println(Response);
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(11)
    @DisplayName("CreateGameTest")
    public void failedCreateGame(){ //Bad Auth
        Assertions.assertThrows(ResponseException.class, () -> SF.createGame("", "failedGame")); //Bad Credentials
    }

    @Test
    @Order(12)
    @DisplayName("JoinGameTest")
    public void successJoinGame(){
        try {
            var existingAuth = (String) SF.register("w", "r", "j").get("authToken");
            var gameIDDouble = (double) SF.createGame(existingAuth, "joinGame").get("gameID");
            int gameID = (int) Math.round(gameIDDouble);
            var Response = SF.joinGame(existingAuth, "WHITE", gameID);
            System.out.println(Response);
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(13)
    @DisplayName("JoinGameTest")
    public void failedJoinGame(){ //Bad Auth
        Assertions.assertThrows(ResponseException.class, () -> SF.joinGame("", "WHITE", 0)); //Bad Credentials
    }



}

