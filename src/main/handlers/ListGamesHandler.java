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

    @Override
    protected Request deserializeReq(spark.Request req) {
        return new ListGamesReq((req.headers("authorization")));
    }

    @Override
    protected Response processNativeRequest(Request req) {
        return new ListGamesService().ListGames((ListGamesReq) req);
    }
}
