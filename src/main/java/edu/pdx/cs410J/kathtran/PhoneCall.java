package edu.pdx.cs410J.kathtran;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * Implements the abstract methods that can be found within
 * the {@link AbstractPhoneCall}. It represents a single
 * phone call record, in which there exists a caller number,
 * callee number, start time, and end time.
 *
 * @author Kathleen Tran
 * @version 1.0
 */
public class PhoneCall extends AbstractPhoneCall {

    /**
     * The phone number of the caller
     */
    private String callerNumber;

    /**
     * The phone number of the person who was called
     */
    private String calleeNumber;

    /**
     * The time at which the call began
     */
    private String startTime;

    /**
     * The time at which the call ended
     */
    private String endTime;

    /**
     * Default constructor.
     */
    public PhoneCall() {
        this.callerNumber = null;
        this.calleeNumber = null;
        this.startTime = null;
        this.endTime = null;
    }

    /**
     * Constructor that specifies all of the fields existent within a call record.
     *
     * @param callerNumber the number of the person who called
     * @param calleeNumber the number of the person who was called
     * @param startTime    the time at which the call began
     * @param endTime      the time at which the call ended
     */
    public PhoneCall(String callerNumber, String calleeNumber, String startTime, String endTime) {
        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return the phone number of the person who originated this phone call
     */
    @Override
    public String getCaller() {
        return this.callerNumber;
    }

    /**
     * @return the phone number of the person who received this phone call
     */
    @Override
    public String getCallee() {
        return this.calleeNumber;
    }

    /**
     * @return a textual representation of the time that this phone call
     * was originated
     */
    @Override
    public String getStartTimeString() {
        return this.startTime;
    }

    /**
     * @return a textual representation of the time that this phone call
     * was completed
     */
    @Override
    public String getEndTimeString() {
        return this.endTime;
    }
}
