package handlers;

import com.google.gson.Gson;
import reqRes.*;
import services.CreateGameService;
import services.JoinGameService;

import java.util.Map;


public class JoinGameHandler extends Handler{
    protected static JoinGameHandler instance = new JoinGameHandler();

    public static JoinGameHandler getInstance(){
        return instance;
    }

    /**
     * Deserializes the spark request
     * Retrieves Auth from header and the player color + GameID from body
     * @param req is a spark request
     * @return native request
     */
    @Override
    protected Request deserializeReq(spark.Request req) {
        var serializer = new Gson();
        var objFromJson = serializer.fromJson(req.body(), Map.class);
        return new JoinGameReq(req.headers("authorization"), (String) objFromJson.get("playerColor"), (int) ((double) objFromJson.get("gameID")));
    }

    /**
     * Calls a new service to carry out the logic of the request
     * @param req native request
     * @return native response
     */
    @Override
    protected Response processNativeRequest(Request req) {
        return new JoinGameService().JoinGame((JoinGameReq) req);
    }
}
