import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Put your name here
 *
 */
public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * big number
     */
    @Test
    public void testReduceToGCD2() {
        NaturalNumber n = new NaturalNumber2(1232);
        NaturalNumber nExpected = new NaturalNumber2(2);
        NaturalNumber m = new NaturalNumber2(422);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * m is 0
     */
    @Test
    public void testReduceToGCD3() {
        NaturalNumber n = new NaturalNumber2(4213);
        NaturalNumber nExpected = new NaturalNumber2(4213);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
    *
    */

    @Test
    public void testReduceToGCD4() {
        NaturalNumber n = new NaturalNumber2(68);
        NaturalNumber nExpected = new NaturalNumber2(17);
        NaturalNumber m = new NaturalNumber2(51);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /**
     * Test 0
     */

    @Test
    public void testIsEven1() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /**
    *
    */

    @Test
    public void testIsEven2() {
        NaturalNumber n = new NaturalNumber2(42);
        NaturalNumber nExpected = new NaturalNumber2(42);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /**
     * Test big even number.
     */
    @Test
    public void testIsEven3() {
        NaturalNumber n = new NaturalNumber2("321323123432");
        NaturalNumber nExpected = new NaturalNumber2("321323123432");
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /**
     * Test odd even number.
     */
    @Test
    public void testIsEven4() {
        NaturalNumber n = new NaturalNumber2("3213231234322323");
        NaturalNumber nExpected = new NaturalNumber2("3213231234322323");
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /**
     * Test with n and m being 0.
     */

    @Test
    public void testPowerMod1() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Test with different numbers
     */

    @Test
    public void testPowerMod2() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Test with large numbers
     */
    @Test
    public void testPowerMod3() {
        NaturalNumber n = new NaturalNumber2(31232);
        NaturalNumber nExpected = new NaturalNumber2(68);
        NaturalNumber p = new NaturalNumber2(3);
        NaturalNumber pExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(233);
        NaturalNumber mExpected = new NaturalNumber2(233);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Test with large numbers
     */
    @Test
    public void testPowerMod4() {
        NaturalNumber n = new NaturalNumber2("2312344312334");
        NaturalNumber nExpected = new NaturalNumber2(1680);
        NaturalNumber p = new NaturalNumber2(23213);
        NaturalNumber pExpected = new NaturalNumber2(23213);
        NaturalNumber m = new NaturalNumber2(2312);
        NaturalNumber mExpected = new NaturalNumber2(2312);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
     * Test with ultra large numbers
     */
    @Test
    public void testPowerMod41() {
        NaturalNumber n = new NaturalNumber2("1213121231221231");
        NaturalNumber nExpected = new NaturalNumber2(30627679);
        NaturalNumber p = new NaturalNumber2(22321);
        NaturalNumber pExpected = new NaturalNumber2(22321);
        NaturalNumber m = new NaturalNumber2(33213332);
        NaturalNumber mExpected = new NaturalNumber2(33213332);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
    *
    */
    @Test
    public void testPowerMod5() {
        NaturalNumber n = new NaturalNumber2(73);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(2);
        NaturalNumber pExpected = new NaturalNumber2(2);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
    *
    */
    @Test
    public void testPowerMod6() {
        NaturalNumber n = new NaturalNumber2(5343);
        NaturalNumber nExpected = new NaturalNumber2(223);
        NaturalNumber p = new NaturalNumber2(23);
        NaturalNumber pExpected = new NaturalNumber2(23);
        NaturalNumber m = new NaturalNumber2(232);
        NaturalNumber mExpected = new NaturalNumber2(232);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /**
    *
    */
    @Test
    public void isWitnessTest1() {
        NaturalNumber w = new NaturalNumber2(3);
        NaturalNumber n = new NaturalNumber2(232);
        NaturalNumber wCopy = new NaturalNumber2(w);
        NaturalNumber nCopy = new NaturalNumber2(n);
        boolean returned = CryptoUtilities.isWitnessToCompositeness(w, n);
        boolean answer = true;
        assertEquals(wCopy, w);
        assertEquals(nCopy, n);
        assertEquals(returned, answer);
    }

    /**
    *
    */
    @Test
    public void isWitnessTest2() {
        NaturalNumber w = new NaturalNumber2(42);
        NaturalNumber n = new NaturalNumber2(321331);
        NaturalNumber wCopy = new NaturalNumber2(w);
        NaturalNumber nCopy = new NaturalNumber2(n);
        boolean returned = CryptoUtilities.isWitnessToCompositeness(w, n);
        boolean answer = false;
        assertEquals(wCopy, w);
        assertEquals(nCopy, n);
        assertEquals(returned, answer);
    }

    /**
     * Test with big number.
     */
    @Test
    public void isWitnessTest3() {
        NaturalNumber w = new NaturalNumber2(23123);
        NaturalNumber n = new NaturalNumber2(2312344);

        NaturalNumber wCopy = new NaturalNumber2(w);
        NaturalNumber nCopy = new NaturalNumber2(n);

        boolean returned = CryptoUtilities.isWitnessToCompositeness(w, n);
        boolean answer = true;
        assertEquals(wCopy, w);
        assertEquals(nCopy, n);
        assertEquals(returned, answer);
    }

    /**
     *
     */
    @Test
    public void isPrime2Test1() {
        NaturalNumber n = new NaturalNumber2(2312344);
        NaturalNumber nCopy = new NaturalNumber2(n);
        boolean returned = CryptoUtilities.isPrime2(n);
        boolean answer = false;
        assertEquals(nCopy, n);
        assertEquals(returned, answer);
    }

    /**
    *
    */
    @Test
    public void isPrime2Test2() {
        NaturalNumber n = new NaturalNumber2("233142132133321");
        NaturalNumber nCopy = new NaturalNumber2(n);
        boolean returned = CryptoUtilities.isPrime2(n);
        boolean answer = false;
        assertEquals(nCopy, n);
        assertEquals(returned, answer);
    }

    /**
    *
    */
    @Test
    public void isPrime2Test3() {
        NaturalNumber n = new NaturalNumber2("233142132133323");
        NaturalNumber nCopy = new NaturalNumber2(n);
        boolean returned = CryptoUtilities.isPrime2(n);
        boolean answer = false;
        assertEquals(nCopy, n);
        assertEquals(returned, answer);
    }

    /*
     *
     */
    @Test
    public void generateNextPrime1() {
        NaturalNumber n = new NaturalNumber2(24);
        CryptoUtilities.generateNextLikelyPrime(n);
        NaturalNumber nextNum = new NaturalNumber2(29);
        assertEquals(nextNum, n);
    }

    /*
     *
     */
    @Test
    public void generateNextPrime2() {
        NaturalNumber n = new NaturalNumber2(23121322);
        CryptoUtilities.generateNextLikelyPrime(n);
        NaturalNumber nextNum = new NaturalNumber2(23121331);
        assertEquals(nextNum, n);
    }

}