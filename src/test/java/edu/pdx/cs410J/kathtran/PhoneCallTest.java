package edu.pdx.cs410J.kathtran;

import junit.framework.TestCase;

/**
 * Tests the functionality in the {@link PhoneCall} main class.
 */
public class PhoneCallTest extends TestCase {

    public void testCompareTo() throws Exception {
        PhoneCall call1 = new PhoneCall("123-432-3213", "243-432-5436", "01/30/2015 10:00 am", "02/01/2015 3:00 pm");
        PhoneCall call2 = new PhoneCall("432-313-4353", "243-432-5436", "03/3/2015 12:00 pm", "03/3/2015 12:30 pm");
        PhoneCall call3 = new PhoneCall("143-547-8789", "243-432-5436", "01/30/2015 10:00 am", "02/01/2015 3:00 am");
        PhoneCall call4 = new PhoneCall("123-432-3213", "243-432-5436", "01/30/2015 10:00 am", "02/01/2015 3:00 pm");

        int result = call4.compareTo(call1);
        assertEquals(result, 0);
    }

    public void testGetCallDuration() throws Exception {
        PhoneCall call1 = new PhoneCall("123-432-3213", "243-432-5436", "01/30/2015 10:00", "01/31/2015 11:10");
        PhoneCall call2 = new PhoneCall("432-313-4353", "243-432-5436", "03/3/2015 12:00", "03/3/2015 14:30");

        System.out.println(call1.getCallDuration());
    }

    public void testDateFormatter() throws Exception {
        PhoneCall call1 = new PhoneCall("123-432-3213", "243-432-5436", "01/30/2015 10:00 am", "01/31/2015 11:10 pm");
        System.out.println(call1.toString());
    }
}