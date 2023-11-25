package handlers;

import reqRes.Request;
import reqRes.Response;
import services.ClearService;

public class ClearHandler extends Handler{
    protected static ClearHandler instance = new ClearHandler();

    public static ClearHandler getInstance(){
        return instance;
    }

    /**
     * Because the clear request doesn't have anything in it, we're just going to return an empty native request
     * Keeping this function for consistency
     * @param req is a spark request
     * @return native request
     */
    @Override
    protected reqRes.Request deserializeReq(spark.Request req) {
        return new reqRes.Request();
    }

    /**
     * Calls a new service to carry out the logic of the request
     * We didn't pass anything down as it's an empty request...
     * Keeping that for consistency though
     * @param req native request
     * @return native response
     */
    @Override
    protected Response processNativeRequest(Request req) {
        return new ClearService().clear();
    }
}
