package reqRes;

/**
 * Serves as the response for the Join Game API
 */
public class JoinGameRes extends Response{

    /**
     * Constructor for the Join Game Response
     */
    public JoinGameRes(){
        super();
    }

    /**
     * Error Join Game Response
     * @param message Error
     */
    public JoinGameRes(String message){
        super(message);
    }
}
