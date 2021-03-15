package test.java.se.xcom.bitmapsort;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PhoneNumberCanonicalizerTest {
    private final String countryCode = "46";
    private final String internationalPrefix = "00";
    private final String nationalPrefix = "0";
    private PhoneNumberCanonicalizer phoneNumberCanonicalizer =
            new PhoneNumberCanonicalizer(countryCode, internationalPrefix, nationalPrefix);

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    @Test
    public void testNumberContainingDashSucceed() {
        String num = "076-1301007";
        String numExpected = "+" + countryCode + "761301007";
        Assert.assertEquals(phoneNumberCanonicalizer.canonicalize(num), numExpected);
    }

    @Test
    public void testNumberWithoutNationalPrefixSucceed() {
        String num = "705608145";
        String numExpected = "+" + countryCode + "705608145";
        Assert.assertEquals(phoneNumberCanonicalizer.canonicalize(num), numExpected);
    }

    @Test
    public void testNumberWithInternationalAndNationalPrefixInMiddleSucceed() {
        String num = "700469913";
        String numExpected = "+" + countryCode + "700469913";
        Assert.assertEquals(phoneNumberCanonicalizer.canonicalize(num), numExpected);
    }

    @Test
    public void testNumberWithILeadingInternationalPrefixAndCountryCodeSucceed() {
        String num = "0046700469913";
        String numExpected = "+" + countryCode + "700469913";
        Assert.assertEquals(phoneNumberCanonicalizer.canonicalize(num), numExpected);
    }

    @Test
    public void testNumberWithILeadingCountryCodeSucceed() {
        String num = "0700469913";
        String numExpected = "+" + countryCode + "700469913";
        Assert.assertEquals(phoneNumberCanonicalizer.canonicalize(num), numExpected);
    }
}
