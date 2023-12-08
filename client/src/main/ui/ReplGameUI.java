package ui;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ReplGameUI {
    private GameUI gameClient;

    public ReplGameUI(String AuthToken, String UserName, String Team, int GameID) {
        gameClient = new GameUI(AuthToken, UserName, Team, GameID);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = gameClient.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                System.out.print(e.getMessage());
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_LIGHT_GREY + " [IN GAME UI] >>> " + SET_TEXT_COLOR_GREEN);
    }

}