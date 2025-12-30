import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator
{
    private static final Scanner scanner = new Scanner(System.in);

    private InputValidator() {}

    public static int readInt(final String prompt,final int minRange,final int maxRange)
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

    public static String readString(final String prompt,final String expectedString)
    {
        boolean inputValid = false;
        String line;

        do
        {
            System.out.print(prompt);
            line = scanner.nextLine().trim();

            if (!expectedString.isBlank())
            {
                if (line.equals(expectedString))
                    inputValid = true;
                else
                    System.out.println("Error: Invalid input. Your entry does not match the expected input: " + expectedString +  " \n");
            }
            else
            {
                if (!line.isBlank())
                    inputValid = true;
                else
                    System.out.println("Error: Invalid input. Your entry is blank/empty.\n");
            }
        }
        while (!inputValid);

        return line;
    }

    public static void readEnter(final String prompt, final String ErrorMessage)
    {
        System.out.println();
        boolean inputValid = false;

        System.out.print(prompt);
        do
        {
            String line = scanner.nextLine().trim();
            if (line.isBlank())
                inputValid = true;
            else
                System.out.print("Error: Invalid input. " + ErrorMessage);
        }
        while (!inputValid);
    }
}
