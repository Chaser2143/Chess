package ui;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run() {
        System.out.println("Welcome to 240 Chess. Type Help to get started.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                System.out.print(e.getMessage());
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        if(client.state == State.SIGNEDOUT){
            System.out.print("\n" + SET_TEXT_COLOR_LIGHT_GREY + " [LOGGED_OUT] >>> " + SET_TEXT_COLOR_GREEN);
        }
        else{
            System.out.print("\n" + SET_TEXT_COLOR_LIGHT_GREY + " [LOGGED_IN] >>> " + SET_TEXT_COLOR_GREEN);
        }
    }

}