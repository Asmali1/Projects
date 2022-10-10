import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program reads a file, reads every word. Then code print's a table to an html
 * file with the word's amount of occurrences in a file.
 *
 * @author Mohamed Asmali
 */
public final class WordCounter {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private WordCounter() {
        // no code needed here
    }

    /**
     * String of separators.
     */
    private static final String WORDSEPERATORS = "., ()-_?/!@#$%^&*\t1234567890:"
            + ";[]{}+=~`><";

    /**
     * Used to compare Strings in lexicographic order.
     */

    private static class StringLT implements Comparator<String> {
        /**
         * Compares parameters o1 and o2. Returns a positive if o1 is greater,
         * negative if o1 is smaller, or 0 indicating they're equal
         *
         * @param o1
         *            first string parameter
         * @param o2
         *            second string parameter
         * @returns positive,negative, or 0 value
         */
        @Override
        public int compare(String o1, String o2) {
            /*
             * string o1 and o2 are converted to lower case and that new object
             * is attached to o1LowerCase o2LowerCase respectively. then both
             * are compared to see which one comes first.
             */
            String o1LowerCase = o1.toLowerCase();
            String o2LowerCase = o2.toLowerCase();

            return o1LowerCase.compareTo(o2LowerCase);

        }
    }

    /**
     * Outputs the closing tags in the generated HTML file.
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content=#out.content* HTML closing tags
     */
    private static void outputFooter(SimpleWriter out) {
        /*
         * generates the closing tags of the generated HTML file: closes the
         * <table>, <body>, and <html> tag.
         */
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Outputs the definition of a given term in the generated HTML file.
     *
     * @param wordList
     *            list of terms from user's file
     *
     * @param wordList2
     *            list of duplicate terms from user's file
     * @param out
     *            the output stream
     *
     * @updates out.content
     * @requires out.is_open
     * @ensures out.content=#out.content* wordList and their counts, and
     *          appropriate HTML tags
     */
    private static void outputWordAndCount(Queue<String> wordList,
            Queue<String> wordList2, SimpleWriter out) {
        /*
         * initialized to keep track of words and corresponding number of
         * occurrences
         */

        Map<String, NaturalNumber> mapOfWords = new Map1L<>();
        /*
         * adds every single String value from wordList to map, along with a
         * starting occurrence value of 0
         */
        for (String x : wordList) {
            mapOfWords.add(x, new NaturalNumber1L());
        }
        /*
         * Counts the occurring times a word shows up in the user's file by
         * checking how many duplicates are in wordList2.
         */
        for (String x : wordList2) {
            if (mapOfWords.hasKey(x)) {
                mapOfWords.value(x).increment();

            }
        }
        /*
         * Cycles through every String value in wordList and prints it out in a
         * row along with the amount of times it shows up in the user's file
         */
        for (String x : wordList) {
            out.println("<tr>");
            out.println("<td>" + x + "</td>");
            out.println("<td>" + mapOfWords.value(x) + "</td>");
            out.println("</tr>");
        }

    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires <pre>
     * {@code 0 <= position < |text|}
     * </pre>
     * @ensures <pre>
     * {@code nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)}
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        char first = text.charAt(position);
        StringBuilder wordCreated = new StringBuilder();
        int i = position + 1;
        wordCreated.append(first);
        if (!separators.contains(first)) {
            while (i < text.length() && !separators.contains(first)) {
                first = text.charAt(i);
                if (!separators.contains(first)) {
                    StringBuilder concat = new StringBuilder();
                    concat.append(first);
                    wordCreated.append(concat);

                }
                i++;
            }
        } else {
            while (i < text.length() && separators.contains(first)) {
                first = text.charAt(i);
                if (separators.contains(first)) {
                    StringBuilder concat = new StringBuilder();
                    concat.append(first);
                    wordCreated.append(concat);

                }
                i++;
            }
        }
        return wordCreated.toString();
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces {@code strSet}
     * @ensures <pre>
     * {@code strSet = entries(str)}
     * </pre>
     */
    private static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";

        /*
         * Loop through string and add every unique character to the set s.
         */
        for (int i = 0; i < str.length(); i++) {
            if (!strSet.contains(str.charAt(i))) {
                strSet.add(str.charAt(i));
            }
        }
    }

