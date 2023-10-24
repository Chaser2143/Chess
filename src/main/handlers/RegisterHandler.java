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

    @Override
    protected reqRes.Request deserializeReq(Request req) {
        var serializer = new Gson();
        var objFromJson = serializer.fromJson(req.body(), Map.class);
        return new RegisterReq(objFromJson.get("username").toString(), objFromJson.get("password").toString(), objFromJson.get("Email").toString());
    }

    @Override
    protected Response processNativeRequest(reqRes.Request req) {
        return new RegisterService().Register((RegisterReq) req);
    }
}
