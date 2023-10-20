package handlers;

import spark.Request;
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
    abstract public reqRes.Response processSparkRequest(spark.Request req);

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
     * @param res is native response
     * @return spark response
     */
    abstract protected  spark.Response serializeRes(reqRes.Response res);
}
