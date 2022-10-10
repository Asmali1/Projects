import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber4;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Compute integer power with interval halving and test it.
 *
 * @author Put your name here
 *
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        /*
         * lowEnough and tooHigh are new objects with the same implentation as
         * "n". tooHigh is copied from n and incremented by 1 to be the "max"
         * interval
         */
        NaturalNumber lowEnough = n.newInstance();
        NaturalNumber tooHigh = n.newInstance();
        tooHigh.copyFrom(n);
        tooHigh.increment();
        /*
         * two, one, and guessCopy are new objects with the same implentation
         * "n". two is set to be the value 2 using the int value 2. one is set
         * to be 1 using the int value 1. guessCopy is copied from "n"
         */
        NaturalNumber two = n.newInstance();
        two.setFromInt(2);
        NaturalNumber one = n.newInstance();
        one.setFromInt(1);
        NaturalNumber guessCopy = n.newInstance();
        guessCopy.copyFrom(n);
        /*
         * tooHigh and low enough are added to n then divided by 2, to be the
         * first "mid-point" of the first interval halving value. tooHigh is
         * subtracted by lowEnough to be compared to 1, the value tooHigh gets
         * added by lowEnough later in the code to revert back to its original
         * value
         */
        n.add(tooHigh);
        n.add(lowEnough);
        n.divide(two);
        tooHigh.subtract(lowEnough);
        /*
         * As long as tooHigh is greater than 1, this while loops will continue.
         * tooHigh reverts back to its original value by addign back lowEnough.
         * copyN is initliazed to be a new object with the same implementation
         * as "n". It is used to compare the original value of n(guessCopy).
         * copyN copies N and is squared. if guessCopy(original value of n) is
         * less than copyN, tooHigh copies the n value and becomes the new max
         * interval, else lowEnough copies that value and becomes the new
         * minimum interval
         */

        /*
         * lowEnough is added to tooHigh, then copied by n and divided by 2 to
         * get the new midpoint value. lowEnough is subtracted from tooHigh to
         * get tooHigh's original value, then subtracted again to get the new
         * midPoint
         */

        while (tooHigh.compareTo(one) > 0) {
            tooHigh.add(lowEnough);
            NaturalNumber copyN = n.newInstance();
            copyN.copyFrom(n);
            copyN.power(r);
            if (guessCopy.compareTo(copyN) < 0) {
                tooHigh.copyFrom(n);

            } else {
                lowEnough.copyFrom(n);
            }
            tooHigh.add(lowEnough);
            n.copyFrom(tooHigh);
            n.divide(two);
            tooHigh.subtract(lowEnough);
            tooHigh.subtract(lowEnough);

        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final String[] numbers = { "0", "1", "13", "1024", "189943527", "0",
                "1", "13", "4096", "189943527", "0", "1", "13", "1024",
                "189943527", "82", "82", "82", "82", "82", "9", "27", "81",
                "243", "143489073", "2147483647", "2147483648",
                "9223372036854775807", "9223372036854775808",
                "618970019642690137449562111",
                "162259276829213363391578010288127",
                "170141183460469231731687303715884105727" };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15,
                2, 3, 4, 5, 15, 2, 3, 4, 5, 15, 2, 2, 3, 3, 4, 5, 6 };
        final String[] results = { "0", "1", "3", "32", "13782", "0", "1", "2",
                "16", "574", "0", "1", "1", "1", "3", "9", "4", "3", "2", "1",
                "3", "3", "3", "3", "3", "46340", "46340", "2097151", "2097152",
                "4987896", "2767208", "2353973" };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber4(numbers[i]);
            NaturalNumber r = new NaturalNumber4(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i]
                        + ", " + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root("
                        + numbers[i] + ", " + roots[i] + ") expected <"
                        + results[i] + "> but was <" + n + ">");
            }
        }

        out.close();
    }

}
