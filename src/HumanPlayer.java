import java.util.Scanner;

public class HumanPlayer extends PlayerType
{
    private static int humanNum = 0;

    public HumanPlayer()
    {
        humanNum++;
        setCustomPlayerID();
    }

    @Override
    public int chooseMove(final SquareState[][] board,final Move[] validMoves)
    {
        if(validMoves == null || validMoves.length == 0)
            throw new IllegalArgumentException("Valid moves list cannot be null or empty");

        int totalMoves = validMoves.length;

        int index;
        if (totalMoves == 1)
        {
            String prompt = "Move\u001B[95m 1 \u001B[0mis the only available move. Press ENTER to play the move.";
            String errorMessage = "Please press ENTER only to play the move.";
            InputValidator.readEnter(prompt, errorMessage);
            index = 0;
        }
        else
        {
            String range = String.format("%d - %d", 1, totalMoves);
            index = InputValidator.readInt("Select a move (\u001B[95m" + range + "\u001B[0m): ", 1, totalMoves) - 1;
        }

        System.out.println();

        return index;
    }

    @Override
    public void passTurn()
    {
        String prompt = "No valid moves available. Press ENTER to pass your turn.";
        String errorMessage = "Please press ENTER only to pass the turn.";
        InputValidator.readEnter(prompt, errorMessage);
        System.out.println();
    }

    public void setCustomPlayerID()
    {
        String name = String.format("Player %d", humanNum);

        String prompt ="""
                Do you want to set a custom name?
                1. No
                2. Yes
                
                Select an option:\u00A0""";

        boolean customize = (InputValidator.readInt(prompt, 1, 2) == 2);
        System.out.println();

        if (customize)
        {
            boolean nameConfirmed;
            do
            {
                name = InputValidator.readString("Enter custom name: ", "");
                String proceedPrompt = String.format("""
                                             1. Confirm custom name: %s
                                             2. Re-enter
                        
                                             Select an option:\u00A0""", name);

                nameConfirmed = InputValidator.readInt(proceedPrompt, 1, 2) == 1;
                System.out.println();
            }
            while (!nameConfirmed);
        }

        setPlayerID(name);
    }
}
