import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program prompts user to enter a number and its square root will be calculated
 * using Newton's iteration.
 *
 * @author Mohamed Asmali
 *
 */
public final class Newton1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton1() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        /*
         * Sets double value 'guess' to be x, as an initial guess 'epilson' is
         * initialized to be .0001 to represent a relative error of .01% that
         * the guess should be in
         */
        double guess = x;
        double epilson = 0.0001;
        /*
         * Loop will continue as long as the absolute value of guess * guess
         * subtracted by x, then divided by x is less than epilson(.0001)
         * squared.
         */
        while (!(Math.abs((guess * guess) - x) / x < (epilson * epilson))) {
            guess = (guess + (x / guess)) / 2;
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
                    + sqrt(userNumber));
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
