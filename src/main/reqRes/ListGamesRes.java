package reqRes;

import models.Game;

import java.util.ArrayList;

/**
 * Serves as the Response for List Games API
 */
public class ListGamesRes extends Response{
    private ArrayList<Game> games;

    /**
     * Instantiates List Games Response
     */
    public ListGamesRes(){
        super();
        games = new ArrayList<>();
    }

    /**
     * Adds a game to the Response List
     * @param game is game to add
     */
    public void addGame(Game game){

    }
}
