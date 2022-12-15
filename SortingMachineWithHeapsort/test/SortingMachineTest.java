import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMany() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "ah", "oh");
        m.add("green");
        m.add("ah");
        m.add("oh");
        assertEquals(mExpected, m);
    }

    /**
     * tests add method to sorting machines already with elements.
     */

    @Test
    public final void testAddMethod() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "blue");
        m.add("blue");
        assertEquals(mExpected, m);

    }

    /**
     * tests changeToExtractionMode.
     */
    @Test
    public final void testChangeToExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());

    }

    /**
     * tests changeToExtractionMode when SortingMachine m has multiple elements.
     */
    @Test
    public final void testChangeToExtractionMode2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "ah",
                "oh");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "he", "ho");
        m.changeToExtractionMode();
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());

    }

    /**
     * tests remove first.
     */
    @Test
    public final void removeFirst() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "blue", "purple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "blue", "purple");
        String mRemoved = m.removeFirst();
        String mExpectedRemoved = mExpected.removeFirst();
        assertEquals(mExpectedRemoved, mRemoved);
        assertEquals(mExpected, m);

    }

    /**
     * tests remove first to empty.
     */
    @Test
    public final void removeFirstEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        String mRemoved = m.removeFirst();
        String mExpectedRemoved = mExpected.removeFirst();
        assertEquals(mExpectedRemoved, mRemoved);
        assertEquals(mExpected, m);

    }

    /**
     * test isInInsertionMode test.
     */
    @Test
    public final void insertionModeTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(true, m.isInInsertionMode());

    }

    /**
     * tests isInInsertionMode when insertion mode is false.
     */
    @Test
    public final void insertionModeTest2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        assertEquals(false, m.isInInsertionMode());

    }

    /**
     * tests size method on empty-sized sorting machine.
     */
    @Test
    public final void sizeEmptyTestExtraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        assertEquals(0, m.size());

    }

    /**
     * tests size method on empty-sized sorting machine.
     */
    @Test
    public final void sizeEmptyTestInstertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(0, m.size());

    }

    /**
     * tests size test on an a sorting machine with elements and extraction mode
     * on.
     */
    @Test
    public final void sizeTestExtraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "blue", "red", "purple", "gray");
        assertEquals(5, m.size());

    }

    /**
     * tests size test on an a sorting machine with elements and insertion mode
     * on.
     */
    @Test
    public final void sizeTestInsertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "blue", "red", "purple", "gray");
        assertEquals(5, m.size());

    }

    /**
     * tests order method.
     */
    @Test
    public final void orderTest() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "blue", "red", "purple", "gray");

        assertEquals(mExpected.order(), m.order());

    }
}
