public class Reversi
{
    Reversi(){}

    public void play()
    {
        System.out.println(ConsoleColours.BRIGHT_WHITE);
        GameLogger.logGameTitle("REVERSI (OTHELLO)");

        String description = """
                Reversi is a two-player strategy game played on an 8x8 board.
                On each turn, players place a piece to capture opponent tiles
                by trapping them between their own pieces.
                
                Control the board. Capture the most tiles. Win the game!""";

        GameLogger.logGameDescription(description);

        boolean playAgain;
        String prompt = """
                          Do you want to play again?
                          1. Yes
                          2. No
                         
                          Select an option (1 - 2):\u00A0""";

        int modeSelection = gameModeSelection();

        PlayerType[] players = determinePlayers(modeSelection);
        GameManager manager = new GameManager();
        do
        {

            int boardSize = enterBoardSize();
            manager.simulateSingleGame(boardSize, players[0], players[1]);

            GameLogger.logHeader("NEW GAME");
            playAgain = (InputValidator.readInt(prompt, 1, 2) == 1);
        }
        while(playAgain);
    }

    private int gameModeSelection()
    {
        GameLogger.logHeader("GAME MODES");

        String modePrompt = """
                          Choose the game mode:
                          1. AI v AI
                          2. Human v AI
                          3. Human v Human
                         
                          Select an option (1 - 3):\u00A0""";

        int selection = InputValidator.readInt(modePrompt, 1, 3);
        System.out.println();

        return selection;
    }

    private int enterBoardSize()
    {
        GameLogger.logHeader("BOARD SIZE SELECTION");

        String boardSizePrompt = """
                             What size board would you like to play on?
                             1. 4 x 4
                             2. 6 x 6
                             3. 8 x 8
                             4. 10 x 10
                             
                             Select an option (1 - 4):\u00A0""";

        int selection = InputValidator.readInt(boardSizePrompt, 1, 4);
        System.out.println();

        return (4 + 2 * (selection - 1));
    }

    private PlayerType[] determinePlayers(final int selection)
    {
        PlayerType[] players = new PlayerType[2];
        if(selection == 1)
        {
            GameLogger.logHeader("AI VS AI SETUP");

            String AISelectionPrompt = """
                                    Which type of AI do you want to see?
                                    1. Random AI vs Random AI
                                    2. Selective AI vs Selective AI
                                    3. Random AI vs Selective AI
            
                                    Select an option (1-3):\u00A0""";

            int AISelection = InputValidator.readInt(AISelectionPrompt, 1, 3);
            players = switch (AISelection)
            {
                case 1 -> new PlayerType[] {new RandomAI(), new RandomAI()};
                case 2 -> new PlayerType[] {new SelectiveAI(), new SelectiveAI()};
                case 3 -> new PlayerType[] {new RandomAI(), new SelectiveAI()};
                default -> throw new IllegalStateException("Unexpected value: " + AISelection);
            };
            System.out.println();
        }
        else
        {
            players[0] = new HumanPlayer();
            if (selection == 2)
            {
                GameLogger.logHeader("HUMAN VS AI SETUP");

                String AISelectionPrompt = """
                        Which type of AI do you want to challenge?
                        1. Random AI
                        2. Selective AI
                        
                        Select an option (1-2):\u00A0""";

                int AISelection = InputValidator.readInt(AISelectionPrompt, 1, 2);
                System.out.println();

                players[1] = (AISelection == 1) ? new RandomAI() : new SelectiveAI();
            }
            else if (selection == 3)
            {
                players[1] = new HumanPlayer();
            }
        }

        return players;
    }
}
