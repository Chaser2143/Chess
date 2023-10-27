package handlers;

import com.google.gson.Gson;
import reqRes.RegisterReq;
import reqRes.Response;
import services.RegisterService;
import spark.Request;

import java.util.Map;

public class RegisterHandler extends Handler{
    protected static RegisterHandler instance = new RegisterHandler();

    public static RegisterHandler getInstance(){
        return instance;
    }

    /**
     * Deserializes the spark request
     * Retrieves the username, password, and email from body
     * @param req is a spark request
     * @return native request
     */
    @Override
    protected reqRes.Request deserializeReq(Request req) {
        var serializer = new Gson();
        var objFromJson = serializer.fromJson(req.body(), Map.class);
        return new RegisterReq((String)objFromJson.get("username"), (String)objFromJson.get("password"), (String)objFromJson.get("email"));
    }

    /**
     * Calls a new service to carry out the logic of the request
     * @param req native request
     * @return native response
     */
    @Override
    protected Response processNativeRequest(reqRes.Request req) {
        return new RegisterService().Register((RegisterReq) req);
    }
}
