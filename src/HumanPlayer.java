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

        String prompt;
        int index = 0;

        if (totalMoves == 1)
        {
            System.out.println();
            boolean inputValid = false;

            do
            {
                System.out.print("Move\u001B[95m 1 \u001B[0mis the only available move. Press ENTER to play the move.");
                Scanner scanner = new Scanner(System.in);

                String line = scanner.nextLine().trim();
                if (line.isBlank())
                    inputValid = true;
                else
                    System.out.println("Error: Invalid input. Press ENTER without typing anything.\n");

            }
            while (!inputValid);
        }
        else
        {
            String range = String.format("%d - %d", 1, totalMoves);
            index = InputValidator.readInt("Select a move (\u001B[95m" + range + "\u001B[0m): ", 1, totalMoves) - 1;
        }

        System.out.println();

        return index;
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
