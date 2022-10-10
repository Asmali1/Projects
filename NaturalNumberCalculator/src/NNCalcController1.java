import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Put your name here
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {
        NaturalNumber top = model.top();
        NaturalNumber bottom = model.bottom();
        view.updateDivideAllowed(!bottom.isZero());
        view.updateSubtractAllowed(top.compareTo(bottom) >= 0);
        view.updatePowerAllowed(bottom.compareTo(INT_LIMIT) <= 0);
        view.updateRootAllowed(
                bottom.compareTo(INT_LIMIT) <= 0 && bottom.compareTo(TWO) >= 0);

        view.updateTopDisplay(model.top());
        view.updateBottomDisplay(model.bottom());

    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        /**
         * when cleared, the view will check if bottom is zero. If bottom is
         * zero, then the divide button and root button will be disabled.
         */
        this.view.updateDivideAllowed(!bottom.isZero());
        this.view.updateRootAllowed(
                bottom.compareTo(INT_LIMIT) <= 0 && !bottom.isZero());
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);

        /*
         * after swapping, the code checks if thew new swapped values abide by
         * these rules; divide is enabled if the new bottom isn't zero, subtract
         * is enabled if top is greater than bottom, power is enabled if bottom
         * is smaller than the maximum int, and root is enabled if bottom is
         * less than int max and NOT zero
         */
        this.view.updateDivideAllowed(!bottom.isZero());
        this.view.updateSubtractAllowed(top.compareTo(bottom) >= 0);
        this.view.updatePowerAllowed(bottom.compareTo(INT_LIMIT) <= 0);
        this.view.updateRootAllowed(
                bottom.compareTo(INT_LIMIT) <= 0 && !bottom.isZero());
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {
        /*
         * Creates aliases
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * top copies bottom and updated on the view; updates view after change
         * in model
         */
        top.copyFrom(bottom);
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {
        /*
         * aliases created
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * top is added to bottom and then top is cleared
         */
        bottom.add(top);
        top.clear();
        /*
         * updates view after change in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {
        /*
         * creates aliases
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * bottom is subtracted from top and then trasnfered over to bottom,
         * clearing top
         */
        top.subtract(bottom);
        bottom.transferFrom(top);
        /*
         * updates view after change in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {
        /*
         * creates aliases
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * top is multiplied by bottom then transfered over to bottom, clearing
         * top
         */
        top.multiply(bottom);
        bottom.transferFrom(top);
        /*
         * updates view after change in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {
        /*
         * creates aliases
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /**
         * top is divided by bottom, and the remainder is returned
         */
        NaturalNumber remainder = top.divide(bottom);
        /*
         * top is transfered to bottom after the division, clearing top; then
         * remainder is transfered over to top.
         */
        bottom.transferFrom(top);
        top.transferFrom(remainder);
        /*
         * updates view after change in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {
        /*
         * creates aliases
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * bottom is powered to top converted to an INT(since the power method's
         * formal parameter is an integer and not a NaturalNumber
         */
        bottom.power(top.toInt());
        /*
         * updates view after change in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {
        /*
         * creates aliases
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * this is calculated as: the bottom's root of top. bottom is converted
         * into an integer because the instance method's root's formal parameter
         * is an integer. then top is transfered over to bottom, clearing top
         */
        top.root(bottom.toInt());
        bottom.transferFrom(top);
        /*
         * updates view after change in model
         */
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {
        /*
         * creates aliases
         */
        // NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * before adding a digit, the code checks to enable: divide if bottom is
         * not zero, subtract if top is greater than or equal to bottom, power
         * if bottom is less than or equal to the maximum integer value, and
         * root if bottom is not zero and is less than or equal to the maximum
         * integer value
         */

        bottom.multiplyBy10(digit);
        /*
         * after digit is added, the code checks whether to enable divide,
         * subtract, power, and root as checked before the digit was added
         */
        //root has to be equal or greater than 2
//        this.view.updateDivideAllowed(!bottom.isZero());
//        this.view.updateSubtractAllowed(top.compareTo(bottom) >= 0);
//        this.view.updatePowerAllowed(bottom.compareTo(INT_LIMIT) <= 0);
//        this.view.updateRootAllowed(
//                bottom.compareTo(INT_LIMIT) <= 0 && bottom.compareTo(TWO) >= 0);
        /*
         * updates view after change in model
         */

        updateViewToMatchModel(this.model, this.view);

    }

}
