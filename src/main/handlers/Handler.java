package handlers;

public class Handler {
    protected static Handler instance;
    public Handler(){

    }

    /**
     * Returns a given instance
     * @return instance
     */
    public static Handler getInstance(){
        return instance;
    }

}
