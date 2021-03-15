package main.java.se.xcom.bitmapsorting.utils;

import java.util.ArrayList;
import java.util.List;

public class Bitmap {
    private byte[] bits;
    // The maximum number to be stored
    private int capacity;
    // The maximum number stored
    private int maxNumAdded = 0;
    // The minimum number stored
    private int minNumAdded = 0;

    public Bitmap(int capacity) {
        this.capacity = capacity;

        // 8 numbers/byte
        bits = new byte[(capacity >> 3) + 1];
    }

    public void add(int num) {
        if (num > capacity) {
            return;
        }
        // num >> 3 is the index in byte[]
        // num & 0x07 is the position byte[index]
        bits[num >> 3] |= 1 << (num & 0x07);
        if (num > maxNumAdded) {
            maxNumAdded = num;
        }

        if (num < minNumAdded && minNumAdded != 0) {
            minNumAdded = num;
        }
    }

    public boolean contain(int num) {
        // num >> 3 is the index in byte[]
        // num & 0x07 is the position byte[index]
        return (bits[num >> 3] & (1 << (num & 0x07))) != 0;
    }

    public void clear(int num) {
        // num >> 3 is the index in byte[]
        // num & 0x07 is the position byte[index]
        bits[num >> 3] &= ~(1 << (num & 0x07));
        if (num == minNumAdded) {
            // find then next minimum number
            for (int i = minNumAdded + 1; i <= maxNumAdded; i++) {
                if (getBit(i)) {
                    minNumAdded = i;
                    return;
                }
            }

            minNumAdded = 0;
            maxNumAdded = 0;
            return;
        }

        if (num == maxNumAdded) {
            // find the next maximum number
            for (int i = maxNumAdded - 1; i >= minNumAdded; i--) {
                if (getBit(i)) {
                    maxNumAdded = i;
                    return;
                }
            }
            // no number any more
            maxNumAdded = 0;
            minNumAdded = 0;
        }
    }

    private boolean getBit(int k) {
        // num >> 3 is the index in byte[]
        // num & 0x07 is the position byte[index]
        return (bits[k >> 3] & 1 << (k & 0x07)) != 0;
    }

    public List<String> getNumsOrderByAsc() {
        List<String> nums = new ArrayList<>();

        for (int i = minNumAdded; i <= maxNumAdded; i++) {
            if (getBit(i)) {
                nums.add(String.valueOf(i));
            }
        }

        return nums;
    }
}
