package edu.pdx.cs410J.kathtran;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains the main method that runs the Phone Bill Application
 * in addition to various helper methods that correct and/or validate
 * user-supplied command line arguments that are used to construct and
 * populate the phone bill.
 *
 * @author Kathleen Tran
 * @version 1.0
 */
public class Project1 {

    /**
     * Takes an array of arguments to populate and model a customer's phone bill. Any
     * missing and/or incorrect arguments will result in an output of the corresponding
     * system error message.
     *
     * @param args options or arguments for the phone bill, or any combination of both.
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        Project1 project1 = new Project1();

        for (String arg : args) {
            if (arg.equals("-README"))
                project1.readme();
        }

        boolean printCall = false;
        int index = 0;
        if (args[index].equals("-print")) {     // The only time that a call will be printed will
            printCall = true;                   // be when the `-print` option is specified as the
            index += 1;                         // very first argument in the array of arguments.
        }

        PhoneBill phoneBill;
        if (args[index] != null && args[index].length() > 1) {
            phoneBill = new PhoneBill(project1.correctNameCasing(args[index]));
            index += 1;
        } else {
            System.err.println("Missing customer name");
            System.exit(1);
        }

        String callerNumber = null;     // Temporary Strings used to
        String calleeNumber = null;     // hold each requirement of
        String startTime = null;        // the phone call record.
        String endTime = null;
        if (args[index] != null && project1.isValidPhoneNumber(args[index]) == true) {
            callerNumber = args[index];
            index += 1;
        } else {
            System.err.println("Missing caller number");
            System.exit(1);
        }
        if (args[index] != null && project1.isValidPhoneNumber(args[index]) == true) {
            calleeNumber = args[index];
            index += 1;
        } else {
            System.err.println("Missing callee number");
            System.exit(1);
        }
        if (args[index] != null && args[index + 1] != null && project1.isValidTime(args[index], args[index + 1])) {
            startTime = args[index];
            startTime = startTime.concat(" " + args[index + 1]);
            index += 2;
        } else {
            System.err.print("Missing start time");
            System.exit(1);
        }
        if (args[index] != null && args[index + 1] != null && project1.isValidTime(args[index], args[index + 1])) {
            endTime = args[index];
            endTime = endTime.concat(" " + args[index + 1]);
        } else {
            System.err.print("Missing end time");
            System.exit(1);
        }

        PhoneCall phoneCall = null;
        if (callerNumber != null && calleeNumber != null && startTime != null && endTime != null)
            phoneCall = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
        else {
            System.err.println("Insufficient arguments for the call record");
            System.exit(1);
        }

        if (printCall == true) {
            System.out.println(phoneCall.toString());
        }
    }

    /**
     * Corrects the casing of some <code>String</code> that is the customer's name.
     *
     * @param nameInput some name provided by the user, as a <code>String</code>.
     * @return a <code>String</code> where the first letter of each name is capitalized
     * while the remaining letters are lower cased. Each part of the name is separated
     * by a single whitespace.
     */
    public String correctNameCasing(String nameInput) {
        String correctedName = new String();
        String[] fullName = nameInput.split(" ");
        for (String name : fullName) {
            char firstLetter = Character.toUpperCase(name.charAt(0));
            String remainingLetters = name.substring(1).toLowerCase();
            correctedName = correctedName.concat(firstLetter + remainingLetters + " ");
        }
        return correctedName.substring(0, correctedName.length() - 1);
    }

    /**
     * Determines whether or not some <code>String</code> is of the form
     * <code>nnn-nnn-nnnn</code> where <code>n</code> is a number <code>0-9</code>.
     *
     * @param phoneNumberInput some phone number provided by the user, as a <code>String</code>.
     * @return <code>True</code> if the form is valid, otherwise <code>false</code>.
     */
    public boolean isValidPhoneNumber(String phoneNumberInput) {
        Pattern phoneNumberFormat = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher numberToBeChecked = phoneNumberFormat.matcher(phoneNumberInput);
        return numberToBeChecked.matches();
    }

