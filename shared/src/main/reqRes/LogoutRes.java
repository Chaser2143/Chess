package reqRes;

/**
 * Represents the logout response
 */
public class LogoutRes extends Response{

    /**
     * Instantiates a new response
     */
    public LogoutRes(){
        super();
    }
    public LogoutRes(String Error){
        super(Error);
    }
}
