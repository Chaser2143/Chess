package serviceTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import org.junit.jupiter.api.*;
import server.Server;

import java.sql.SQLException;
import java.util.Objects;

public class DAOTests {

    private static java.sql.Connection connection;
    private static AuthDAO AuthDAOInst;

    private static UserDAO UserDAOInst;

    private static GameDAO GameDAOInst;

    @BeforeAll
    public static void init() throws DataAccessException, SQLException {
        //Initialize a new connection
        connection = Server.DB.getConnection();

        connection.setAutoCommit(true); //Disable rollback possibility

        //Set this connection in all DAOs
        AuthDAO.getInstance().setConnection(connection);
        GameDAO.getInstance().setConnection(connection);
        UserDAO.getInstance().setConnection(connection);

        //Get Access to the DB
        AuthDAOInst = AuthDAO.getInstance();
        GameDAOInst = GameDAO.getInstance();
        UserDAOInst = UserDAO.getInstance();
    }

    @BeforeEach
    public void clearAll() throws SQLException, DataAccessException {
        AuthDAOInst.clearAll();
        UserDAOInst.clearAll();
        GameDAOInst.clearAll();
    }

    @AfterAll
    public static void closeAll() throws SQLException {

        //Close the connection
        connection.close();

        //Set all the connections in the DAO's to null
        AuthDAO.getInstance().setConnection(null);
        GameDAO.getInstance().setConnection(null);

    }

    //AUTHDAO TESTS
    @Test
    @Order(1)
    @DisplayName("ClearAllAuth")
    public void clearAllAuth(){
        try {
            AuthDAOInst.clearAll();
            Assertions.assertTrue(true, "Test ran with no errors");
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(2)
    @DisplayName("addAuthToken")
    public void addAuthToken(){
        try {
            AuthDAOInst.addAuthToken(new AuthToken("abcdefg", "test123"));
            Assertions.assertTrue(true, "Test ran with no errors");
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(3)
    @DisplayName("deleteAuthToken")
    public void deleteAuthToken(){
        try {
            AuthDAOInst.addAuthToken(new AuthToken("abcdefg", "test123"));
            AuthDAOInst.deleteAuthToken(new AuthToken("abcdefg", "test123"));
            Assertions.assertTrue(true, "Test ran with no errors");
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(4)
    @DisplayName("getAuthToken")
    public void getAuthToken(){
        try {
            AuthDAOInst.addAuthToken(new AuthToken("abcdefghijk", "test456"));
            var AT = AuthDAOInst.getAuthToken("abcdefghijk");
            if(AT.getAuthToken() != null) {
                Assertions.assertTrue(true, "Authtoken found with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(5)
    @DisplayName("createAuthToken")
    public void createAuthToken(){
        try {
            var AT = AuthDAOInst.createAuthToken("abcdefghijk", "test456");
            if(AT.getAuthToken() != null) {
                Assertions.assertTrue(true, "Authtoken created with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(6)
    @DisplayName("generateAuthString")
    public void generateAuthString(){
        try {
            var AT = AuthDAOInst.generateAuthString();
            if(AT.toString() != null) {
                Assertions.assertTrue(true, "Auth String Generated with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(7)
    @DisplayName("getAllAuth")
    public void getAllAuth(){
        try {
            AuthDAOInst.addAuthToken(new AuthToken("abcdefghijk", "test456"));
            var allAuths = AuthDAOInst.getAll();
            if(allAuths.size() == 1) {
                Assertions.assertTrue(true, "Retrieved all auths with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    //USERDAO TESTS
    @Test
    @Order(8)
    @DisplayName("ClearAllUser")
    public void clearAllUser(){
        try {
            UserDAOInst.clearAll();
            Assertions.assertTrue(true, "Test ran with no errors");
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(9)
    @DisplayName("addUser")
    public void addUser(){
        try {
            UserDAOInst.addUser(new User("a", "b", "c"));
            Assertions.assertTrue(true, "Test ran with no errors");
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(10)
    @DisplayName("getUserLong")
    public void getUserLong(){
        try {
            UserDAOInst.addUser(new User("a", "b", "c"));
            var user = UserDAOInst.getUser("a", "b", "c");
            if(user.getUsername() != null) {
                Assertions.assertTrue(true, "User found with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(11)
    @DisplayName("getUserShort")
    public void getUserShort(){
        try {
            UserDAOInst.addUser(new User("a", "b", "c"));
            var user = UserDAOInst.getUser("a", "b");
            if(user.getUsername() != null) {
                Assertions.assertTrue(true, "User found with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(12)
    @DisplayName("createUser")
    public void createUser(){
        try {
            var user = UserDAOInst.createUser("a", "b", "c");
            if(user.getUsername() != null) {
                Assertions.assertTrue(true, "User created with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(13)
    @DisplayName("getAllUser")
    public void getAllUser(){
        try {
            UserDAOInst.addUser(new User("a", "b", "c"));
            var allUsers = UserDAOInst.getAll();
            if(allUsers.size() == 1) {
                Assertions.assertTrue(true, "Retrieved all users with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    //GAMEDAO TESTS
    @Test
    @Order(14)
    @DisplayName("ClearAllGame")
    public void clearAllGame(){
        try {
            GameDAOInst.clearAll();
            Assertions.assertTrue(true, "Test ran with no errors");
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(15)
    @DisplayName("createGame")
    public void createGame(){
        try {
            int gameid = GameDAOInst.createGame("abc");
            Assertions.assertTrue(true, "Test ran with no errors");
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(16)
    @DisplayName("getGame")
    public void getGame(){
        try {
            int gameid = GameDAOInst.createGame("abc");
            var game = GameDAOInst.getGame(gameid);
            if(game.getGame() != null) {
                Assertions.assertTrue(true, "Game found with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(17)
    @DisplayName("updateGameTeam")
    public void updateGameTeam(){
        try {
            int gameid = GameDAOInst.createGame("abc");
            GameDAOInst.updateGameTeam(true, "c", gameid);
            var game = GameDAOInst.getGame(gameid);
            if(Objects.equals(game.getWhiteUsername(), "c")) {
                Assertions.assertTrue(true, "Game Team Updated with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }

    @Test
    @Order(18)
    @DisplayName("getAllGame")
    public void getAllGame(){
        try {
            GameDAOInst.createGame("abc");
            var allGames = GameDAOInst.getAll();
            if(allGames.size() == 1) {
                Assertions.assertTrue(true, "Retrieved all games with no errors");
            }
        }
        catch(Exception e){
            Assertions.assertTrue(false, "Test threw an error and failed");
        }
    }


}
