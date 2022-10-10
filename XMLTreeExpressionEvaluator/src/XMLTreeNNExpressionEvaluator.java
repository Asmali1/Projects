import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Mohamed Asmali
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        //NN number is initialized to be the return value of each method/recursive call
        NaturalNumber number = new NaturalNumber1L();
        // if the number value is available, then number is set equal to
        //number is set to the string value of the attribute value of "value"
        if (exp.label().equals("number")) {
            number.setFromString(exp.attributeValue("value"));
        }
        /*
         * if root of the current loop is plus/times, the "if" statements create
         * a new Natural Number "firstValue" created from the constructor of
         * "number." firstNumber is copied from the return value of the
         * recursive call of the first child and the recursive call of the
         * second child is added/multipled. then "number" copies the final
         * object value of "firstValue"
         */
        if (exp.label().equals("plus")) {
            NaturalNumber firstValue = number.newInstance();
            firstValue.copyFrom(evaluate(exp.child(0)));
            firstValue.add(evaluate(exp.child(1)));
            number.copyFrom(firstValue);

        }
        if (exp.label().equals("times")) {
            NaturalNumber firstValue = number.newInstance();
            firstValue.copyFrom(evaluate(exp.child(0)));
            firstValue.multiply(evaluate(exp.child(1)));
            number.copyFrom(firstValue);

        }
        /*
         * if root of current loop is minus/divide,firstValue and secondValue
         * are instantiated to 0 with the same constructor as "number."
         * firstValue copies the return value of the recursive call of the first
         * child, and the secondValue copies the return value of the second
         * child's recursive call. if the secondValue is larger than the
         * firstValue, then program will halt and alert user that this
         * expression will lead to a negative number. If the secondValue is 0,
         * the program will also halt and alert the user via console that
         * dividing by Zero is impossible and causes an error.
         */
        if (exp.label().equals("minus")) {
            NaturalNumber firstValue = number.newInstance();
            NaturalNumber secondValue = number.newInstance();
            firstValue.copyFrom(evaluate(exp.child(0)));
            secondValue.copyFrom(evaluate(exp.child(1)));
            if (secondValue.compareTo(firstValue) > 0) {
                Reporter.fatalErrorToConsole(
                        "This expression leads to a negative number "
                                + "Natural Numbers cannot be negative");
            }

            firstValue.subtract(secondValue);
            number.copyFrom(firstValue);

        }
        if (exp.label().equals("divide")) {
            NaturalNumber firstValue = number.newInstance();
            NaturalNumber secondValue = number.newInstance();
            NaturalNumber zero = number.newInstance();
            firstValue.copyFrom(evaluate(exp.child(0)));
            secondValue.copyFrom(evaluate(exp.child(1)));
            if (secondValue.compareTo(zero) == 0) {
                Reporter.fatalErrorToConsole("Cannot divide by 0");
            }
            firstValue.divide(secondValue);
            number.copyFrom(firstValue);
        }

        return number;
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
        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
