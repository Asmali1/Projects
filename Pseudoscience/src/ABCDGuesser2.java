import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Program uses the Jager Formula combined with the "charming theory" to
 * calculate the best approximation using numbers entered by the user.
 *
 * @author Mohamed Asmali
 *
 */
public final class ABCDGuesser2 {
    /**
     * Default constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     *
     * Uses a serious of for loops to find the appropriate exponential values
     * for w,x,y,z that multiply to have a relative error of a hundredth of a
     * percent, compared to "u."
     *
     * @param u
     *            value to be approximated, entered my user
     *
     * @param w
     *            value entered by the user
     * @param x
     *            value entered by the user
     * @param y
     *            value entered by the user
     * @param z
     *            value entered by the user
     *
     * @param out
     *            the output stream
     * @return the best double approximation
     */
    public static double getBestApproximation(double u, double w, double x,
            double y, double z, SimpleWriter out) {
        //these are the 17 numbers used for the Jager formula.
        final double[] jagerNumbers = { -5.0, -4, -3, -2, -1, -1.0 / 2.0,
                -1.0 / 3.0, -1.0 / 4.0, 0, 1.0 / 4.0, 1.0 / 3.0, 1.0 / 2.0, 1,
                2, 3, 4, 5 };
        //initialized to be the best/closest value to the 'userValue'
        //entered by the user. will change throughout upcoming loop
        double closestEstimate = 0;
        //in to keep track of the best exponential values from the jagerNumber list.
        double closestIndex1 = 0, closestIndex2 = 0, closestIndex3 = 0,
                closestIndex4 = 0;
        double relativeError = 0;
        /*
         * serious of for loops that checks every single combination of a,b,c,d
         * exponents from the jagerNumber list Every single combination is
         * multiplied to see which one gives the lowest relative error and
         * closest value to the user's inputed value If a new
         */
        for (int i = 0; i < jagerNumbers.length; i++) {
            double a = (Math.pow(w, jagerNumbers[i]));
            for (int j = 0; j < jagerNumbers.length; j++) {
                double b = (Math.pow(x, jagerNumbers[j]));
                for (int k = 0; k < jagerNumbers.length; k++) {
                    double c = (Math.pow(y, jagerNumbers[k]));
                    for (int p = 0; p < jagerNumbers.length; p++) {
                        double d = (Math.pow(z, jagerNumbers[p]));
                        double currentEstiamte = a * b * c * d;
                        if (Math.abs((currentEstiamte - u) / u) < Math
                                .abs((closestEstimate - u) / u)) {
                            closestEstimate = currentEstiamte;
                            closestIndex1 = jagerNumbers[i];
                            closestIndex2 = jagerNumbers[j];
                            closestIndex3 = jagerNumbers[k];
                            closestIndex4 = jagerNumbers[p];
                            relativeError = Math.abs((closestEstimate - u) / u);

                        }
                    }
                }
            }
        }

        /*
         * Prints the results for the best exponents of w,x,y,z, best
         * approximation, and the relative error percentage.
         */
        final int percentage = 100;
        out.println("The best exponent for w is: " + closestIndex1);
        out.println("The best exponent for x is: " + closestIndex2);
        out.println("The best exponent for y is: " + closestIndex3);
        out.println("The best exponent for z is: " + closestIndex4);
        out.println(
                "The relative error is: " + relativeError * percentage + "%");
        return closestEstimate;
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        String value;
        double userValue = -1;
        while (userValue < 0) {
            out.print("Enter a positive integer value for your constant : ");
            value = in.nextLine();
            if (FormatChecker.canParseDouble(value)) {
                userValue = Double.parseDouble(value);

            } else {
                out.println("Not a valid number ");
            }
        }
        return userValue;

    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        String userString;
        double userValue = 0;
        while (userValue < 1.0) {
            out.print("Enter a positive integer: ");
            userString = in.nextLine();
            if (FormatChecker.canParseDouble(userString)) {
                userValue = Double.parseDouble(userString);

            } else {
                out.println("Not a valid number ");
            }
        }
        return userValue;
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
         * Call upon the getPositiveDouble and getPositiveDoubleNotOne methods
         * to allow user to enter a double value
         */
        double userValue = getPositiveDouble(in, out);
        double wValue = getPositiveDoubleNotOne(in, out);
        double xValue = getPositiveDoubleNotOne(in, out);
        double yValue = getPositiveDoubleNotOne(in, out);
        double zValue = getPositiveDoubleNotOne(in, out);

        /*
         * loop checks every single combination of a,b,c,d exponents from the
         * jagerNumber list Every single combination is multiplied to see which
         * one gives the lowest relative error and closest value to the user's
         * inputed value If a new
         */
        double bestApproximation = getBestApproximation(userValue, wValue,
                xValue, yValue, zValue, out);
        out.println("The best approximation is: " + bestApproximation);

        /*
         * Prints the results for the best exponents of w,x,y,z, best
         * approximation, and the relative error percentage.
         */

        /*
         * Put your main program code here; it may call myMethod as shown
         */
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
