package edu.pdx.cs410J.kathtran;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.util.ArrayList;

/**
 * Implements the dump method that can be found within
 * the {@link PhoneBillDumper}. It creates a text file
 * that contains the records of a provided phone bill.
 *
 * @author Kathleen Tran
 * @version 3.0
 */
public class TextDumper implements PhoneBillDumper {

    /**
     * The name of the file that the phone bill is being saved to.
     */
    private String fileName;

    /**
     * Verifies that the customer name supplied at the command line is
     * the same as the one on record if the record currently exists.
     *
     * @param customer some name provided by the user
     * @return true if the name matches the existing record, otherwise false
     * @throws IOException if the file cannot be found
     */
    boolean checkCustomerName(String customer) throws IOException {
        File file = new File(getFileName());
        boolean fileExists = file.exists();

        if (fileExists) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while (line != null) {
                if (line.equals("CUSTOMER:")) {
                    line = br.readLine();
                    return customer.equals(line);
                }
            }
            br.close();
            fr.close();
        }
        return false;
    }

    /**
     * Dumps a phone bill to some destination specified by the user.
     *
     * @param bill a phone bill for some customer that contains at least one phone call record
     * @throws IOException if file cannot be found
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        @SuppressWarnings("unchecked")
        ArrayList<PhoneCall> phoneBill = (ArrayList<PhoneCall>) bill.getPhoneCalls();
        File file = new File(getFileName());
        boolean fileExists = file.exists();

        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        if (!fileExists)
            bw.write("CUSTOMER:\n" + bill.getCustomer() + "\n");
        bw.write("PHONE CALL:\n");
        if (!phoneBill.isEmpty()) {
            for (PhoneCall call : phoneBill) {
                bw.write(call.getCaller() + "\n");
                bw.write(call.getCallee() + "\n");
                bw.write(call.getStartTimeString() + "\n");
                bw.write(call.getEndTimeString() + "\n");
            }
        }
        bw.close();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName + ".txt";
    }
}
