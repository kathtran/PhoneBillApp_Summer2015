package edu.pdx.cs410J.kathtran;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents Project 2 and contains the main method that runs the Phone
 * Bill Application in addition to various helper methods that correct
 * and/or validate user-supplied command line arguments that are used to
 * construct and populate the phone bill.
 * <p>
 * v2.0 UPDATE: There now exists calls to methods that handle working
 * with external files for both reading to and writing from phone bills.
 *
 * @author Kathleen Tran
 * @version 2.0
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
            boolean savePhoneBill = false;
            String fileName = null;
            int index = 0;
            for (String arg : args) {
                if (arg.startsWith("-")) {
                    if (arg.equals("-print")) {
                        printCall = true;
                        index += 1;
                    }
                    if (!arg.equals("-print") && !arg.equals("-textFile")) {
                        System.err.println("Unknown command line option");
                        System.exit(1);
                    }
                }
            }

            for (int i = 0; i < args.length; ++i) {
                if (args[i].equals("-textFile") && args[i + 1] != null) {
                    if (args[i + 1].contains("-")) {
                        System.err.println("Missing file name");
                        System.exit(1);
                    }
                    savePhoneBill = true;
                    fileName = args[i + 1];
                    index += 2;
                }
            }

            TextDumper textDumper = new TextDumper();
            textDumper.setFileName(fileName);

            PhoneBill phoneBill = null;
            if (args[index] != null && args[index].length() > 1) {
                phoneBill = new PhoneBill(project2.correctNameCasing(args[index]));
                index += 1;
            } else {
                System.err.println("Cannot locate the customer name. " +
                        "You may want to check the order and/or formatting of your arguments.");
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
                System.err.println("Cannot locate the caller number. " +
                        "You may want to check the order and/or formatting of your arguments.");
                System.exit(1);
            }
            if (args[index] != null && project2.isValidPhoneNumber(args[index])) {
                calleeNumber = args[index];
                index += 1;
            } else {
                System.err.println("Cannot locate the callee number. " +
                        "You may want to check the order and/or formatting of your arguments.");
                System.exit(1);
            }
            if (args[index] != null && args[index + 1] != null && project2.isValidDateAndTime(args[index], args[index + 1])) {
                startTime = args[index];
                startTime = startTime.concat(" " + args[index + 1]);
                index += 2;
            } else {
                System.err.println("Cannot locate the start time. " +
                        "You may want to check the order and/or formatting of your arguments.");
                System.exit(1);
            }
            if (args[index] != null && args[index + 1] != null && project2.isValidDateAndTime(args[index], args[index + 1])) {
                endTime = args[index];
                endTime = endTime.concat(" " + args[index + 1]);
                index += 2;
            } else {
                System.err.println("Cannot locate the end time. " +
                        "You may want to check the order and/or formatting of your arguments.");
                System.exit(1);
            }
            if (index < args.length) {
                System.err.print("Extraneous command line arguments");
                System.exit(1);
            }

            PhoneCall phoneCall = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
            phoneBill.addPhoneCall(phoneCall);

            File file = new File(textDumper.getFileName());
            boolean fileExists = file.exists();

            if (printCall)
                System.out.println(phoneBill.getMostRecentPhoneCall(phoneCall).toString());
            if (savePhoneBill) {
                if (fileExists) {
                    if (textDumper.checkCustomerName(phoneBill.getCustomer()))
                        textDumper.dump(phoneBill);
                    else {
                        System.err.println("The supplied customer name and the name on file did not match. " +
                                "The phone bill record was not saved.");
                        System.exit(1);
                    }
                } else
                    textDumper.dump(phoneBill);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid and/or malformatted date(s) entered");
            System.exit(1);
        } catch (ParseException ex) {
            System.err.println("Invalid date(s) entered");
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Invalid and/or malformatted text file.");
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
        @SuppressWarnings("all")
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
     * Determines the validity of some <code>String</code> representative of the date
     * and time both in regards to the values provided and to their formatting.
     *
     * @param dateInput the month, day, and year
     * @param timeInput the hour and minute(s)
     * @return True if the both the date and formatting are valid, otherwise false
     * @throws NumberFormatException when the argument cannot be parsed into an Integer
     * @throws ParseException        when the date is invalid
     */
    public boolean isValidDateAndTime(String dateInput, String timeInput) throws NumberFormatException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.parse(dateInput);
        return isValidDateAndTimeFormat(dateInput + " " + timeInput);
    }

    /**
     * Determines whether or not some <code>String</code> is of the form
     * <code>mm/dd/yyyy hh:mm</code> where the month, day, and hour may be
     * one digit if it is less than the value of nine.
     *
     * @param dateAndTimeToCheck date and time
     * @return True if the form is valid, otherwise false
     */
    public boolean isValidDateAndTimeFormat(String dateAndTimeToCheck) {
        String[] check = dateAndTimeToCheck.split(" ");
        String dateInput = check[0];
        String timeInput = check[1];

        Pattern dateFormat = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");
        Matcher dateToBeChecked = dateFormat.matcher(dateInput);

        Pattern timeFormat = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        Matcher timeToBeChecked = timeFormat.matcher(timeInput);

        return dateToBeChecked.matches() && timeToBeChecked.matches();
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
                "application that allows the user to model a phone bill. In version\n" +
                "1.0, the user may associate at most one phone record per customer\n" +
                "name. However, the information will not be stored between each usage.\n" +
                "A single phone record consists of the phone number of the caller, the\n" +
                "phone number that was called, the time at which the call began, and\n" +
                "the time at which the call ended.\n\n" +
                "Updates\n" +
                "-------\n" +
                "v2.0\t\tThe program now allows the user to save their phone bill\n" +
                "\t\tto an external text file (both new and existing). Users may\n" +
                "\t\talso load phone bill records from existing files. Each file\n" +
                "\t\tcorrelates to a single user and their phone call(s).\n\n" +
                "Commands\n" +
                "--------\n\n" +
                "-README\t\tThe project description. Entering this option at\n" +
                "\t\tthe command line will display this page.\n" +
                "-print\t\tA description of some phone call. Entering this\n" +
                "\t\toption at the command line will display the\n" +
                "\t\tdescription of the most recently added phone call.\n" +
                "-textFile <file>\t\tWhere to read/write the phone bill\n\n" +
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
                "CS410J PROJECT 2: STORING YOUR PHONE BILL IN A TEXT FILE\n\n" +
                "AUTHOR: KATHLEEN TRAN\nLAST MODIFIED: 7/14/2015\n\n");
        System.exit(1);
    }
}