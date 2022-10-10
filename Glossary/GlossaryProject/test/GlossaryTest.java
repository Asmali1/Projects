import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Test for Glossary.java.
 *
 * @author moham
 *
 */
public class GlossaryTest {
    /**
     * creates a txt file using outPutheader and compares it to a correct text
     * file that was manually typed and implemented.
     */
    @Test
    public void outputHeaderTEST() {
        SimpleWriter testFile = new SimpleWriter1L(
                "JUnitTestFolder/outputHeaderHello");
        Glossary.outputHeader("Hello", testFile);
        SimpleReader testReader = new SimpleReader1L(
                "JUnitTestFolder/outputHeaderHello");
        SimpleReader correctText = new SimpleReader1L(
                "JUnitCorrectAnswers/outputHeaderHelloCorrect");
        while (!testReader.atEOS()) {
            assertEquals(correctText.nextLine(), testReader.nextLine());
        }
        testReader.close();
        correctText.close();
    }

    /**
     * test output header, comparing the one created by outputFooter and one
     * manually typed.
     */
    @Test
    public void outputFooterTEST() {
        SimpleWriter testFile = new SimpleWriter1L(
                "JUnitTestFolder/outputFooterTXT");
        Glossary.outputFooter(testFile);
        SimpleReader testReader = new SimpleReader1L(
                "JUnitTestFolder/outputFooterTXT");
        SimpleReader correctText = new SimpleReader1L(
                "JUnitCorrectAnswers/outputFooterCorrectTXT");
        while (!testReader.atEOS()) {
            assertEquals(correctText.nextLine(), testReader.nextLine());
        }
        testReader.close();
        correctText.close();
    }

    /**
     * tests to see if getAlphabatizedTermsList correctly gets the terms in a
     * list and alphabetizes them.
     */
    @Test
    public void getAlphabatizedTermsListTEST() {
        SimpleReader testReader = new SimpleReader1L(
                "JUnitTestFolder/termsTest.txt");
        Queue<String> testList = new Queue1L<>();
        Queue<String> correctList = new Queue1L<>();
        Glossary.getAlphabatizedTermsList(testList, testReader);
        correctList.enqueue("Cat");
        correctList.enqueue("Dog");
        correctList.enqueue("book");
        correctList.enqueue("definition");
        correctList.enqueue("glossary");
        correctList.enqueue("language");
        correctList.enqueue("meaning");
        correctList.enqueue("term");
        correctList.enqueue("word");
        assertEquals(correctList, testList);
    }

    /**
     * tests to see if code outputs correct tags given a definition, queue,
     * output stream, text file, and a Queue with terms. Tests to see if method
     * will hyperlink the word if it is found in the Queue list.
     */

    @Test
    public void outputDefinitionTEST() {
        Queue<String> listOfTerms = new Queue1L<>();
        listOfTerms.enqueue("test");
        SimpleWriter testFile = new SimpleWriter1L(
                "JUnitTestFolder/outputDefinitionTest");
        Glossary.outputDefinition(
                "hello this is a test to see if test will be hyperlinked",
                testFile, listOfTerms);
        SimpleReader testReader = new SimpleReader1L(
                "JUnitTestFolder/outputDefinitionTest");
        SimpleReader correctText = new SimpleReader1L(
                "JUnitCorrectAnswers/outputDefinitionCorrect");
        while (!testReader.atEOS()) {
            assertEquals(correctText.nextLine(), testReader.nextLine());
        }
        testReader.close();
        correctText.close();

    }

    /**
     * tests to see if code outputs correct tags given a definition, queue,
     * output stream, text file, and a Queue with terms. Tests to see if method
     * will hyperlink MULTIPLE WORDS if they are found in the Queue list.
     */

    @Test
    public void outputDefinitionTEST2() {
        Queue<String> listOfTerms = new Queue1L<>();
        listOfTerms.enqueue("cat");
        listOfTerms.enqueue("dog");
        listOfTerms.enqueue("book");
        listOfTerms.enqueue("definition");
        listOfTerms.enqueue("glossary");
        listOfTerms.enqueue("language");
        listOfTerms.enqueue("meaning");
        listOfTerms.enqueue("term");
        listOfTerms.enqueue("word");
        SimpleWriter testFile = new SimpleWriter1L(
                "JUnitTestFolder/outputDefinitionTest2");
        Glossary.outputDefinition(
                "hello this is a test. I wonder if my cat and dog are doing well. "
                        + "I found a book to look up this word with its definition in "
                        + "this nice glossary. But I don't know the language "
                        + "nor the meaning",
                testFile, listOfTerms);
        SimpleReader testReader = new SimpleReader1L(
                "JUnitTestFolder/outputDefinitionTest2");
        SimpleReader correctText = new SimpleReader1L(
                "JUnitCorrectAnswers/outputDefinitionCorrect2");
        while (!testReader.atEOS()) {
            assertEquals(correctText.nextLine(), testReader.nextLine());
        }
        testReader.close();
        correctText.close();

    }

    /**
     * tests to see if output of a given file and folder location is correct in
     * comparison with a manually typed text file.
     */
    @Test
    public void processFileTEST() {

        Glossary.proccessFile("JUnitTestFolder/termsTest.txt",
                "JUnitTestFolder");
        SimpleReader testReader = new SimpleReader1L(
                "JUnitTestFolder/Index.html");
        SimpleReader correctTest = new SimpleReader1L(
                "JUnitCorrectAnswers/ProcesssFileCorrectTest.html");
        while (!correctTest.atEOS()) {
            assertEquals(correctTest.nextLine(), testReader.nextLine());
        }
        testReader.close();
        correctTest.close();
    }

    /**
     * tests to see if output of a given file and folder location is correct in
     * comparison with a manually typed text file. Tests with MULTIPLE spacing
     */
    @Test
    public void processFileTEST2() {

        Glossary.proccessFile("JUnitTestFolder/termsTest2.txt",
                "JUnitTestFolder");
        SimpleReader testReader = new SimpleReader1L(
                "JUnitTestFolder/Index.html");
        SimpleReader correctTest = new SimpleReader1L(
                "JUnitCorrectAnswers/ProcesssFileCorrectTest2.html");
        while (!correctTest.atEOS()) {
            assertEquals(correctTest.nextLine(), testReader.nextLine());
        }
        testReader.close();
        correctTest.close();
    }

}