    /**
     * Gets all the terms in the fileData input stream and adds in to 2
     * QueueLists then sorts it.
     *
     * @param wordList
     *            list of terms that are alphabetized
     * @param wordList2
     *            all terms from fileData are enqueued into this list(so
     *            duplicates exist to be counted)
     * @param fileData
     *            user text file with terms and definitions
     * @updates termsList
     * @ensures termsList=#termsList contains only terms and is sorted by
     *          alphabetical order
     */
    private static void getList(Queue<String> wordList, Queue<String> wordList2,
            SimpleReader fileData) {
        /*
         * Every line in fileData is checked
         */
        Set<Character> seps = new Set1L<>();
        generateElements(WORDSEPERATORS, seps);
        Queue<String> linesFromFile = new Queue1L<>();
        /*
         * adds each line in the user's file to the queue
         */
        while (!fileData.atEOS()) {
            String tempLine = fileData.nextLine();
            linesFromFile.enqueue(tempLine);

        }
        /*
         * loop continues until the every line from linesFromFile is dequeued
         */
        while (linesFromFile.length() > 0) {
            String newLine = linesFromFile.dequeue();
            //keeps track of the position in linesFromFile
            int positionofLine = 0;
            while (positionofLine < newLine.length()) {
                /*
                 * creates a string from the current string in the current line
                 * until a separator is found
                 */
                String currentWord = nextWordOrSeparator(newLine,
                        positionofLine, seps);
                positionofLine = positionofLine + currentWord.length();
                // Remove any separators from list
                if (currentWord.length() > 0
                        && !seps.contains(currentWord.charAt(0))) {
                    boolean contains = false;
                    for (String x : wordList) {
                        /*
                         * if a duplicate is found, then the current string
                         * value for "currentWord" is not added.
                         */
                        if (currentWord.equals(x)) {
                            contains = true;
                        }
                    }
                    //adds duplicates
                    wordList2.enqueue(currentWord);
                    //prevents duplicates from being enqueued.
                    if (!contains) {
                        wordList.enqueue(currentWord);
                    }
                }
            }
        }
        /**
         * Creates a comparator to alphabetize the Queue "wordList"
         */
        Comparator<String> sorter = new StringLT();
        wordList.sort(sorter);

    }

    /**
     * outputs "opening" tags in the generated HTML file.
     *
     * @param userInput
     *            the title/header of the HTML file given by user
     * @param fileOut
     *            the output stream
     * @updates out.content
     * @ensures out.content=#out.content*HTML opening tags
     *
     */
    private static void outputHeader(SimpleWriter fileOut, String userInput) {
        /*
         * Opens up the html file with the header tags.
         */
        fileOut.println("<html>");
        fileOut.println("<style>");
        fileOut.println("table, th, td {");
        fileOut.println("border:1px solid black;");
        fileOut.println("}");
        fileOut.println("</style>");
        fileOut.println("<head>");
        fileOut.println("<title>Words Counted in " + userInput + "</title>");
        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("<h3>Words Counted in " + userInput + "</h3>");
        /*
         * creates the horizontal thin lines
         */
        fileOut.println("<hr class=\"new1\">");
        fileOut.println("<table style=\"width:10%\">");
        fileOut.println("<tr>");
        fileOut.println("<th>Words</th>");
        fileOut.println("<th>Counts</th>");
        fileOut.println("</tr>");

    }

    /**
     * Processes information in a given text file by the user, converting it to
     * an input stream to be read, and an HTML file is created using the name of
     * the file.
     *
     * @param userInput
     *            User's text file
     * @param outputFile
     *            user's inputed folder location
     * @requires folderLocation is a valid folder and termsFile is a valid text
     *           file with terms and definitions
     * @ensures html files are generated in the user's inputed folder
     */
    private static void processFile(String userInput, String outputFile) {
        SimpleWriter fileOut = new SimpleWriter1L(outputFile);
        SimpleReader fileData = new SimpleReader1L(userInput);
        //queue to hold non-duplicates
        Queue<String> wordList = new Queue1L<>();
        //queue to hold duplicates
        Queue<String> wordList2 = new Queue1L<>();
        //creates a list of duplicates and non-duplicates.
        //outputs header
        outputHeader(fileOut, userInput);
        getList(wordList, wordList2, fileData);
        //outputs a table with the word and its corresponding definition
        outputWordAndCount(wordList, wordList2, fileOut);
        //outputs the end tags of the table and html file
        outputFooter(fileOut);

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        /*
         * User is asked to input their file destination and their output file
         * name.
         */
        out.print("Enter the name of the input file: ");
        //gettysburg.txt
        String userInput = in.nextLine();
        out.print("Enter the name of the outputfile: ");
        //gettysburg.html
        String outputFile = in.nextLine();
        processFile(userInput, outputFile);
        //lets the user know the file has been generated successfully
        out.println("Success!");
        /*
         * closes in and out stream
         */
        out.close();
        in.close();
    }

}
