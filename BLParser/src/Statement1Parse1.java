import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Joseph Daprano, Mohamed Asmali, Donovan Liao
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer
                .isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";

        //check for valid condition
        Statement localS = s.newInstance();
        tokens.dequeue();
        String condition = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(condition),
                "Violation of: c is a valid condition");
        Condition c = parseCondition(condition);
        String then = tokens.dequeue();
        Reporter.assertElseFatalError(then.equals("THEN"),
                "ERROR: expected THEN, but found " + then);

        //parse block of if statement
        localS.parseBlock(tokens);
        String removed = tokens.dequeue();
        String end = "";
        if (removed.equals("ELSE")) {
            //do if there is else statement
            Statement localS2 = s.newInstance();
            localS2.parseBlock(tokens);
            s.assembleIfElse(c, localS, localS2);
            end = tokens.dequeue();

        } else {
            //do if there is only an if and no else statement
            s.assembleIf(c, localS);
            end = tokens.dequeue();
        }
        Reporter.assertElseFatalError(end.equals("END"),
                "ERROR: expected END, but found: " + end);
        String postIf = tokens.dequeue();
        Reporter.assertElseFatalError(postIf.equals("IF"),
                "ERROR: expected IF, but found: " + postIf);

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {

        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        Statement localS = s.newInstance();
        tokens.dequeue();

        //parse condition
        Condition c = parseCondition(tokens.dequeue());
        String whileDO = tokens.dequeue();
        Reporter.assertElseFatalError(whileDO.equals("DO"),
                "ERROR: expected DO, but found " + whileDO);
        //parse block of while statement
        localS.parseBlock(tokens);
        s.assembleWhile(c, localS);
        String whileEnd = tokens.dequeue();
        Reporter.assertElseFatalError(whileEnd.equals("END"),
                "ERROR: expected END, but found: " + whileEnd);

        String postWhile = tokens.dequeue();
        Reporter.assertElseFatalError(postWhile.equals("WHILE"),
                "ERROR: expected WHILE, but found: " + postWhile);

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0
                && Tokenizer.isIdentifier(tokens.front()) : ""
                        + "Violation of: identifier string is proper prefix of tokens";

        //assemble call
        s.assembleCall(tokens.dequeue());

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        switch (tokens.front()) {
            case "IF": {
                //parse statement if it is if statement
                Statement s = new Statement1();
                parseIf(tokens, s);
                this.transferFrom(s);
                break;
            }
            case "WHILE": {
                //parse statement if it is while statement
                Statement s = new Statement1();
                parseWhile(tokens, s);
                this.transferFrom(s);
                break;
            }
            default: {
                //parse statement if it is call statement
                Statement s = new Statement1();
                parseCall(tokens, s);
                this.transferFrom(s);
                break;
            }
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        this.clear();

        //parse every child of the block
        while (!tokens.front().equals("### END OF INPUT ###")) {
            Statement block = this.newInstance();

            block.parse(tokens);
            this.addToBlock(this.lengthOfBlock(), block);
        }
    }

    /*
     * Main test method -------------------------------------------------------
     */

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
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        //out.print(tokens);
        file.close();
        s.parseBlock(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
