# duplicated-num-sorting

### Code structure

```
BitmapSorting bitmapSorting = new BitmapSorting();
bitmapSorting.sort();
```

### Sort strategy

Now the sorting is basing on the integer value of phone number after normalization (removal of the leading "+" + country code) - the less length of phone number, the higher priority to be sorted out.

Taking 763053058, 77061, 770503058 for example, the sorting result if order by descending is 77061, 763053058, 770503058.

If using the strategy to align leftmost to sort out the result, then the result should be 763053058, 770503058, 77061.

One of the solution to address is:
1. Add "0" from the rightmost of the number to make the shorter number be always having the same length, e.g. 77061 -> 770610000, but we might need to differentiate 77061 and 770610 from the original number 
2. 
