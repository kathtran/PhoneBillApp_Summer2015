package edu.pdx.cs410J.kathtran;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;

/**
 * This class implements the abstract methods that can be found
 * within the AbstractPhoneBill class in addition to new methods
 * that support the construction of the phone bill skeleton. The
 * customer's name as well as their collection of phone call
 * records are maintained here.
 *
 * @author Kathleen Tran
 * @verison 1.0
 */
public class PhoneBill extends AbstractPhoneBill {

    /**
     * The customer's name. May consist of one or more words,
     * and be comprised of any character, symbol, and number.
     */
    private String customer;

    /**
     * All phone call records that are associated with the
     * customer. Each record, or item, is an instance of the
     * PhoneCall class.
     *
     * @see PhoneCall
     */
    private Collection phoneCalls;

    /**
     * Default constructor.
     */
    public PhoneBill() {
        this.customer = null;
        this.phoneCalls = null;
    }

    /**
     * Constructor that specifies the customer's name.
     *
     * @param customer a name that may consist of one or more
     *                 words, as some String.
     */
    public PhoneBill(String customer) {
        this.customer = customer;
        this.phoneCalls = null;
    }

    /**
     * @return the name of the customer whose phone bill this is.
     */
    @Override
    public String getCustomer() {
        return this.customer;
    }

    /**
     * Adds a phone call record to this phone bill.
     *
     * @param call an instance of the <code>PhoneCall</code> class that
     *             contains the caller's phone number, callee's phone
     *             number, and start and end times of the call.
     */
    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        this.phoneCalls.add(call);
    }

    /**
     * @return all of the phone calls (as instances of {@link
     * AbstractPhoneCall}) in this phone bill.
     */
    @Override
    public Collection getPhoneCalls() {
        return this.phoneCalls;
    }
}
