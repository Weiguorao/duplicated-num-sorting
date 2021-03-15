package main.java.se.xcom.bitmapsorting;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import main.java.se.xcom.bitmapsorting.utils.Bitmap;
import main.java.se.xcom.bitmapsorting.utils.IOUtils;
import main.java.se.xcom.bitmapsorting.utils.PhoneNumberCanonicalizer;

public class BitmapSorting {
    private final String delimiter = BitmapSortingProperties.DELIMITER.value();
    private final String countryCode = BitmapSortingProperties.COUNTRYCODE.value();
    private final String internationalPrefix = BitmapSortingProperties.INTERNATIONALPREFIX.value();
    private final String nationalPrefix = BitmapSortingProperties.NATIONALPREFIX.value();
    private final PhoneNumberCanonicalizer phoneNumberCanonicalizer =
            new PhoneNumberCanonicalizer(countryCode, internationalPrefix, nationalPrefix);
    // sort the phone number in bitmap
    private final Bitmap bitmap =
            new Bitmap(Integer.parseInt(BitmapSortingProperties.MAXPHONENUM.value()));
    // keep track of the duplicated times for each phone number
    private final HashMap<String, Integer> numWithDuplicates = new HashMap<String, Integer>();

    public File sort() throws IOException {
        // read each number from file, and canonicalize and save the phone number in bitmap with
        // duplicates in numWithDuplicates
        readNumsFromFile();

        // go through bitmap and together with the duplicates info from numWithDuplicates to output
        // the sorted info to the file
        return sortNumsBackToFile();
    }

    private void readNumsFromFile() throws IOException {
        BufferedReader reader = null;
        try {
            reader =
                    new BufferedReader(
                            new FileReader(BitmapSortingProperties.INPUTFILENAME.value()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    saveNumWithDuplicates(line);
                }
            }
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private File sortNumsBackToFile() throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            File outputFile =
                    new File(
                            BitmapSortingProperties.OUTPUTFILEPATH.value()
                                    + new SimpleDateFormat("yyyyMMddHHmm'.txt'")
                                    .format(new Date()));
            outputFile.createNewFile();
            FileOutputStream out = new FileOutputStream(outputFile);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            readNumsFromFile();

            for (String num : bitmap.getNumsOrderByAsc()) {
                if (numWithDuplicates.get(String.valueOf(num)) > 1) {
                    bufferedWriter.write(getNumToWriteOut(num));
                    bufferedWriter.newLine();
                }
            }
            return outputFile;
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
        }
    }

    private void saveNumWithDuplicates(String num) {
        // to save some memory...
        String numWithoutCountryCode =
                phoneNumberCanonicalizer.canonicalize(num).replace("+" + countryCode, "");
        if (isNumTooLong(numWithoutCountryCode)) {
            // can put be in a log
            System.out.println("This number is longer than expected after normalization: " + num);
            return;
        }
        bitmap.add(Integer.parseInt(numWithoutCountryCode));
        Integer duplicates = numWithDuplicates.get(numWithoutCountryCode);
        numWithDuplicates.put(numWithoutCountryCode, duplicates == null ? 1 : duplicates + 1);
    }

    // canonicalized phone number + ";" + duplicates (those numbers without duplicates skipped)
    private String getNumToWriteOut(String num) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        stringBuilder.append(countryCode);
        stringBuilder.append(num);
        stringBuilder.append(delimiter);
        stringBuilder.append(numWithDuplicates.get(num));
        return stringBuilder.toString();
    }

    // log those numbers longer than expected
    private boolean isNumTooLong(String numWithoutCountryCode) {

        if (numWithoutCountryCode.length() > BitmapSortingProperties.MAXPHONENUM.value().length()) {

            return true;
        }
        return false;
    }
}
