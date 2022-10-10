import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to convert an XML RSS (version 2.0) feed from a given URL into the
 * corresponding HTML output file.
 *
 * @author Mohamed Asmali
 *
 */
public final class RSSReader {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSReader() {
    }

    /**
     * Outputs the "opening" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * <html> <head> <title>the channel tag title as the page title</title>
     * </head> <body>
     * <h1>the page title inside a link to the <channel> link</h1>
     * <p>
     * the channel description
     * </p>
     * <tr>
     * <th>Date</th>
     * <th>Source</th>
     * <th>News</th>
     * </tr>
     *
     * @param channel
     *            the channel element XMLTree
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the root of channel is a <channel> tag] and out.is_open
     * @ensures out.content = #out.content * [the HTML "opening" tags]
     */
    private static void outputHeader(XMLTree channel, SimpleWriter out) {
        assert channel != null : "Violation of: channel is not null";
        assert out != null : "Violation of: out is not null";
        assert channel.isTag() && channel.label().equals("channel") : ""
                + "Violation of: the label root of channel is a <channel> tag";
        assert out.isOpen() : "Violation of: out.is_open";
        //opens up html file
        out.println("<html>");
        //opens up style of table
        out.println("<style>");
        //sets up tables and it's items to have a border of 1 px solid black
        out.println("table,th,td{");
        out.println("border:1px solid black;");
        out.println("}");
        //closes the style of the table
        out.println("</style>");
        //opens up title of of the page
        out.println("<head>");

        /*
         * if getChildment comes back as NOT -1, the title of the channel is
         * available and will be generated as the title of the HTML page Checks
         * to see title is there. If title is there, it checks it if it has a
         * children. If link is a available, it will be hyperlinked to the title
         * If none is available, then code will generate "No title is available"
         */
        if (getChildElement(channel, "title") != -1) {
            if (channel.child(getChildElement(channel, "title"))
                    .numberOfChildren() > 0) {
                if (channel.child(getChildElement(channel, "link"))
                        .numberOfChildren() > 0) {

                    out.println("<title>" + channel
                            .child(getChildElement(channel, "title")).child(0)
                            + "</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>" + "<a href="
                            + channel.child(getChildElement(channel, "link"))
                                    .child(0)
                            + " target=" + "\"_blank\"" + ">"
                            + channel.child(getChildElement(channel, "title"))
                                    .child(0)
                            + "</a>" + "</h1>");

                } else {
                    out.println("<title>" + channel
                            .child(getChildElement(channel, "title")).child(0)
                            + "</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>" + channel
                            .child(getChildElement(channel, "title")).child(0)
                            + "</h1>");

                }

            } else if (channel.child(getChildElement(channel, "link"))
                    .numberOfChildren() > 0) {

                out.println("<title>No title available</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>" + "<a href="
                        + channel.child(getChildElement(channel, "link"))
                                .child(0)
                        + " target=" + "\"_blank\"" + ">" + "No title available"
                        + "</a>" + "</h1>");

            } else {
                out.println("<title>No title available</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>No title available</h1>");

            }

        }
        /*
         * Checks if there is a description, if there is a description, then
         * code will check if it has a child. If the description doesn't have a
         * child, then code will print "No description available"
         */

        if (getChildElement(channel, "description") != -1) {
            if (channel.child(getChildElement(channel, "description"))
                    .numberOfChildren() > 0) {
                out.println("<p>" + channel
                        .child(getChildElement(channel, "description")).child(0)
                        + "</p>");
            } else {
                out.println("<p> No description available</p>");
            }
        }
        /*
         * opens up table with a width of 100%, and a border of 1;]. Opens up 3
         * rows:Date, Source, and Title/Description and closes the tag
         */
        out.println("<table style=\"width:100%\"" + "border=\"1\"" + ">");
        out.println("<tr>");
        out.println("<th>Date</th>");
        out.println("<th>Source</th>");
        out.println("<th>Title/Description</th>");
        out.println("</tr>");

    }

