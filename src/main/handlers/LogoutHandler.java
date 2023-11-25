package handlers;

import reqRes.LogoutReq;
import reqRes.Request;
import reqRes.Response;
import services.LogoutService;

public class LogoutHandler extends Handler{
    protected static LogoutHandler instance = new LogoutHandler();

    public static LogoutHandler getInstance(){
        return instance;
    }

    /**
     * Deserializes the spark request
     * Retrieves the auth token from header
     * @param req is a spark request
     * @return native request
     */
    @Override
    protected Request deserializeReq(spark.Request req) {
        return new LogoutReq(req.headers("authorization"));
    }


    /**
     * Calls a new service to carry out the logic of the request
     * @param req native request
     * @return native response
     */
    @Override
    protected Response processNativeRequest(Request req) {
        return new LogoutService().Logout((LogoutReq) req);
    }
}
