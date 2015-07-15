package edu.pdx.cs410J.kathtran;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.text.ParseException;

/**
 *
 */
public class TextParser implements PhoneBillParser {

    /**
     * The name of the file that the phone bill is being read from.
     */
    private String fileName;

    /**
     * Parses some source and returns a phone bill.
     *
     * @throws ParserException If the source cannot be parsed
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {
        Project2 project2 = new Project2();
        PhoneBill phoneBill;
        PhoneCall phoneCall;
        File file = new File(getFileName());
        boolean customerNameCheck = false;
        boolean phoneCallsCheck = false;

        String caller;
        String callee;
        String startDate;
        String startTime;
        String endDate;
        String endTime;
        try {
            if (file.exists()) {
                FileReader fr = new FileReader(getFileName());
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();

                while (line != null) {
                    if (!customerNameCheck) {
                        if (line.equals("CUSTOMER:")) {
                            line = br.readLine();
                            phoneBill = new PhoneBill(project2.correctNameCasing(line));

                            while (line != null) {
                                if (line.equals("PHONE CALL:")) {
                                    line = br.readLine();
                                    caller = br.readLine();
                                    callee = br.readLine();
                                    startDate = br.readLine();
                                    startTime = br.readLine();
                                    endDate = br.readLine();
                                    endTime = br.readLine();

                                    if (project2.isValidPhoneNumber(caller) &&
                                            project2.isValidPhoneNumber(callee) &&
                                            project2.isValidDateAndTime(startDate, startTime) &&
                                            project2.isValidDateAndTime(endDate, endTime)) {
                                        phoneCall = new PhoneCall(caller, callee, startDate + " " + startTime, endDate + " " + endTime);
                                        phoneBill.addPhoneCall(phoneCall);
                                    }
                                } else {
                                    break;
                                }
                            }
                        } else {
                            System.err.println("Could not identify customer name.");
                            System.exit(1);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Something went wrong while trying to locate the specified file.");
            System.exit(1);
        } catch (ParseException ex) {
            System.err.println("DATE ERR");
            System.exit(1);
        }


        return null;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName + ".txt";
    }
}
