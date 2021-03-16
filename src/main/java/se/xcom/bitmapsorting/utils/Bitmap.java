package main.java.se.xcom.bitmapsorting.utils;


import java.util.ArrayList;
import java.util.List;

public class Bitmap {
    private byte[] bits;
    // The maximum number to be stored
    private int maxNum;

    public Bitmap(int maxNum) {
        this.maxNum = maxNum;

        // 8 numbers/byte
        bits = new byte[(maxNum >> 3) + 1];
    }

    public void add(int num) {
        if (num > maxNum) {
            return;
        }
        // num >> 3 is the index in byte[]
        // num & 0x07 is the position byte[index]
        bits[num >> 3] |= 1 << (num & 0x07);
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
    }

    public List<String> getNumsOrderByAsc() {
        List<String> nums = new ArrayList<>();

        for (int i = 0; i < bits.length; i++) {
            for (int j = 0; j < 8; j++) {
                // if the bit set
                if ((bits[i] & (1 << j)) != 0) {
                    nums.add(String.valueOf(i * 8 + j));
                }
            }
        }
        return nums;
    }
}