    /**
     * Outputs the "closing" tags in the generated HTML file. These are the
     * expected elements generated by this method:
     *
     * </table>
     * </body> </html>
     *
     * @param out
     *            the output stream
     * @updates out.contents
     * @requires out.is_open
     * @ensures out.content = #out.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        /*
         * generates the closing tags of the generated HTML file: closes the
         * <table>, <body>, and <html> tag.
         */
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of type tag of the {@code XMLTree}
     *         or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of type tag of the {@code XMLTree} or
     *   -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";
        //Initialized k to be index to be the starting index of the xml;
        //will increment in upcoming while loop to find child element of "tag"
        //j keeps count of the first occurrence of the given tag among the
        //children of the xml
        int k = 0, j = -1;
        String name = xml.child(k).label();
        //if tag is equal to child 0 for the xml, then statement will proceed
        if (name.equals(tag)) {
            j = k;
        }
        // Finds the child element that the tag is if "if" statement is false
        while (!name.equals(tag) && k < xml.numberOfChildren()) {
            name = xml.child(k).label();
            if (name.equals(tag)) {
                j = k;
            }

            k++;
        }

        return j;

    }

    /**
     * Processes one news item and outputs one table row. The row contains three
     * elements: the publication date, the source, and the title (or
     * description) of the item.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML table row with publication date, source, and title of news item]
     * </pre>
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item") : ""
                + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";
        //opens up row for the current "item" tag
        out.println("<tr>");
        /*
         * checks if publication date is available and prints it out to the
         * "Date" row; if there is no publication date, then
         * "No Publication Date Available" will be generated instead
         */
        if (getChildElement(item, "pubDate") != -1) {
            if (item.child(getChildElement(item, "pubDate"))
                    .numberOfChildren() > 0) {
                out.println("<td>"
                        + item.child(getChildElement(item, "pubDate")).child(0)
                        + "</td>");
            } else {
                out.println("<td>No Publication Date Available</td>");
            }
        }
        /*
         * checks to see if the current item has a "source" tag. the item's
         * source information will be printed to the column under the "Source"
         * row. If source isn't found, then "No source available" will print
         * instead If a "url" attribute is found and it has an attribute, that
         * will be hyperlinked to the "source" or if there is no URL attribute
         * found, it will be skipped if there is a URL attribute, but no source
         * child, it will be hyperlinked to the generated "No source available"
         */
        if (getChildElement(item, "source") != -1) {
            if (item.child(getChildElement(item, "source"))
                    .numberOfChildren() > 0) {
                if (item.child(getChildElement(item, "source"))
                        .hasAttribute("url")) {

                    out.println("<td>" + "<a href="
                            + item.child(getChildElement(item, "source"))
                                    .attributeValue("url")
                            + " target=" + "\"_blank\"" + ">"
                            + item.child(getChildElement(item, "source"))
                                    .child(0)
                            + "</a>" + "</td>");
                } else {
                    out.println(
                            "<td>" + item.child(getChildElement(item, "source"))
                                    .child(0) + "</td>");
                }

            } else {
                if (item.child(getChildElement(item, "source"))
                        .hasAttribute("url")) {

                    out.println("<td>" + "<a href="
                            + item.child(getChildElement(item, "source"))
                                    .attributeValue("url")
                            + " target=" + "\"_blank\"" + ">"
                            + "No source Available" + "</a>" + "</td>");

                } else {
                    out.println("<td> No source Available</td>");
                }
            }
        }
        /*
         * checks to see if the current item has a link tag. If a link tag is
         * found, it's child's will be the hyperlink on the title tag if the
         * link or the title is not found, code will check to see if there is a
         * description and output that to the HTML file under the
         * Description/Title row If no link is available, then just the title
         * will print If neither is available, then "No description or title
         * available will print
         */
        if (getChildElement(item, "title") != -1 && item
                .child(getChildElement(item, "title")).numberOfChildren() > 0) {

            if (getChildElement(item, "link") != -1
                    && item.child(getChildElement(item, "link"))
                            .numberOfChildren() > 0) {
                out.println("<td>" + "<a href="
                        + item.child(getChildElement(item, "link")).child(0)
                        + " target=" + "\"_blank\"" + ">"
                        + item.child(getChildElement(item, "title")).child(0)
                        + "</a>" + "</td>");

            } else {
                out.println("<td>"
                        + item.child(getChildElement(item, "title")).child(0)
                        + "</td>");
            }

        } else if (getChildElement(item, "description") != -1
                && item.child(getChildElement(item, "description"))
                        .numberOfChildren() > 0) {

            if (getChildElement(item, "link") != -1
                    && item.child(getChildElement(item, "link"))
                            .numberOfChildren() > 0) {
                out.println("<td>" + "<a href="
                        + item.child(getChildElement(item, "link")).child(0)
                        + " target=" + "\"_blank\"" + ">"
                        + item.child(getChildElement(item, "description"))
                                .child(0)
                        + "</a>" + "</td>");
            } else {
                out.println("<td>" + item
                        .child(getChildElement(item, "description")).child(0)
                        + "</td>");
            }

        } else {
            if (getChildElement(item, "link") != -1
                    && item.child(getChildElement(item, "link"))
                            .numberOfChildren() > 0) {
                out.println("<td>" + "<a href="
                        + item.child(getChildElement(item, "link")).child(0)
                        + " target=" + "\"_blank\"" + ">"
                        + item.child(getChildElement(item, "description"))
                                .child(0)
                        + "</a>" + "</td>");

            } else {
                out.println("<td> No description or title available</td>");
            }
        }
        //closes the column

        out.println("</tr>");

    }

    /**
     * Checks to see if user's entered RSS feed is an XML Tree.
     *
     * @param xml
     *            XMLTree
     * @return boolean value(isRSS)
     *
     */
    private static boolean checkRSS(XMLTree xml) {
        boolean isRSS = false;
        /*
         * if the XMLTree entered by the user has an an "rss" tag, and a version
         * attribute with a "2.0," isRSS will return true
         */
        if (xml.label().equals("rss")) {
            if (xml.hasAttribute("version")
                    && xml.attributeValue("version").equals("2.0")) {
                isRSS = true;

            }
        }

        return isRSS;
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
        out.print("Enter a valid RSS 2.0 feed : ");
        String userRSS = in.nextLine();
        //opens up XMLTree to be the user's string
        XMLTree xml = new XMLTree1(userRSS);
        //if checkRSS(method that checks if user's entered RSS is a valid RSS
        //2.0), statement proceeds and notifies user that it's incorrect

        if (!checkRSS(xml)) {
            out.println("That is an incorrect RSS feed");

            /*
             * Else statement proceeds if checkRSS comes back true. Allows user
             * to enter an HTML file for the RSS 2.0 to be outputted on
             */
        } else {
            out.print("Enter a valid .html output file: ");
            String userOutputFile = in.nextLine();
            //user's entered HTML for the userOutput is initialized
            //to be a SimpleWriter called "fileOut"
            SimpleWriter fileOut = new SimpleWriter1L(userOutputFile);
            //opens up and outputs the header of the page using the channel
            //child in the valid RSS 2.0 feed
            outputHeader(xml.child(0), fileOut);
            /*
             * for every item child found in "channel," it is sent to the
             * processItem method and its publication date, source, and
             * title/description is outputted to the HTML file
             */
            for (int i = 0; i < xml.child(0).numberOfChildren(); i++) {
                if (xml.child(0).child(i).label().equals("item")) {
                    processItem(xml.child(0).child(i), fileOut);
                }
            }
            //prints when HTML has been generated and the RSS feed has been
            //outputted neatly to it
            out.println("Success!");
            //generates "closing" tags to the HTML file
            outputFooter(fileOut);
            //closes fileOut SimpleWriter to prevent leaks
            fileOut.close();

        }
        //closes in SimpleReader and out SimpleWriter to prevent leaks
        in.close();
        out.close();

    }

}
