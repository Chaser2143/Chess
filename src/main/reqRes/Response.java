package reqRes;

/**
 * Base Response Class
 * Used for inheritance, holds no methods
 * Holds all constant Failure Responses
 */
public class Response {
    String message;
    String FourHundred = "Error: bad request";
    String FourOOne = "Error: unauthorized";
    String FourOThree = "Error: already taken";
    String FiveHundred = "Error: ";

    /**
     * Empty Constructor for successful responses
     */
    public Response(){}

    /**
     * Constructor with String for Error Responses
     */
    public Response(String message){
        this.message = message;
    }
}
