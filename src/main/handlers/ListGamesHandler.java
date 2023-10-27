package handlers;

import reqRes.ListGamesReq;
import reqRes.Request;
import reqRes.Response;
import services.ListGamesService;

public class ListGamesHandler extends Handler{
    protected static ListGamesHandler instance = new ListGamesHandler();

    public static ListGamesHandler getInstance(){
        return instance;
    }

    /**
     * Deserializes the spark request
     * Retrieves Auth from header
     * @param req is a spark request
     * @return native request
     */
    @Override
    protected Request deserializeReq(spark.Request req) {
        return new ListGamesReq((req.headers("authorization")));
    }

    /**
     * Calls a new service to carry out the logic of the request
     * @param req native request
     * @return native response
     */
    @Override
    protected Response processNativeRequest(Request req) {
        return new ListGamesService().ListGames((ListGamesReq) req);
    }
}
