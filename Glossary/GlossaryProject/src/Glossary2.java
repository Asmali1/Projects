import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author P. Bucci
 */
public final class Glossary2 {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private Glossary2() {
        // no code needed here
    }

    /**
     * Used to compare Strings in lexicographic order.
     */
    public static class StringLT implements Comparator<String> {
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
            return o1.compareTo(o2);

        }
    }

    /**
     * outputs "opening" tags in the generated HTML file.
     *
     * @param term
     *            the title/header of the HTML file
     * @param out
     *            the output stream
     * @updates out.content
     * @ensures out.content=#out.content*HTML opening tags
     *
     */
    public static void outputHeader(String term, SimpleWriter out) {
        /*
         * prints opening tags and headers in the output stream "out"
         */
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + term + "</title>");
        out.println("</head>");
        out.println("<style>");
        //size of outputted paragraph
        out.println("p{ font-size: 1.1em;}");
        out.println("<body>");
        out.println("</style>");
        //term is used as header and it is bold, italicized, and red
        out.println("<h1 style=" + "\"color:red;\">" + "<b><i>" + term
                + "</i></b></h1>");

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
    public static void outputFooter(SimpleWriter out) {
        /*
         * generates the closing tags of the generated HTML file: closes the
         * <table>, <body>, and <html> tag.
         */
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Outputs the definition of a given term in the generated HTML file.
     *
     * @param definition
     *            the output stream
     * @param out
     *            the output stream
     * @param termsList
     *            list of terms
     *
     * @updates out.content
     * @requires out.is_open
     * @ensures
     */
    public static void outputDefinition(String definition, SimpleWriter out,
            Queue<String> termsList) {
        //opens up paragraph tag
        out.print("<p>");
        /*
         * each word separated by one space in the string definitions is added
         * to the String array "words
         */
        String[] words = definition.split(" ");
        /*
         * each word in words is checked with each word in the Queue list
         * definitionsList. if the words are equal, it means that there is a
         * word in "definition" that is in the user's inputed text file.
         * contains becomes true and that word is hyper linked to the existing
         * word in the definitionsList's webpage
         */
        for (int i = 0; i < words.length; i++) {
            StringBuilder word = new StringBuilder();
            for (int j = 0; i < words[i].length(); i++) {
                if (Character.isLetter(words[i].charAt(j))) {
                    word.append(words[i].charAt(j));
                }
            }
            boolean contains = false;
            for (String a : termsList) {
                if (word.toString().equals(a)) {
                    contains = true;
                }
            }
            /*
             * if word is in definitionsList, it is printed with a hyperlink to
             * its definition, otherwise, it is printed normally
             */
            if (contains) {
                out.print("<a href=" + word.toString() + ".html>"
                        + word.toString() + "</a>" + " ");
            } else {
                out.print(word.toString() + " ");
            }
        }
        //closes paragraph tag
        out.println("</p>");
        //prints line that cuts the page after the definitions are printed
        out.print("<hr style=\"height:2px;border-width:0;color:gray;background"
                + "-color:blue\">\r\n");
        //printed in html file and hyper links to the main Index web page
        out.print("<p>Return to ");
        out.println("<a href=Index.html" + ">Index</a></p>");
    }

    /**
     * Gets all the terms in the fileData input stream and adds in to Queue list
     * then sorts it.
     *
     * @param termsList
     * @param fileData
     * @updates termsList
     * @ensures termsList=#termsList is sorted
     */
    public static void getAlphabatizedTermsList(Queue<String> termsList,
            SimpleReader fileData) {
        /*
         * keeps the terms in a list. the while loop is used to check every line
         * after a blank like(that is the term). After the txt file reaches end
         * of stream, while loop ends and all the terms are grabbed. The
         * comparator is used to organize the terms in alphabetical order
         */
        Comparator<String> sorter = new StringLT();

        while (!fileData.atEOS()) {
            String term = fileData.nextLine();
            termsList.enqueue(term);
            while (!term.isEmpty()) {
                term = fileData.nextLine();

            }
        }
        //sorts out termsList using the Comparator "sorter"
        termsList.sort(sorter);
    }

    /**
     * Processes information in a given text file by the user, converting it to
     * an input stream to be a read, and an HTML file being created in their
     * inputed folder.
     *
     * @param termsFile
     *            User's text file
     * @param folderLocation
     *            user's inputed folder location
     * @requires folderLocation is a valid folder and termsFile is a valid text
     *           file with terms and definitions
     * @ensures html files are generated in the user's inputed folder
     */
    public static void proccessFile(String termsFile, String folderLocation) {
        //html file where the glossary information gets printed to
        SimpleWriter fileOut = new SimpleWriter1L(
                folderLocation + "/Index.html");
        //the text file inputed by the user to be read
        SimpleReader fileData = new SimpleReader1L(termsFile);
        /*
         * Queue is created to keep all terms. It is called by the method
         * "getAlphabatizedTermsList" to add all terms and be sorted
         */
        Queue<String> termsList = new Queue1L<>();
        getAlphabatizedTermsList(termsList, fileData);

        /*
         * prints out the header for the Glossary in the Index.html file along
         * with the long blue line that cuts the web page
         */
        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println("<title>Glossary</title>");
        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("<h1>Glossary</h1>");
        fileOut.print(
                "<hr style=\"height:2px;border-width:0;color:gray;background"
                        + "-color:blue\">\r\n");
        fileOut.println("<h2>Index</h2>");
        //bullet points open up
        fileOut.println("<ul>");
        //Initialized  to be the length of the Queue definitionsLists
        int len = termsList.length();
        /*
         * Loop continues as long as len is not 0. Creates new SimpleReader to
         * read in the user's inputted text file again
         */
        while (len > 0) {
            SimpleReader fileData2 = new SimpleReader1L(termsFile);
            /*
             * a term from the termsList is pulled and trimmed, so there's no
             * white spaces in between. currentFile creates a new HTML file with
             * the name of the currentTerm. replaceAll is used to remove any
             * spaces between so no errors are causes
             */
            String currentTerm = termsList.dequeue().trim();
            String currentFile = folderLocation + "/"
                    + currentTerm.replaceAll(" ", "") + ".html";
            //opens output .html file is created using currentFile
            SimpleWriter termOut = new SimpleWriter1L(currentFile);
            /*
             * this if statement is used just in case the user accidentally puts
             * in a file that doesn't have a term nor definition-- just an empty
             * line
             */
            if (!currentTerm.isEmpty()) {
                //header is created in the termOut output stream
                outputHeader(currentTerm, termOut);
                /*
                 * currentLine is initialized to be the first line of fileData.
                 * the loop keeps checking and looks for the first line that is
                 * equal to the currentTerm
                 */
                String currentLine = fileData2.nextLine();
                while (!currentLine.equals(currentTerm)) {
                    currentLine = fileData2.nextLine();
                }
                /*
                 * stringBuilder definition Builder is created to be definition
                 * of the currentTerm. It will keep adding on lines if the
                 * currentTerm has definitions in separate lines until it
                 * reaches an empty line
                 */
                StringBuilder definitionBuilder = new StringBuilder();
                definitionBuilder.append(fileData2.nextLine());
                currentLine = fileData2.nextLine();
                while (!currentLine.isEmpty()) {
                    //appends space and the currentLine(which is a string).
                    definitionBuilder.append(" " + currentLine);
                    //currentLine moves on to the next line to be checked
                    currentLine = fileData2.nextLine();
                }
                /*
                 * method is used to output definition to the termOut output
                 * stream. definitionBuilder is changed to a string first and
                 * trimmed of any white spaces at the beginning and end of it
                 */
                outputDefinition(definitionBuilder.toString().trim(), termOut,
                        termsList);
                outputFooter(termOut);
                /*
                 * outputs bullet points with the name of the term and Hyperlink
                 * to their definition. all spaces of currentTerm are removed to
                 * prevent issues.
                 */
                fileOut.println("<li><a href=" + currentTerm.replaceAll(" ", "")
                        + ".html" + ">" + currentTerm + "</a></li>");
                //closes output stream to prevent leaks
                termOut.close();
                //closes input stream to prevent leaks
                fileData2.close();
            }
            //adds current term back to termsList
            termsList.enqueue(currentTerm);
            //decrements len by 1 for next loop
            len--;
        }
        //closes input stream to prevent leaks
        fileData.close();
        //closing tag for bullet points
        fileOut.println("</ul>");
        //outputs closing tags for fileOut
        outputFooter(fileOut);

        //closes output file to prevent any leaks
        fileOut.close();

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        out.print("Enter a text file with terms and definitions: ");
        String userTextFile = in.nextLine();
        out.print("Enter a folder name: ");
        String userFolder = in.nextLine();
        proccessFile(userTextFile, userFolder);
        out.println("Success!");
        in.close();
        out.close();
    }

}
