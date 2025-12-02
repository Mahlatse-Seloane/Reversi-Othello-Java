import java.util.Arrays;

public class Board
{
    private SquareState[][] board;
    private int boardSize;

    public void createBoard(int boardSize)
    {
        this.boardSize = boardSize;
        this.board = new SquareState[boardSize][boardSize];

        for(int i = 0; i < this.boardSize; i++)
            Arrays.fill(board[i], SquareState.EMPTY);
    }

    public void setCellContent(int row, int col, SquareState square)
    {
        if(row < 0 || row >= boardSize)
            throw new IndexOutOfBoundsException("Row: " + row + " is out the bounds.");
        if(col < 0 || col >= boardSize)
            throw new IndexOutOfBoundsException("Col: " + col + " is out the bounds.");

        this.board[row][col] = square;
    }

    public SquareState lookAtCellContent(int row, int col)
    {
        if(row < 0 || row >= boardSize)
            throw new IndexOutOfBoundsException("Row: " + row + " is out the bounds.");
        if(col < 0 || col >= boardSize)
            throw new IndexOutOfBoundsException("Col: " + col + " is out the bounds.");

        return this.board[row][col];
    }

    public void printBoard()
    {
        int separatorLength = 5 + (4 * (boardSize - 1));
        String separator = "-".repeat(separatorLength);

        System.out.println();
        for(int i = 0; i < boardSize; i++)
        {
            System.out.println(separator);
            StringBuilder row = new StringBuilder("|");
            for (int j = 0; j < boardSize; j++)
            {
                String cellContent = switch(board[i][j])
                {
                    case SquareState.EMPTY -> "  ";
                    case SquareState.WHITE -> " W";
                    case SquareState.BLACK -> " B";
                };

                row.append(cellContent).append(" |");
            }
            System.out.println(row);
        }

        System.out.println(separator);
    }
}
