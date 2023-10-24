package handlers;

import com.google.gson.Gson;
import reqRes.Response;

/**
 * Abstract Handler Class
 */
public abstract class Handler {
    protected static Handler instance;

    /**
     * Returns a given instance
     * @return instance
     */
    public static Handler getInstance(){
        return instance;
    }

    /**
     * Processes the given request
     * - Calls method to deserialize Spark Request into Java Request
     * - Passes the native request into the correct handler
     * - Gets a response from the handler
     * - Serializes the response into a Spark Response
     * - Return the Spark Response
     * @return the correct response object
     */
    public String processSparkRequest(spark.Request req){
        //Deserialize Request
        reqRes.Request nativeReq = deserializeReq(req);
        //Process
        reqRes.Response nativeRes = processNativeRequest(nativeReq);
        //Serialize Response & Return
        return serializeRes(nativeRes);
    }

    /**
     * Deserializes a Spark request into a native request
     * @param req is a spark request
     * @return native request
     */
    abstract protected reqRes.Request deserializeReq(spark.Request req);

    /**
     * Calls the correct service to process a native request
     * Retrieves a native response from the service
     * @param req native request
     * @return native response
     */
    abstract protected reqRes.Response processNativeRequest(reqRes.Request req);

    /**
     * Take in a native response
     * Serializes to give spark response
     *
     * @param res is native response
     * @return spark response
     */
    protected String serializeRes(Response res){
        var serializer = new Gson();
        var json = serializer.toJson(res);
        System.out.println("JSON: " + json);
        return json;
    }
}
