package webSocketServer;
import org.eclipse.jetty.websocket.api.Session;
import java.util.HashMap;

public class WebSocketSessions {

    /**
     * The First Key Integer represents a gameID
     * The corresponding value hashmap is decomposed into a username key and a session value
     */
    private static HashMap<Integer, HashMap<String, Session>> sessionMap = new HashMap<Integer, HashMap<String, Session>>(); //Singleton Design

    /**
     * Temp method for testing
     */
    public HashMap<Integer, HashMap<String, Session>> getSessionMap(){
        return sessionMap;
    }

    /**
     * Given the proper parameters, adds a session to a specific game
     */
    public void addSessionToGame(int gameID, String userName, Session session){
        //If an entry doesn't exist, add it
        var tempMap = sessionMap.computeIfAbsent(gameID, k -> new HashMap<String, Session>());
        //Add the session
        tempMap.put(userName, session);
    }

    /**
     * Given the proper parameters, removes a specific session from a specific game
     */
    public void removeSessionFromGame(int gameID, String userName, Session session){
        var tempMap = sessionMap.get(gameID); //Find the game
        if(tempMap != null){ //Remove username's session
            tempMap.remove(userName, session);
        }
    }

    /**
     * Completely removes a session from the DB
     */
    public void removeSession(Session session){
        var allKeys = sessionMap.keySet(); //Iterate through every game
        for(var key : allKeys){
            var tempMap = sessionMap.get(key);
            var tempUserNameKeys = tempMap.keySet();
            for(var username : tempUserNameKeys){ //Iterate through all the keys of the submap
                if(tempMap.get(username) == session){
                    tempMap.remove(username, session);
                    return; //Once we find it we can stop
                }
            }
        }
    }

    /**
     * Given a gameID, returns a hashmap of all that game's sessions
     */
    public HashMap<String, Session> getSessionsForGame(int gameID){
        return sessionMap.get(gameID);
    }
}
