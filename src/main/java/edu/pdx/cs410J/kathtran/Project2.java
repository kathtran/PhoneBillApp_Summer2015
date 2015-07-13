package edu.pdx.cs410J.kathtran;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents Project 1 and contains the main method that runs the Phone
 * Bill Application in addition to various helper methods that correct
 * and/or validate user-supplied command line arguments that are used to
 * construct and populate the phone bill.
 *
 * @author Kathleen Tran
 * @version 1.0
 */
public class Project2 {

    /**
     * Takes an array of arguments to populate and model a customer's phone bill. Any
     * missing and/or incorrect arguments will result in the output of a corresponding
     * system error message.
     *
     * @param args options or arguments for the phone bill, or any combination of both.
     */
    public static void main(String[] args) {

        try {
            Project2 project2 = new Project2();

            for (String arg : args) {
                if (arg.equals("-README"))
                    project2.readme();
            }

            boolean printCall = false;
            int index = 0;
            if (args[index].equals("-print")) {     // The only time that a call will be printed will
                printCall = true;                   // be when the `-print` option is specified as the
                index += 1;                         // very first argument in the array of arguments.
            }

            PhoneBill phoneBill = null;
            if (args[index] != null && args[index].length() > 1) {
                phoneBill = new PhoneBill(project2.correctNameCasing(args[index]));
                index += 1;
            } else {
                System.err.println("Missing customer name");
                System.exit(1);
            }

            String callerNumber = null;     // Temporary Strings used to
            String calleeNumber = null;     // hold each requirement of
            String startTime = null;        // the phone call record.
            String endTime = null;
            if (args[index] != null && project2.isValidPhoneNumber(args[index])) {
                callerNumber = args[index];
                index += 1;
            } else {
                System.err.println("Missing caller number");
                System.exit(1);
            }
            if (args[index] != null && project2.isValidPhoneNumber(args[index])) {
                calleeNumber = args[index];
                index += 1;
            } else {
                System.err.println("Missing callee number");
                System.exit(1);
            }
            if (args[index] != null && args[index + 1] != null && project2.isValidTime(args[index], args[index + 1])) {
                startTime = args[index];
                startTime = startTime.concat(" " + args[index + 1]);
                index += 2;
            } else {
                System.err.print("Missing start time");
                System.exit(1);
            }
            if (args[index] != null && args[index + 1] != null && project2.isValidTime(args[index], args[index + 1])) {
                endTime = args[index];
                endTime = endTime.concat(" " + args[index + 1]);
                index += 2;
            } else {
                System.err.print("Missing end time");
                System.exit(1);
            }
            if (index < args.length) {
                System.err.print("Extraneous command line arguments");
                System.exit(1);
            }

            PhoneCall phoneCall = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
            phoneBill.addPhoneCall(phoneCall);

            if (printCall)
                System.out.println(phoneBill.getMostRecentPhoneCall(phoneCall).toString());
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid date entered");
            System.exit(1);
        }
    }

    /**
     * Corrects the casing of some <code>String</code> that is the customer's name.
     *
     * @param nameInput some name provided by the user
     * @return a String where the first letter of each name is capitalized while
     * the remaining letters are lower cased. Each part of the name is separated
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
     * @param phoneNumberInput some phone number provided by the user
     * @return True if the form is valid, otherwise false
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
     * @param dateInput the month, day, and year
     * @param timeInput the hour and minute(s)
     * @return True if the form is valid, otherwise false
     * @throws NumberFormatException when argument cannot be parsed into an Integer
     */
    public boolean isValidTime(String dateInput, String timeInput) throws NumberFormatException {
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
        if (!checkDateValidity(month, day, year)) {
            System.err.println("Invalid date entered");
            System.exit(1);
        }
        return (dateToBeChecked.matches() && (year >= 1900 && year <= 2015) &&
                timeToBeChecked.matches() && (hour >= 0 && hour <= 23) &&
                (minute >= 0 && minute <= 59));
    }

    /**
     * Determines the validity of some given date.
     *
     * @param month the number representation of some month (1-12)
     * @param day   some day of the month. May vary depending on month
     * @param year  some year between 1900 and 2015, inclusive
     * @return true if the day is valid within the month, otherwise false
     */
    public boolean checkDateValidity(int month, int day, int year) {
        if (month == 2 && day <= 28)
            return true;
        if (month == 2 && day == 29 && (year % 4 == 0 && year % 100 != 0))      // Leap year in the month of February
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
     * Prints out a brief description of what the Phone Bill Application is and how it operates.
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
                "Options are to be entered before arguments, and only the customer\n" +
                "name may be delimited by double quotes.\n" +
                "\n" +
                "----------------------------------------------------------\n" +
                "CS410J PROJECT 1: DESIGNING A PHONE BILL APPLICATION\n\n" +
                "AUTHOR: KATHLEEN TRAN\nLAST MODIFIED: 7/7/2015\n\n");
        System.exit(1);
    }
}