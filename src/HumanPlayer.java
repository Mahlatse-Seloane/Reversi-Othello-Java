import java.util.Scanner;

public class HumanPlayer extends PlayerType
{
    public HumanPlayer()
    {
        setPlayerID("Human Player");
    }

    @Override
    public int chooseMove(final SquareState[][] board,final Move[] moves)
    {
        int totalMoves = moves.length;
        StringBuilder options = new StringBuilder("Your options:");
        for(int i = 0; i < totalMoves; i++)
            options.append(String.format("\n%s. row: %s col: %s",(i+1),moves[i].row(),moves[i].col()));

        System.out.println(options);
        Scanner scanner = new Scanner(System.in);

        String prompt = "Select a move (" + 1 + "-" + totalMoves + "): ";
        int chosenMove;

        do
        {
            System.out.print(prompt);
            chosenMove = scanner.nextInt();
        }
        while(chosenMove < 1 || chosenMove > totalMoves);

        return chosenMove - 1;
    }
}
