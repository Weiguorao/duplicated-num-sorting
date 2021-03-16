# duplicated-num-sorting

### Code structure

```
BitmapSorting bitmapSorting = new BitmapSorting();
bitmapSorting.sort();
```

### Sorting strategy
Now the sorting is basing on the integer value of each phone number after normalization (removal of the leading "+" + country code) - the less length of phone number, the higher priority to be sorted out.

Taking 763053058, 77061, 770503058 for example, the sorting result if order by descending is 77061, 763053058, 770503058.

If using the strategy to align leftmost to sort out the result, then the result should be 763053058, 770503058, 77061.

One of the solution to address it is:
1. Adding "0" from the rightmost of the normalized number (removal of the leading "+" + country code) to make the shorter number be always having the same length with that of the others, e.g. 77061 -> 770610000, but we might need to differentiate 77061 and 770610 from the original numbers.

2. Using the revised number in Bitmap and also creating a hash map with the key "the revised number" and the value "the normalized number". The structure of the current hash map in the code will be updated liking as below:
```
{
  "770610000": {"7706100": 4, "77061": 1, "770610": 2},
  "770503058": {"770503058": 2},
  ...
}
```
In Bitmap, it will always use "770610000" for all the original numbers "7706100", "77061", "770610". Always using "the revised number" and "the normalized number" to find the corresponding structure to increase the duplicates.

3. Going through Bitmap, sorting the number with ascending order of each bit set, and for every such number, and then using this number as the key of hash map to connect it and sorting the final results basing on the length of key of next hash map.
e.g. {"7706100": 4, "77061": 1, "770610": 2} ->   
```
77061: 1
770610: 2
7706100: 4
```

### Other considerations

#### The allowed maximum length of numbers
1. Right now the numbers whose lengths are longer than expected (e.g. 10 digits without the leading "+", country code, international prefix, national prefix) will be skipped in the sorting out results (these original numbers are printed during runtime but could be loggered).

2. If such numbers are allowed, then a bit update of Bitmap structure is needed, for example, int -> long should be enough.

3. "MAXPHONENUM" in the code could be simplified as "MAXALLOWEDLENTHOFPHONENUM".

#### The normalization of numbers
The current implementation could be further reviewed to see if there are any other cases that should be covered.

#### The size of input file becomes bigger and bigger
1. If the number of different phone numbers is not huge - i.e. many duplicates in the file  
The solution should handle it smoothly as the number is reading from buffer of input file. But we could make it a bit faster - splitting the file into smaller trunks and reading the numbers and setting them in parellel, and then sorting them out in the end.

2. If the number of different phone numbers is also huge

Either split the phone numbers into different parts (according to e.g. phone number prefix) and then sort in this way. Or likely the other algorithm (external merge sort) is more suitable in this case.

