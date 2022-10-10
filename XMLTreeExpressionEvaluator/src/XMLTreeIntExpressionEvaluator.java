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
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
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
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        //number is initiated to be the return value each method/recursive call
        int number = 0;
        // if the number value is available, then number is set equal to
        //that string value converted to an int
        if (exp.label().equals("number")) {
            number = Integer.parseInt(exp.attributeValue("value"));
        }
        /*
         * if the root of the current loop is plus, minus,times, or divide, the
         * if statements make a recursive call to check if they have any
         * operands within those operators. . then they are
         * added/subtracted/multiplied/divided if a number is divided by 0, the
         */
        if (exp.label().equals("plus")) {
            number = evaluate(exp.child(0)) + evaluate(exp.child(1));
        }
        if (exp.label().equals("minus")) {
            number += evaluate(exp.child(0)) - evaluate(exp.child(1));
        }
        if (exp.label().equals("times")) {
            number += evaluate(exp.child(0)) * evaluate(exp.child(1));
        }
        /*
         * program will halt and will notify the user about the "Dividing by 0"
         * error via control panel
         */
        if (exp.label().equals("divide")) {
            int divider = evaluate(exp.child(1));
            if (divider == 0) {
                Reporter.fatalErrorToConsole("Cannot Divide by 0");
            }
            number += evaluate(exp.child(0)) / evaluate(exp.child(1));
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
