package demo4;

import java.util.Arrays;

public class BoyerMooreTextSearchStrategy extends AbstractTextSearchStrategy {
    private static final int CHARACTER_CACHE_SIZE = 256;

    private int subtextLength;
    private int lastSubtextIndex;
    private char[] subtextCharsUpper;
    private char[] subtextCharsLower;

    private int[] shiftTable = new int[CHARACTER_CACHE_SIZE];

    public void setSubtext(String subtext) {
        // record the length of the subtext
        this.subtextLength = subtext.length();

        // record the last index of the subtext
        this.lastSubtextIndex = this.subtextLength - 1;

        // extract the upper case version of the subtext into an easily accessible char[]
        this.subtextCharsUpper = subtext.toUpperCase().toCharArray();
        this.subtextCharsLower = subtext.toLowerCase().toCharArray();

        // initialize the shift table with the maximum shift -> the length of the subtext
        Arrays.fill(this.shiftTable, 0, this.shiftTable.length, this.subtextLength);

        // for each character in the subtext, calculate its maximum safe shift distance
        for (int i = 0; i < this.lastSubtextIndex; i++) {
            // adjust the shift table entry for the upper case letter
            this.shiftTable[this.subtextCharsUpper[i] % CHARACTER_CACHE_SIZE] = this.lastSubtextIndex - i;

            // if the upper case letter isn't the same as the lower case letter then
            // adjust the shift table entry for the lower case letter as well
            if (this.subtextCharsUpper[i] != this.subtextCharsLower[i])
                this.shiftTable[this.subtextCharsLower[i] % CHARACTER_CACHE_SIZE] = this.lastSubtextIndex - i;
        }
    }

    public int indexOf(String text) {
        return indexOf(text.toCharArray());
    }

    public int indexOf(char[] text) {
        // ensure we are in a state to search the text
        if (this.subtextCharsUpper == null) {
            throw new IllegalStateException("setSubtext must be called with a valid value before this method can operate");
        }

        // initialize some variables modified within the text search loop
        int textPosition = this.lastSubtextIndex;
        char textChar = ' ';
        int subtextPosition;
        final int textLength = text.length;

        // search through text until the textPosition exceeds the textLength
        while (textPosition < textLength) {
            // reset the comparison position within the subtext to the END of the subtext
            subtextPosition = this.lastSubtextIndex;

            if (subtextPosition >= 0) {
                // locate the character in the text to be compared against
                textChar = text[textPosition];

                // check for matching character from the end to the beginning of the subtext
                while (subtextPosition >= 0 &&
                        (this.subtextCharsLower[subtextPosition] == textChar ||
                                this.subtextCharsUpper[subtextPosition] == textChar)) {
                    // the text char and subtext char matched, so shift both positions left and recompare
                    subtextPosition--;
                    textPosition--;

                    // calculate the next character of the text to compare
                    if (textPosition != -1) {
                        textChar = text[textPosition];
                    }
                }
            }

            // subtextPosition == -1 indicates we have successfully matched the
            // entire subtext from last char to first char so return the matching index
            if (subtextPosition == -1) {
                return textPosition + 1;
            }

            // otherwise we had a mismatch, so calculate the maximum safe shift
            // and move ahead to the next search position in the text
            textPosition += Math.max(this.shiftTable[textChar % CHARACTER_CACHE_SIZE], this.subtextLength - subtextPosition);
        }

        // if we fall out of the search loop then we couldn't find the subtext
        return -1;
    }
}
