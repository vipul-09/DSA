import java.util.*;

class Solution {

    class TrieNode {
        TrieNode[] children = new TrieNode[26];

        int bestIndex = -1;
        int bestLength = Integer.MAX_VALUE;
    }

    TrieNode root = new TrieNode();

    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {

        // Build trie
        for (int i = 0; i < wordsContainer.length; i++) {
            insert(wordsContainer[i], i);
        }

        int[] ans = new int[wordsQuery.length];

        for (int i = 0; i < wordsQuery.length; i++) {
            ans[i] = query(wordsQuery[i]);
        }

        return ans;
    }

    private void insert(String word, int index) {

        TrieNode node = root;

        updateBest(node, word.length(), index);

        // insert reversed
        for (int i = word.length() - 1; i >= 0; i--) {

            int c = word.charAt(i) - 'a';

            if (node.children[c] == null) {
                node.children[c] = new TrieNode();
            }

            node = node.children[c];

            updateBest(node, word.length(), index);
        }
    }

    private void updateBest(TrieNode node, int length, int index) {

        if (length < node.bestLength ||
           (length == node.bestLength && index < node.bestIndex)) {

            node.bestLength = length;
            node.bestIndex = index;
        }
    }

    private int query(String word) {

        TrieNode node = root;

        // traverse reversed query
        for (int i = word.length() - 1; i >= 0; i--) {

            int c = word.charAt(i) - 'a';

            if (node.children[c] == null) {
                break;
            }

            node = node.children[c];
        }

        return node.bestIndex;
    }
}
