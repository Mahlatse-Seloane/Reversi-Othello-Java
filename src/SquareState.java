public enum SquareState
{
    EMPTY (" "),
    WHITE("W"),
    BLACK("B");

    private final String symbol;

    SquareState(String symbol)
    {
        this.symbol = symbol;
    }

    public String getSymbol()
    {
        return symbol;
    }
}