    /**
     * Determines whether or not some <code>String</code> is of the form
     * <code>mm/dd/yyyy hh:mm</code> where the month, day, and hour may be
     * one digit if it is less than the value of nine.
     *
     * @param dateInput the month, day, and year, as a <code>String</code>.
     * @param timeInput the hour and minutes, as a <code>String</code>.
     * @return <code>True</code> if the form is valid, otherwise <code>false</code>.
     */
    public boolean isValidTime(String dateInput, String timeInput) {
        Pattern dateFormat = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");
        Matcher dateToBeChecked = dateFormat.matcher(dateInput);
        String[] dateCheck = dateInput.split("/");
        int month = Integer.parseInt(dateCheck[0]);
        int day = Integer.parseInt(dateCheck[1]);
        int year = Integer.parseInt(dateCheck[2]);

        Pattern timeFormat = Pattern.compile("\\d{1,2}:\\d{2}");
        Matcher timeToBeChecked = timeFormat.matcher(timeInput);
        String[] timeCheck = timeInput.split(":");
        int hour = Integer.parseInt(timeCheck[0]);
        int minute = Integer.parseInt(timeCheck[0]);
        if (checkDateValidity(month, day, year) == false) {
            System.err.println("Invalid date");
            System.exit(1);
        }
        return (dateToBeChecked.matches() && (year >= 1900 && year <= 2015) &&
                timeToBeChecked.matches() && (hour >= 0 && hour <= 23) &&
                (minute >= 0 && minute <= 59));
    }

    /**
     * Determines the validity of some given date.
     *
     * @param month the <code>int</code> that correlates to some month of the year.
     * @param day the <code>int</code> that correlates to some day of the month.
     * @param year the <code>int</code> that correlates to some year.
     * @return <code>true</code> if the day is valid within the month, otherwise <code>false</code>.
     */
    public boolean checkDateValidity(int month, int day, int year) {
        if (month == 2 && day <= 28)
            return true;
        if (month == 2 && day == 29 && (year%4 == 0 && year%100 != 0))
            return true;
        if (month == 9 || month == 4 || month == 6 || month == 11) {
            if (day <= 30)
                return true;
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day <= 31)
                return true;
        }
        return false;
    }

    /**
     * This prints out a brief description of what the Phone Bill program does.
     */
    public void readme() {
        System.out.print("\n\t\tREADME - PHONE BILL APPLICATION\n" +
                "\t\t===============================\n" +
                "Introduction\n" +
                "------------\n\n" +
                "Welcome to the Phone Bill Application! This is a command-line\n" +
                "application that allows the user to model a phone bill. In its\n" +
                "current version, the user may associate at most one phone record\n" +
                "per customer name. However, the information will not be stored\n" +
                "between each usage. A single phone record consists of the phone\n" +
                "number of the caller, the phone number that was called, the time\n" +
                "at which the call began, and the time at which the call ended.\n\n" +
                "Commands\n" +
                "--------\n\n" +
                "-README\t\tThe project description. Entering this option at\n" +
                "\t\tthe command line will display this page.\n" +
                "-print\t\tA description of some phone call. Entering this\n" +
                "\t\toption at the command line will display the\n" +
                "\t\tdescription of the most recently added phone call.\n\n" +
                "To add a calling event, the following arguments must be provided\n" +
                "in the order listed below, separated by a single white space.\n\n" +
                "<customer>\t\tPerson whose phone bill we're modelling\n" +
                "<caller number>\t\tPhone number of the caller\n" +
                "<callee number>\t\tPhone number of the person called\n" +
                "<start time>\t\tDate and time the call began\n" +
                "<end time>\t\tDate and time the call ended\n\n" +
                "If the customer name contains more than one word, it must be\n" +
                "delimited by double quotes. Phone numbers must be of the form\n" +
                "nnn-nnn-nnnn where n is a number 0-9. Date and time should be\n" +
                "in the format: mm/dd/yyyy hh:mm and zeros may be omitted where\n" +
                "appropriate.\n\n" +
                "usage: java edu.pdx.cs410J.kathtran.Project1 [options] <args>\n" +
                "\n" +
                "----------------------------------------------------------\n" +
                "CS410J PROJECT 1: DESIGNING A PHONE BILL APPLICATION\n\n" +
                "AUTHOR: KATHLEEN TRAN\nLAST MODIFIED: 7/5/2015\n\n");
        System.exit(1);
    }
}