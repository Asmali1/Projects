import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Mohamed Asmali, Donovan Liao, Joseph Daprano
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    @Test
    //test for createFromArgsTest
    public void testConstructorTest() {
        Set<String> set = this.constructorTest();
        Set<String> expectedSet = this.constructorRef();
        assertEquals(set, expectedSet);
    }

    @Test
    //test for add to non empty set
    public void testAddToNonEmpty() {
        Set<String> set = this.createFromArgsTest("man");
        Set<String> sExpected = this.createFromArgsRef("man", "alone");
        set.add("alone");
        assertEquals(sExpected, set);
    }

    @Test
    //test for add to non-empty Set
    public void testAddToNonEmpty2() {
        Set<String> set = this.createFromArgsTest("alone", "harz", "hehehe");
        Set<String> sExpected = this.createFromArgsRef("alone", "harz",
                "hehehe", "yeew");
        set.add("yeew");
        assertEquals(sExpected, set);
    }

    @Test
    //test for add to empty Set
    public void testAddToEmpty() {
        Set<String> set = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("alone");
        set.add("alone");
        assertEquals(sExpected, set);
    }

    @Test
    //test for remove from non empty Set
    public void testRemoveFromNonEmpty() {
        Set<String> set = this.createFromArgsTest("one", "man");
        Set<String> sExpected = this.createFromArgsRef("one");
        String word = set.remove("man");
        String wordExpected = "man";
        assertEquals(sExpected, set);
        assertEquals(wordExpected, word);
    }

    @Test
    //test for remove to empty Set
    public void testRemoveToEmpty() {
        Set<String> set = this.createFromArgsTest("man");
        Set<String> sExpected = this.createFromArgsRef();
        String word = set.remove("man");
        String wordExpected = "man";
        assertEquals(sExpected, set);
        assertEquals(wordExpected, word);
    }

    @Test
    //test for remove to empty Set
    public void testRemoveFromNonEmpty2() {
        Set<String> set = this.createFromArgsTest("1", "2", "3");
        Set<String> sExpected = this.createFromArgsRef("2", "3");
        String word = set.remove("1");
        String wordExpected = "1";
        assertEquals(sExpected, set);
        assertEquals(wordExpected, word);
    }

    @Test
    //test for removeAny from non-empty set
    public void testRemoveAnyNonEmpty() {
        Set<String> set = this.createFromArgsTest("one", "man");
        Set<String> sExpected = this.createFromArgsRef("one", "man");
        String r = set.removeAny();
        assertEquals(true, sExpected.contains(r));
        sExpected.remove(r);
        assertEquals(set, sExpected);
    }

    @Test
    //test for removeAny from non-empty set
    public void testRemoveAnyNonEmpty2() {
        Set<String> set = this.createFromArgsTest("one");
        Set<String> sExpected = this.createFromArgsRef("one");
        String r = set.removeAny();
        assertEquals(true, sExpected.contains(r));
        sExpected.remove(r);
        assertEquals(set, sExpected);
    }

    @Test
    //test for contains in empty set
    public void testContainsFromEmpty() {
        Set<String> set = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        boolean c = set.contains("one");
        assertEquals(sExpected, set);
        assertEquals(false, c);
    }

    @Test
    //test for contains in non empty set
    public void testContainsFromNonEmpty() {
        Set<String> set = this.createFromArgsTest("one", "man");
        Set<String> sExpected = this.createFromArgsRef("one", "man");
        boolean c = set.contains("one");
        assertEquals(sExpected, set);
        assertEquals(true, c);
    }

    @Test
    //test for contains in non empty set outputting false
    public void testContainsFromNonEmpty2() {
        Set<String> set = this.createFromArgsTest("one", "man");
        Set<String> sExpected = this.createFromArgsRef("one", "man");
        boolean c = set.contains("three");
        assertEquals(sExpected, set);
        assertEquals(false, c);
    }

    @Test
    //test for size on empty set
    public void testSizeEmpty() {
        Set<String> set = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        int size = set.size();
        assertEquals(sExpected, set);
        assertEquals(0, size);
    }

    @Test
    //test for size on non empty set
    public void testSizeNonEmpty() {
        Set<String> set = this.createFromArgsTest("one", "man");
        Set<String> sExpected = this.createFromArgsRef("one", "man");
        int size = set.size();
        assertEquals(sExpected, set);
        assertEquals(2, size);
    }

}
