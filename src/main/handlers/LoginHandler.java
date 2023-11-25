package handlers;

import com.google.gson.Gson;
import reqRes.LoginReq;
import reqRes.Request;
import reqRes.Response;
import services.LoginService;

import java.util.Map;

public class LoginHandler extends Handler{
    protected static LoginHandler instance = new LoginHandler();

    public static LoginHandler getInstance(){
        return instance;
    }

    /**
     * Deserializes the spark request
     * Retrieves the username and password from body
     * @param req is a spark request
     * @return native request
     */
    @Override
    protected Request deserializeReq(spark.Request req) {
        var serializer = new Gson();
        var objFromJson = serializer.fromJson(req.body(), Map.class);
        return new LoginReq((String) objFromJson.get("username"), (String) objFromJson.get("password"));
    }

    /**
     * Calls a new service to carry out the logic of the request
     * @param req native request
     * @return native response
     */
    @Override
    protected Response processNativeRequest(Request req) {
        return new LoginService().Login((LoginReq) req);
    }
}
