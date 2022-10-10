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
public final class ABCDGuesser1 {
    /**
     * Default constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
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

//these are the 17 numbers used for the Jager formula.
        final double[] jagerNumbers = { -5, -4, -3, -2, -1, -1.0 / 2.0,
                -1.0 / 3.0, -1.0 / 4.0, 0, 1.0 / 4.0, 1.0 / 3.0, .5, 1, 2, 3, 4,
                5 };
        //intiliazed a,b,c,d to be the exponents of w,x,y,z respectively
        double a = 0, b = 0, c = 0, d = 0;
        //intizialized to be the best/closest value to the
        //'userValue' entered by the user. will change throughout upcoming loop
        double closestEstimate = 0;
        //intiailized to keep track of the best exponential
        //values from the jagerNumber list.
        double closestIndex1 = 0, closestIndex2 = 0, closestIndex3 = 0,
                closestIndex4 = 0;
        //calculates relative error using its formula(Absolute value
        //of (measured-estimated)/measured).
        double relativeError = 0;
        /*
         * loop checks every single combination of a,b,c,d exponents from the
         * jagerNumber list Every single combination is multiplied to see which
         * one gives the lowest relative error and closest value to the user's
         * inputed value
         */
        int i = 0;
        while (i < jagerNumbers.length) {
            a = Math.pow(wValue, jagerNumbers[i]);
            int j = 0;
            while (j < jagerNumbers.length) {
                b = Math.pow(xValue, jagerNumbers[j]);
                int n = 0;
                while (n < jagerNumbers.length) {
                    c = Math.pow(yValue, jagerNumbers[n]);
                    int p = 0;
                    while (p < jagerNumbers.length) {
                        d = Math.pow(zValue, jagerNumbers[p]);
                        double currentEstimate = a * b * c * d;
                        if (Math.abs((currentEstimate - userValue)
                                / userValue) < Math
                                        .abs((closestEstimate - userValue)
                                                / userValue)) {
                            closestEstimate = currentEstimate;
                            closestIndex1 = jagerNumbers[i];
                            closestIndex2 = jagerNumbers[j];
                            closestIndex3 = jagerNumbers[n];
                            closestIndex4 = jagerNumbers[p];
                            relativeError = Math.abs(
                                    (closestEstimate - userValue) / userValue);

                        }
                        p++;
                    }
                    n++;

                }
                j++;

            }
            i++;

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
        out.println("The best approximation is: " + closestEstimate);
        out.println(
                "The relative error is: " + relativeError * percentage + "%");

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
