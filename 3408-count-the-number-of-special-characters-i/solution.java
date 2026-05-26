import java.util.*;

class Solution {

    public int numberOfSpecialChars(String word) {

        Set<Character> lower = new HashSet<>();
        Set<Character> upper = new HashSet<>();

        // Store lowercase and uppercase letters
        for (char ch : word.toCharArray()) {

            if (Character.isLowerCase(ch)) {
                lower.add(ch);
            } else {
                upper.add(Character.toLowerCase(ch));
            }
        }

        int count = 0;

        // Count common letters
        for (char ch : lower) {

            if (upper.contains(ch)) {
                count++;
            }
        }

        return count;
    }
}
