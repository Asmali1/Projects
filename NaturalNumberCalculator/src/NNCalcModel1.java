import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Model class.
 *
 * @author Put your name here
 */
public final class NNCalcModel1 implements NNCalcModel {

    /**
     * Model variables.
     */
    private final NaturalNumber top, bottom;

    /**
     * Default constructor.
     */
    public NNCalcModel1() {
        this.top = new NaturalNumber2();
        this.bottom = new NaturalNumber2();
    }

    @Override
    public NaturalNumber top() {

        /*
         * returns the current NaturalNumber "top" in "this"
         */

        return this.top;
    }

    @Override
    public NaturalNumber bottom() {

        /*
         * returns the current NaturalNumber "bottom" in this
         */
        return this.bottom;
    }

}
