import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program prompts user to enter a number and its square root will be calculated
 * using Newton's iteration. User is promoted to also enter an 'ε' value, which
 * will be used as the relative error of where the guessed value should fall
 * within.
 *
 * @author Mohamed Asmali
 *
 */
public final class Newton3 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton3() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x, double εValue) {
        /*
         * Sets double value 'guess' to be x, as an initial guess
         */
        double guess = x;
        /*
         * Loop will be skipped if the user's value(guess) initialized from 'x'
         * is 0 Loop will continue as long as the absolute value of guess *
         * guess subtracted by x, then divided by x is less than epilson(.0001)
         * squared. the user's 'εValue' is used as the relative error that
         * 'guess' should be within.
         */
        if (guess != 0) {
            while (!(Math.abs((guess * guess) - x) / x < (εValue * εValue))) {
                guess = (guess + (x / guess)) / 2;
            }
        }
        /*
         * Returns end 'guess' value calculated from loop to the main method.
         */
        return guess;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Program prompts user to enter a value for 'ε' to calculate a square
         * root of the number(s) they enter using Newtons iteration. The program
         * will calculate a square root value within the relative error of 'ε.'
         */
        out.print("Enter a value for ε: ");
        double ε = in.nextDouble();
        /*
         * answer is initialized to be the string 'y', meaning "yes." Loop will
         * continue to ask user if they wish to calculate another square root
         * unless they don't enter y Prompts user to enter a number to calculate
         * its square root Number entered by user will be sent to the 'sqrt'
         * method to calculate Loop exits when user enters anything other than
         * 'y'(meaning yes) and program prints "Have a great day!' signaling the
         * program ending
         */
        String answer = "y";
        while (answer.equals("y")) {
            out.print("Enter a number to calcualte square root: ");
            double userNumber = in.nextDouble();
            out.println("The square root of " + userNumber + " is "
                    + sqrt(userNumber, ε));
            out.print("Would you like to calcualte another square root? ");
            answer = in.nextLine();
        }

        out.println("Have a great day!");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
