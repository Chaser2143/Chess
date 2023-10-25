package handlers;

import com.google.gson.Gson;
import models.AuthToken;
import reqRes.LogoutReq;
import reqRes.Request;
import reqRes.Response;
import services.LogoutService;

import java.util.Map;

public class LogoutHandler extends Handler{
    protected static LogoutHandler instance = new LogoutHandler();

    public static LogoutHandler getInstance(){
        return instance;
    }
    @Override
    protected Request deserializeReq(spark.Request req) {
        return new LogoutReq(req.headers("authorization"));
    }


    @Override
    protected Response processNativeRequest(Request req) {
        return new LogoutService().Logout((LogoutReq) req);
    }
}
