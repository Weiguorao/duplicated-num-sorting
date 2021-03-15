package test.java.se.xcom.bitmapsort;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BitmapTest {
    // each number need 9 digits without the country code etc.
    private Bitmap bitmap = new Bitmap(999999999);

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    @Test
    public void testSingleNumberSetSucceeded() {
        bitmap.add(123);
        Assert.assertEquals(true, bitmap.contain(123));
        Assert.assertEquals(false, bitmap.contain(124));

        bitmap.add(678);
        Assert.assertEquals(true, bitmap.contain(678));
        bitmap.clear(123);
        Assert.assertEquals(false, bitmap.contain(123));
        Assert.assertEquals(true, bitmap.contain(678));

        bitmap.add(999999999);
        Assert.assertEquals(true, bitmap.contain(999999999));

        bitmap.add(1);
        Assert.assertEquals(true, bitmap.contain(1));
    }

    @Test
    public void testSortNumbersSucceeded() {
        bitmap.add(123);
        bitmap.add(888);
        bitmap.add(212);
        bitmap.add(999);
        bitmap.add(1216);
        bitmap.add(777);
        bitmap.add(999);

        List<String> bits = bitmap.getNumsOrderByAsc();
        Assert.assertEquals("123", bits.get(0));
        Assert.assertEquals("212", bits.get(1));
        Assert.assertEquals("777", bits.get(2));
        Assert.assertEquals("888", bits.get(3));
        Assert.assertEquals("999", bits.get(4));
        Assert.assertEquals("1216", bits.get(5));
    }
}
