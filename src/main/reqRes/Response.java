package reqRes;

/**
 * Base Response Class
 * Used for inheritance, holds no methods
 * Holds all constant Failure Responses
 */
public class Response {
    public static String message;
    public static String FourHundred = "Error: bad request";
    public static String FourOOne = "Error: unauthorized";
    public static String FourOThree = "Error: already taken";
    public static String FiveHundred = "Error: ";

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

    /**
     * Gets the Response Message
     * @return string
     */
    public String getMessage() {
        return message;
    }
}
