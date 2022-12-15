import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;

/**
 * This program takes a text file and represents a specified number of words
 * from the file in a tag cloud based on their number of occurrences.
 *
 * @author Joseph Daprano, Donny Liao, Mohamed Asmali
 *
 */
public final class TagCloudGenerator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
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
    private static void outputHeader(String title, SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        //Print out HTML formatted header
        out.println("<html>\n\t<head>\n\t<title>" + title
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
     * @param out
     *            SimpleWriter to output error messages
     * @param in
     *            SimpleReader to acquire input values
     * @return valid number of words to have in tag cloud
     *
     */
    private static int getValidInput(Map<String, Integer> words,
            SimpleWriter out, SimpleReader in) {
        out.print("Input number of words to be included in tag cloud: ");
        int n = in.nextInteger();
        while (n < 0 || n > words.size()) {
            if (n < 0) {
                out.println("Must be a positive integer. ");
                out.print(
                        "Input number of words to be included in tag cloud: ");
                n = in.nextInteger();
            } else {
                out.println(
                        "Must be less than or equal to number of words in input file. ");
                out.print(
                        "Input number of words to be included in tag cloud: ");
                n = in.nextInteger();
            }
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
    private static class CountLT
            implements Comparator<Map.Pair<String, Integer>> {

        @Override
        public int compare(Map.Pair<String, Integer> a,
                Map.Pair<String, Integer> b) {
            int verdict = 0;
            if (a.value() > b.value()) {
                verdict = -1;
            } else if (a.value() < b.value()) {
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
            Set<Character> separatorSet) {
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
    private static Map<String, Integer> wordCountMap(SimpleReader input) {
        assert input.isOpen() : "Violation of: input.is_open";
        //construct Map of words and counts
        Map<String, Integer> countMap = new Map1L<>();
        while (!input.atEOS()) {
            //get term
            String line = input.nextLine();
            //construct set of separators
            Set<Character> separators = new Set1L<>();
            String separatorString = " \t\n\r,-.!?[]\";:/()_*";
            for (int i = 0; i < separatorString.length(); i++) {
                separators.add(separatorString.charAt(i));
            }
            /*
             * iterate through retrieved line, adding new terms and iterating
             * counts of existing terms (ignoring spaces retrieved by
             * nextWordOrSeparator
             */
            int i = 0;
            while (i < line.length()) {
                String term = nextWordOrSeparator(line, i, separators);
                if (!term.contains(" ") && !countMap.hasKey(term)) {
                    countMap.add(term, 1);
                } else if (!term.contains(" ")) {
                    countMap.replaceValue(term, countMap.value(term) + 1);
                }
                i += term.length();
            }
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
     * @param out
     *            SimpleWriter to write to output file
     *
     *
     */
    private static void printTagInCloud(SimpleWriter out, int fSize, int count,
            String word) {
        out.println("<span style=\"cursor:default\" class=\"f" + fSize
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
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //request names of input and output files
        out.print("Input name of text file to count words from: ");
        String inputFile = in.nextLine();
        SimpleReader inFile = new SimpleReader1L(inputFile);
        out.print("Input name for outputted HTML file: ");
        String outputFile = in.nextLine();

        //create writer for tag cloud HTML page
        SimpleWriter htmlOut = new SimpleWriter1L(outputFile);

        //get map of words with their corresponding counts
        Map<String, Integer> data = wordCountMap(inFile);

        //request number of words to be included in tag cloud
        int n = getValidInput(data, out, in);
        if (n > 0) {
            //create queue to contain specified number of words in ABC order
            Queue<String> ordered = new Queue1L<>();

            //comparator for sorting by count
            Comparator<Map.Pair<String, Integer>> cs = new CountLT();

            //create sorting machine to sort words by count
            SortingMachine<Map.Pair<String, Integer>> cSort = new SortingMachine2<>(
                    cs);

            //add entries to Sort Machine
            for (Map.Pair<String, Integer> x : data) {
                cSort.add(x);
            }

            cSort.changeToExtractionMode();

            //add sorted values to queue
            for (int j = 0; j < n; j++) {
                ordered.enqueue(cSort.removeFirst().key());
            }

            //calculate font proportion
            //MAKE SURE NO DIVIDE BY ZERO
            int large = data.value(ordered.front());
            int small = 0;
            for (String x : ordered) {
                small = data.value(x);
            }

            //comparator for sorting abc order
            Comparator<String> stringComparator = new StringLT();
            SortingMachine<String> sSort = new SortingMachine2<>(
                    stringComparator);
            while (ordered.length() > 0) {
                sSort.add(ordered.dequeue());
            }

            //output opening tags for tag cloud HTML file
            outputHeader("Top " + n + " words in " + inputFile, htmlOut);
            htmlOut.print("<h2>Top " + n + " words in " + inputFile
                    + "</h2>\n<hr>\n<div class=\"cdiv\">\n\t<p class=\"cbox\">\n");

            //sort queue by abc order
            sSort.changeToExtractionMode();
            //print tag cloud
            while (sSort.size() > 0) {
                String word = sSort.removeFirst();
                int size = fontSize(large, small, data.value(word));
                printTagInCloud(htmlOut, size, data.value(word), word);
            }
        } else {
            //output opening tags for tag cloud HTML file with 0 tags
            outputHeader("Top 0 words in " + inputFile, htmlOut);
            htmlOut.print("<h2>Top 0 words in " + inputFile
                    + "</h2>\n<hr>\n<div class=\"cdiv\">\n\t<p class=\"cbox\">\n");
        }
        //print ending HTML tags
        htmlOut.println("</p>\r\n" + "</div>\r\n" + "</body>\r\n" + "</html>");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
        inFile.close();
        htmlOut.close();
    }

}
