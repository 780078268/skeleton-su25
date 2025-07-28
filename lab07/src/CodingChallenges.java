import java.lang.reflect.Array;
import java.util.*;

public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        // TODO
        Set<Integer> seen = new HashSet<>();
        int N = values.length;
        for (int value : values) {
            seen.add(value);
        }
        for(int i = 0; i <= N; i++) {
            if(!seen.contains(i)) {
                return i;
            }
        }
        return N;
    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        // TODO
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        if(c1.length != c2.length) return false;
        for(int i = 0; i < c1.length; i++) {
            if(c1[i] != c2[i]) return false;
        }
        return true;
    }
}
