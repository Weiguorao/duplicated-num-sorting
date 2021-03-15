package main.java.se.xcom.bitmapsorting;

import java.io.File;

public enum BitmapSortingProperties {
    INPUTFILENAME("inputFileName", "./data/clients.txt") {
        public void isValid() {
            File input = new File(this.value());
            if (!input.exists()) {
                throw new IllegalArgumentException(
                        "The file to be sorted does not exist. Path: " + input.getAbsolutePath(),
                        null);
            }
        }
    },

    OUTPUTFILEPATH("outputFilePath", "./data/") {
        public void isValid() {
            File outputPath = new File(this.value());
            if (!outputPath.exists()) {
                throw new IllegalArgumentException(
                        "The path to be output does not exist. Path: " + outputPath, null);
            }
        }
    },

    DELIMITER("delimiter", ";") {
        public void isValid() {}
    },

    COUNTRYCODE("countryCode", "46") {
        public void isValid() {}
    },

    INTERNATIONALPREFIX("internationalPrefix", "00") {
        public void isValid() {};
    },

    NATIONALPREFIX("nationalPrefix", "0") {
        public void isValid() {}
    },

    MAXPHONENUM("maxPhoneNumber", "999999999") {
        public void isValid() {
            String input = this.value();
            try {
                Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Each phone number should always be a digit: " + input, null);
            }
        }
    };

    private String label;
    private String defaultValue;

    BitmapSortingProperties(String label, String defaultValue) {
        this.label = label;
        this.defaultValue = defaultValue;
    }

    public abstract void isValid();

    /**
     * Get the property value from the System properties
     *
     * @return {@code System.getProperty(label)} if it is not null or the {@code defaultValue}
     */
    public String value() {
        return System.getProperty(label, defaultValue);
    }

    public String getLabel() {
        return label;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
