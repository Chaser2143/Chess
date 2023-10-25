package handlers;

import com.google.gson.Gson;
import reqRes.Response;
import spark.*;

import java.util.Objects;

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
    public spark.Response processSparkRequest(spark.Request req, spark.Response res){
        //Deserialize Request
        reqRes.Request nativeReq = deserializeReq(req);
        //Process
        reqRes.Response nativeRes = processNativeRequest(nativeReq);
        //Serialize Response & Return
        return serializeRes(nativeRes, res);
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
    protected spark.Response serializeRes(Response res, spark.Response sRes){
        var serializer = new Gson();

        //Set the response status
        if(Objects.equals(res.getMessage(), Response.FourHundred)){
            sRes.status(400);
        } else if (Objects.equals(res.getMessage(), Response.FourOOne)) {
            sRes.status(401);
        } else if (Objects.equals(res.getMessage(), Response.FourOThree)) {
            sRes.status(403);
        } else if (res.getMessage() != null) {
            sRes.status(500);
        } else{
            sRes.status(200);
        }

        var json = serializer.toJson(res);
        System.out.println("JSON: " + json);
        sRes.body(json);
        return sRes;
    }
}
