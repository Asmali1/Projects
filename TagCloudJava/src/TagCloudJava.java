import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * This program takes a text file and represents a specified number of words
 * from the file in a tag cloud based on their number of occurrences.
 *
 * @author Joseph Daprano, Donny Liao, Mohamed Asmali
 *
 */
public final class TagCloudJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudJava() {
    }

    /**
     * Maximum font value for tags in tag cloud.
     */
    private static final int FONT_MAX = 48;
    /**
     * Minimum font value for tags in tag cloud.
     */
    private static final int FONT_MIN = 11;

    /**
     * Outputs the "opening" tags in the generated HTML file.
     *
     * @param title
     *            the title of the page
     * @param out
     *            the output stream
     * @updates out.content
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(String title, PrintWriter out)
            throws IOException {
        assert out != null : "Violation of: out is not null";
        out.write("<html>\n\t<head>\n\t<title>" + title
                + "</title>\n<link href=\"http://web.cse.ohio-state.edu/"
                + "software/2231/web-sw2/assignments/projects/tag-cloud-"
                + "generator/data/tagcloud.css\" rel=\"stylesheet\" "
                + "type=\"text/css\">\n" + "<link href=\"tagcloud.css\" "
                + "rel=\"stylesheet\" type=\"text/css\">\n"
                + "</head>\n<body>\n");
    }

    /**
     * Method to acquire valid number of words in tag cloud; valid if number is
     * both positive and less than or equal to the number of unique words in
     * input text.
     *
     * @param words
     *            Queue of unique words in input file
     * @param in
     *            SimpleReader to acquire input values
     * @return valid number of words to have in tag cloud
     *
     */
    private static int getValidInput(java.util.Map<String, Integer> words,
            BufferedReader in) {
        System.out.print("Input number of words to be included in tag cloud: ");
        int n = 0;
        try {
            n = Integer.parseInt(in.readLine());

            while (n < 0 || n > words.size()) {
                if (n < 0) {
                    System.out.println("Must be a positive integer. ");
                    System.out.print(
                            "Input number of words to be included in tag cloud: ");
                    n = Integer.parseInt(in.readLine());
                } else {
                    System.out.println(
                            "Must be less than or equal to number of words in"
                                    + " input file. ");
                    System.out.print(
                            "Input number of words to be included in tag cloud: ");
                    n = Integer.parseInt(in.readLine());
                }
            }
        } catch (IOException e) {
            System.err.println("error reading text from file");
        }
        return n;
    }

    /**
     *
     * Comparator to sort Strings in ABC order.
     *
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return a.compareToIgnoreCase(b);
        }

    }

    /**
     *
     * Comparator to sort Map.Pairs by integer values.
     *
     */
    private static class CountLT implements Comparator<Entry<String, Integer>> {

        @Override
        public int compare(Entry<String, Integer> a, Entry<String, Integer> b) {
            int verdict = 0;
            if (a.getValue() > b.getValue()) {
                verdict = -1;
            } else if (a.getValue() < b.getValue()) {
                verdict = 1;
            }

            return verdict;
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (string of spaces) in the given
     * {@code text} starting at the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separatorSet
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures nextWordOrSeparator = next word in text if starting index is a
     *          non-separator or a string of spaces if starting index is a
     *          separator
     */
    private static String nextWordOrSeparator(String text, int position,
            java.util.Set<Character> separatorSet) {
        //create StringBuilder object
        StringBuilder word = new StringBuilder();
        char c = text.charAt(position);
        int i = position;
        /*
         * if the character at the starting position is a separator, append a
         * space to return string and continue iterating until contacting a non-
         * separator
         */
        if (separatorSet.contains(c)) {
            while (i < text.length() && separatorSet.contains(text.charAt(i))) {
                c = text.charAt(i);
                word.append(" ");
                i++;
            }
            /*
             * if the character at the starting position is not a separator,
             * append characters to the return string until contacting a
             * separator
             */
        } else {
            while (i < text.length()
                    && !separatorSet.contains(text.charAt(i))) {
                c = text.charAt(i);
                word.append(c);
                i++;
            }
        }
        return word.toString();
    }

    /**
     * Returns a Map of each word/count pair in the inputed text document read
     * from {@code input}.
     *
     * @param input
     *            source of strings
     * @return
     * @return Map of terms and counts read from {@code input}
     * @requires input.is_open
     * @ensures input.is_open and countMap = [Map of each term/count pair in
     *          {@code input}]
     */
    private static java.util.Map<String, Integer> wordCountMap(
            BufferedReader input) {
        //assert input.isOpen() : "Violation of: input.is_open"; FIX
        //construct Map of words and counts
        java.util.Map<String, Integer> countMap = new HashMap<>();

        String line;
        try {
            line = input.readLine();
            while (line != null) {
                //get term

                //construct set of separators
                java.util.Set<Character> separators = new HashSet<>();
                String separatorString = " \t\n\r,-.!?[]\";:/()_*";
                for (int i = 0; i < separatorString.length(); i++) {
                    separators.add(separatorString.charAt(i));
                }
                /*
                 * iterate through retrieved line, adding new terms and
                 * iterating counts of existing terms (ignoring spaces retrieved
                 * by nextWordOrSeparator
                 */
                int i = 0;
                while (i < line.length()) {
                    String term = nextWordOrSeparator(line, i, separators);
                    if (!term.contains(" ") && !countMap.containsKey(term)) {
                        countMap.put(term, 1);
                    } else if (!term.contains(" ")) {
                        countMap.replace(term, countMap.get(term) + 1);
                    }
                    i += term.length();
                }
                line = input.readLine();
            }
        } catch (IOException e) {
            System.err.println("error reading text file line");
        }

        return countMap;
    }

    /**
     * This method outputs an HTML formatted line for one tag in the tag cloud,
     * including its corresponding count and font size.
     *
     * @param fSize
     *            the font size for the tag in tag cloud
     * @param count
     *            the number of times the tag appears in the input text document
     * @param word
     *            the tag
     * @param htmlOut
     *            PrintWriter to write to output file
     *
     *
     */
    private static void printTagInCloud(PrintWriter htmlOut, int fSize,
            int count, String word) throws IOException {
        htmlOut.println("<span style=\"cursor:default\" class=\"f" + fSize
                + "\" title = \"count: " + count + "\">" + word + "</span>");
    }

    /**
     * calculates appropriate font size proportion given maximum and minimum
     * count values.
     *
     * @param large
     *            largest word count value
     * @param small
     *            smallest word count value
     * @param count
     *            number of occurrences of word in input file
     *
     * @return the calculated font size
     */

    private static int fontSize(int large, int small, float count) {

        float ts = FONT_MAX - FONT_MIN;
        float zero = 0;
        float size = 0;
        float difference = large - small;
        if (Float.compare(difference, zero) > 0) {
            size = ts * (count - small) / difference + FONT_MIN;
        } else {
            size = FONT_MAX;
        }

        return (int) size;

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        //request names of input and output files
        System.out.print("Input name of text file to count words from: ");

        String inputFile;
        try {
            inputFile = in.readLine();
        } catch (IOException e) {
            System.err.println("error reading user input");
            return;
        }

        //open input file
        BufferedReader inFile;
        try {
            inFile = new BufferedReader(new FileReader(inputFile));
        } catch (IOException e) {
            System.err.println("Error opening input file");
            return;
        }

        System.out.print("Input name for outputted HTML file: ");
        String outputFile;
        try {
            outputFile = in.readLine();
        } catch (IOException e) {
            System.err.println("error reading user input");
            try {
                inFile.close();
            } catch (IOException e2) {
                System.err.println("error closing input file stream");
                return;
            }
            return;
        }

        //create writer for tag cloud HTML page
        PrintWriter htmlOut;
        try {
            htmlOut = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFile)));
        } catch (IOException e) {
            System.err.println("Error opening output file");
            try {
                inFile.close();
            } catch (IOException e2) {
                System.err.println("error closing input file stream");
                return;
            }
            return;
        }

        //get map of words with their corresponding counts
        java.util.Map<String, Integer> data = wordCountMap(inFile);

        //request number of words to be included in tag cloud
        int n = getValidInput(data, in);
        if (n > 0) {
            //comparator for sorting by count
            Comparator<Entry<String, Integer>> cs = new CountLT();

            //create queue to contain specified number of words in ABC order
            java.util.Queue<Entry<String, Integer>> ordered = new PriorityQueue<>(
                    cs);

            //add sorted values to queue
            for (Entry<String, Integer> x : data.entrySet()) {
                ordered.add(x);
            }

            //calculate font proportion
            //MAKE SURE NO DIVIDE BY ZERO
            int large = ordered.peek().getValue();
            int small = 0;
            for (Entry<String, Integer> x : ordered) {
                small = x.getValue();
            }

            //comparator for sorting abc order
            Comparator<String> stringComparator = new StringLT();

            java.util.Queue<String> last = new PriorityQueue<>(n,
                    stringComparator);

            for (int j = 0; j < n; j++) {
                last.add(ordered.poll().getKey());
            }

            //output opening tags for tag cloud HTML file
            try {
                outputHeader("Top " + n + " words in " + inputFile, htmlOut);
            } catch (IOException e) {
                System.err.println("error reading text and writing");
                return;
            }
            htmlOut.print("<h2>Top " + n + " words in " + inputFile
                    + "</h2>\n<hr>\n<div class=\"cdiv\">\n\t<p class=\"cbox\">\n");

            //print tag cloud
            while (last.size() > 0) {
                String word = last.remove();
                int size = fontSize(large, small, data.get(word));
                try {
                    printTagInCloud(htmlOut, size, data.get(word), word);
                } catch (IOException e) {
                    System.err.println("error printing to file");
                    return;
                }
            }
        } else {
            //output opening tags for tag cloud HTML file with 0 tags
            try {
                outputHeader("Top 0 words in " + inputFile, htmlOut);
            } catch (IOException e) {
                System.err.println("error printing to file");
                return;
            }
            htmlOut.print("<h2>Top 0 words in " + inputFile
                    + "</h2>\n<hr>\n<div class=\"cdiv\">\n\t<p class=\"cbox\">\n");
        }
        //print ending HTML tags
        htmlOut.println("</p>\r\n" + "</div>\r\n" + "</body>\r\n" + "</html>");

        /*
         * Close input and output streams
         */
        try {
            inFile.close();
        } catch (IOException e) {
            System.err.println("error closing input file stream");
            return;
        }

        htmlOut.close();

    }

}
