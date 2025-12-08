import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator
{
    private static final Scanner scanner = new Scanner(System.in);

    private InputValidator() {}

    public static int ReadInt(final String prompt,final int minRange,final int maxRange)
    {
        boolean inputValid = false;
        int tempInput = 0;

        System.out.println();
        System.out.print(prompt);
        do
        {
            try
            {
                tempInput = scanner.nextInt();
                if (tempInput >= minRange && tempInput <= maxRange)
                    inputValid = true;
                else
                    System.out.printf("Error: Input out of range. Please enter a number (%d-%d): ", minRange, maxRange);
            }
            catch (InputMismatchException e)
            {
                System.out.printf("Error: Invalid input. Please enter a whole number (%d - %d): ",minRange,maxRange);
            }
            catch (Exception e)
            {
                System.out.println("\nSOMETHING WENT WRONG");
            }
            finally
            {
                scanner.nextLine(); // clear invalid input
            }
        }
        while (!inputValid);

        return tempInput;
    }
}
