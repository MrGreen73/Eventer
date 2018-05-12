package com.ivan.eventer.model;

import java.util.ArrayList;
import java.util.List;

// http://en.wikipedia.org/wiki/Bitap_algorithm
public class Bitap {


    public static List<Integer> find(String doc, String pattern, int k) {

        // Range of the alphabet
        // 128 is enough if we stay in the ASCII range (0-127)
        int alphabetRange = 1024;
        int firstMatchedText = -1;
        // Indexes where the pattern was found
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        long[] r = new long[k + 1];

        long[] patternMask = new long[alphabetRange];
        for (int i = 0; i <= k; i++) {
            r[i] = 1;
        }

        // Example : The mask for the letter 'e' and the pattern "hello" is
        // 11101 (0 means this letter is at this place in the pattern)
        for (int i = 0; i < pattern.length(); ++i) {
            patternMask[(int) pattern.charAt(i)] |= 1 << i;
        }
        int i = 0;

        while (i < doc.length()) {

            long old = 0;
            long nextOld = 0;

            for (int d = 0; d <= k; ++d) {
                // Three operations of the Levenshtein distance
                long sub = (old | (r[d] & patternMask[doc.charAt(i)])) << 1;
                long ins = old | ((r[d] & patternMask[doc.charAt(i)]) << 1);
                long del = (nextOld | (r[d] & patternMask[doc.charAt(i)])) << 1;
                old = r[d];
                r[d] = sub | ins | del | 1;
                nextOld = r[d];
            }
            // When r[k] is full of zeros, it means we matched the pattern
            // (modulo k errors)
            if (0 < (r[k] & (1 << pattern.length()))) {
                // The pattern "aaa" for the document "bbaaavv" with k=2
                // will slide from "bba","baa","aaa","aav","avv"
                // Because we allow two errors !
                // This test keep only the first one and skip all the others.
                // (We can't skip by increasing i otherwise the r[d] will be
                // wrong)
                if ((firstMatchedText == -1) || (i - firstMatchedText > pattern.length())) {
                    firstMatchedText = i;
                    indexes.add(firstMatchedText - pattern.length() + 1);
                }
            }
            i++;
        }

        return indexes;
    }
}