import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero
    /**
     * Test int constructor.
     */
    @Test
    public void constructorInt() {
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber testExpected = this.constructorRef(0);
        assertEquals(testExpected, test);

    }

    /**
     * Test int constructor with max int value.
     */
    @Test
    public void constructorInt2() {
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber testExpected = this.constructorRef(Integer.MAX_VALUE);

        assertEquals(testExpected, test);

    }

    /**
     * Test int constructor with random value.
     */
    @Test
    public void constructorInt3() {
        NaturalNumber test = this.constructorTest(12343412);
        NaturalNumber testExpected = this.constructorRef(12343412);
        assertEquals(testExpected, test);

    }

    /**
     * Test string constructor with huge string value.
     */
    @Test
    public void constructorString2() {
        NaturalNumber test = this
                .constructorTest("123456789012345678901234567890");
        NaturalNumber testExpected = this
                .constructorRef("123456789012345678901234567890");
        assertEquals(testExpected, test);

    }

    /**
     * Test string constructor with bigger string.
     */
    @Test
    public void constructorString3() {
        NaturalNumber test = this.constructorTest(
                "12345678901234567890123456789012345678901234567890123"
                        + "4567890123456789012345678901234567890");
        NaturalNumber testExpected = this.constructorRef(
                "12345678901234567890123456789012345678901234567890123"
                        + "45678901234567890123456" + "78901234567890");
        assertEquals(testExpected, test);

    }

    /**
     * Test empty string constructor.
     */
    @Test
    public void constructorZeroString() {
        NaturalNumber test = this.constructorTest("0");
        NaturalNumber testExpected = this.constructorRef("0");
        assertEquals(testExpected, test);

    }

    /**
     * Test natural constructor with 0.
     */
    @Test
    public void constructorNN() {
        NaturalNumber test = this.constructorTest();
        NaturalNumber testExpected = this.constructorRef();
        assertEquals(testExpected, test);

    }

    /**
     * Test nn constructor with big max NN int value.
     */
    @Test
    public void constructorNN2() {
        NaturalNumber test = this
                .constructorTest(new NaturalNumber3(Integer.MAX_VALUE));
        NaturalNumber testExpected = this
                .constructorRef(new NaturalNumber3(Integer.MAX_VALUE));
        assertEquals(testExpected, test);

    }

    /**
     * Test nn constructor with big max NN int value.
     */
    @Test
    public void constructorNN3() {
        NaturalNumber test = this.constructorTest(new NaturalNumber3(
                "123456789012345678901234567890123456789012345678901231234"
                        + "5678901234567890123456789012345678901234567890123"));
        NaturalNumber testExpected = this
                .constructorRef(new NaturalNumber3("123456789012345678901234"
                        + "56789012345678901234567890123123456789012345678901234"
                        + "56789012345678901234567890123"));
        assertEquals(testExpected, test);

    }

    /**
     * Test for isZero int constructor.
     */
    @Test
    public void isZeroInt() {
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber testExpected = this.constructorRef(0);

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test for isZero int constructor.
     */
    @Test
    public void isZeroInt2() {
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber testExpected = this.constructorRef(Integer.MAX_VALUE);
        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test for isZero int constructor.
     */
    @Test
    public void isZeroInt3() {
        NaturalNumber test = this.constructorTest(400000);
        NaturalNumber testExpected = this.constructorRef(400000);

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero with string constructor.
     */
    @Test
    public void isZeroString() {
        NaturalNumber test = this.constructorTest();
        NaturalNumber testExpected = this.constructorRef();
        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero with string constructor.
     */
    @Test
    public void isZeroString2() {
        NaturalNumber test = this.constructorTest("0");
        NaturalNumber testExpected = this.constructorRef("0");
        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero with string constructor.
     */
    @Test
    public void isZeroString4() {
        NaturalNumber test = this.constructorTest("7500000");
        NaturalNumber testExpected = this.constructorRef("7500000");
        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero with NaturalNumber constructor.
     */
    @Test
    public void isZeroNN() {
        NaturalNumber test = this.constructorTest(new NaturalNumber3(
                "12345678901234567890123456789012345678901234567890"));
        NaturalNumber testExpected = this.constructorRef(
                "12345678901234567890123456789012345678901234567890");
        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero with NaturalNumber constructor with max Integer value.
     */
    @Test
    public void isZeroNN2() {
        NaturalNumber test = this
                .constructorTest(new NaturalNumber3(Integer.MAX_VALUE));
        NaturalNumber testExpected = this
                .constructorRef(new NaturalNumber3(Integer.MAX_VALUE));
        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test multiplyBy10 with int constructor.
     */
    @Test
    public void multiplyBy10Int() {
        NaturalNumber test = this.constructorTest(439);
        NaturalNumber testExpected = this.constructorRef(439);
        testExpected.multiplyBy10(4);
        test.multiplyBy10(4);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with int constructor.
     */
    @Test
    public void multiplyBy10Int2() {
        NaturalNumber test = this.constructorTest(231232);
        NaturalNumber testExpected = this.constructorRef(231232);
        testExpected.multiplyBy10(5);
        test.multiplyBy10(5);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with int constructor.
     */
    @Test
    public void multiplyBy10Int3() {
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber testExpected = this.constructorRef(0);
        testExpected.multiplyBy10(7);
        test.multiplyBy10(7);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with int constructor.
     */
    @Test
    public void multiplyBy10Int4() {
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber testExpected = this.constructorRef(0);
        testExpected.multiplyBy10(0);
        test.multiplyBy10(0);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with NaturalNumber constructor.
     */
    @Test
    public void multiplyBy10NaturalNumber1() {
        NaturalNumber test = this
                .constructorTest(new NaturalNumber3(Integer.MAX_VALUE));
        NaturalNumber testExpected = this
                .constructorRef(new NaturalNumber3(Integer.MAX_VALUE));
        testExpected.multiplyBy10(8);
        test.multiplyBy10(8);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with NaturalNumber constructor.
     */
    @Test
    public void multiplyBy10NaturalNumber2() {
        NaturalNumber test = this.constructorTest(new NaturalNumber3());
        NaturalNumber testExpected = this.constructorRef(new NaturalNumber3());
        testExpected.multiplyBy10(6);
        test.multiplyBy10(6);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with NaturalNumber constructor.
     */
    @Test
    public void multiplyBy10NaturalNumber3() {
        NaturalNumber test = this.constructorTest(new NaturalNumber3(
                "123456123455231312345612345523131234561234552313"));
        NaturalNumber testExpected = this.constructorRef(new NaturalNumber3(
                "123456123455231312345612345523131234561234552313"));
        testExpected.multiplyBy10(9);
        test.multiplyBy10(9);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with NaturalNumber constructor.
     */
    @Test
    public void multiplyBy10NaturalNumber4() {
        NaturalNumber test = this.constructorTest(new NaturalNumber3());
        NaturalNumber testExpected = this.constructorRef(new NaturalNumber3());
        testExpected.multiplyBy10(0);
        test.multiplyBy10(0);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with String constructor.
     */
    @Test
    public void multiplyBy10String1() {
        NaturalNumber test = this.constructorTest("0");
        NaturalNumber testExpected = this.constructorRef("0");
        testExpected.multiplyBy10(0);
        test.multiplyBy10(0);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with String constructor.
     */
    @Test
    public void multiplyBy10String2() {
        NaturalNumber test = this.constructorTest("12303212");
        NaturalNumber testExpected = this.constructorRef("12303212");
        testExpected.multiplyBy10(4);
        test.multiplyBy10(4);
        testExpected.multiplyBy10(7);
        test.multiplyBy10(7);
        assertEquals(testExpected, test);
    }

    /**
     * Test divideBy10 with int constructor.
     */
    public void divideBy10Int() {
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber testExpected = this.constructorRef(0);
        int returned = test.divideBy10();
        int returnedExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(returnedExpexted, returned);

    }

    /**
     * Test divideBy10 with int constructor.
     */
    public void divideBy10Int2() {
        NaturalNumber test = this.constructorTest(12345);
        NaturalNumber testExpected = this.constructorRef(12345);
        int returned = test.divideBy10();
        int returnedExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(returnedExpexted, returned);

    }

    /**
     * Test divideBy10 with int constructor.
     */
    public void divideBy10Int3() {
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber testExpected = this.constructorRef(Integer.MAX_VALUE);
        int returned = test.divideBy10();
        int returnedExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(returnedExpexted, returned);

    }

    /**
     * Test divideBy10 with string constructor.
     */
    public void divideBy10String() {
        NaturalNumber test = this.constructorTest("");
        NaturalNumber testExpected = this.constructorRef("");
        int returned = test.divideBy10();
        int returnedExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(returnedExpexted, returned);

    }

    /**
     * Test divideBy10 with string constructor.
     */
    public void divideBy10String2() {
        NaturalNumber test = this
                .constructorTest("123221321313241535712391462387519231723");
        NaturalNumber testExpected = this
                .constructorRef("123221321313241535712391462387519231723");
        int returned = test.divideBy10();
        int returnedExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(returnedExpexted, returned);

    }

    /**
     * Test divideBy10 with NaturalNumber constructor.
     */
    public void divideBy10NN() {
        NaturalNumber test = this.constructorTest(new NaturalNumber3());
        NaturalNumber testExpected = this.constructorRef(new NaturalNumber3());
        int returned = test.divideBy10();
        int returnedExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(returnedExpexted, returned);

    }

    /**
     * Test divideBy10 with NaturalNumber constructor.
     */
    public void divideBy10NN2() {
        NaturalNumber test = this
                .constructorTest(new NaturalNumber3(Integer.MAX_VALUE));
        NaturalNumber testExpected = this
                .constructorRef(new NaturalNumber3(Integer.MAX_VALUE));
        int returned = test.divideBy10();
        int returnedExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(returnedExpexted, returned);
    }
}
