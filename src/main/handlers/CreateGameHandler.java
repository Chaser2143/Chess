package handlers;

import com.google.gson.Gson;
import reqRes.CreateGameReq;
import reqRes.LoginReq;
import reqRes.Request;
import reqRes.Response;
import services.CreateGameService;

import java.util.Map;


public class CreateGameHandler extends Handler{
    protected static CreateGameHandler instance = new CreateGameHandler();

    public static CreateGameHandler getInstance(){
        return instance;
    }

    /**
     * Deserializes the spark request
     * Retrieves Auth from header and gameName from body
     * @param req is a spark request
     * @return native request
     */
    @Override
    protected Request deserializeReq(spark.Request req) {
        var serializer = new Gson();
        var objFromJson = serializer.fromJson(req.body(), Map.class);
        return new CreateGameReq(req.headers("authorization"), (String) objFromJson.get("gameName"));
    }

    /**
     * Calls a new service to carry out the logic of the request
     * @param req native request
     * @return native response
     */
    @Override
    protected Response processNativeRequest(Request req) {
        return new CreateGameService().CreateGame((CreateGameReq) req);
    }
}
