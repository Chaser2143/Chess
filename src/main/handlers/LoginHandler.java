package handlers;

import com.google.gson.Gson;
import reqRes.LoginReq;
import reqRes.RegisterReq;
import reqRes.Request;
import reqRes.Response;
import services.LoginService;
import services.RegisterService;

import java.util.Map;

public class LoginHandler extends Handler{
    protected static LoginHandler instance = new LoginHandler();

    public static LoginHandler getInstance(){
        return instance;
    }
    @Override
    protected Request deserializeReq(spark.Request req) {
        var serializer = new Gson();
        var objFromJson = serializer.fromJson(req.body(), Map.class);
        return new LoginReq((String) objFromJson.get("username"), (String) objFromJson.get("password"));
    }

    @Override
    protected Response processNativeRequest(Request req) {
        return new LoginService().Login((LoginReq) req);
    }
}
