package edu.pdx.cs410J.kathtran;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import edu.pdx.cs410J.InvokeMainTestCase;

import java.text.ParseException;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project3.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing command line arguments"));
    }

    @Test
    public void testIsValidDateAndTime() throws ParseException {
        Project3 project3 = new Project3();
        String date = "01/23/2015";
        String time = "11:00";
        String mark = "am";
        assertTrue("Formatting returns True", project3.isValidDateAndTime(date, time, mark));
    }

}