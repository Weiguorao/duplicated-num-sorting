package main.java.se.xcom.bitmapsorting.utils;

public class PhoneNumberCanonicalizer {
    private final String countryCode;
    private final String internationalPrefix;
    private final String nationalPrefix;

    public PhoneNumberCanonicalizer(
            String countryCode, String internationalPrefix, String nationalPrefix) {
        this.countryCode = countryCode;
        this.internationalPrefix = internationalPrefix;
        this.nationalPrefix = nationalPrefix;
    }

    public String canonicalize(String number) {
        // Remove all weird characters such as /, -, ...
        number = number.replaceAll("[^+0-9]", "");

        // already in desired format: should not allow +0046?
        if (number.startsWith("+")) {
            return number;
        }
        // 0046 -> +46: should not allow 00744312345
        else if (number.startsWith(internationalPrefix)) {
            return "+" + number.substring(2);
        }
        // 0708275867 -> +46708275867
        else if (number.startsWith(nationalPrefix)) {
            return "+" + countryCode + number.substring(1);
        } else { // pass number than expected
            return "+" + countryCode + number;
        }
    }
}
