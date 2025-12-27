public class HumanPlayer extends PlayerType
{
    private static int humanNum = 0;

    public HumanPlayer()
    {
        humanNum++;
        setCustomPlayerID();
    }

    @Override
    public int chooseMove(final SquareState[][] board,final Move[] moves)
    {
        int totalMoves = moves.length;
        StringBuilder prompt = new StringBuilder("Your options:");
        for(int i = 0; i < totalMoves; i++)
            prompt.append(String.format("\n%s. row: %d col: %d",(i+1),moves[i].row(),moves[i].col()));

        prompt.append(String.format("\n\nSelect a move (%d - %d): " ,1,totalMoves));
        return InputValidator.readInt(prompt.toString(),1,totalMoves) - 1;
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
