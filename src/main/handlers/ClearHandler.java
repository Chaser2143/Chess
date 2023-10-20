package handlers;

import reqRes.Request;
import reqRes.Response;

public class ClearHandler extends Handler{
    protected static ClearHandler instance = new ClearHandler();


    @Override
    public reqRes.Response processSparkRequest(spark.Request req) {
        return null;
    }

    @Override
    protected reqRes.Request deserializeReq(spark.Request req) {
        return null;
    }

    @Override
    protected Response processNativeRequest(Request req) {
        return null;
    }

    @Override
    protected spark.Response serializeRes(Response res) {
        return null;
    }
}
