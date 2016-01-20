package trie;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;


/**
 * Implementation of the trie
 * @param <T> weights type
 */
public class RWayTrie<T> implements Trie<T> {

    private static int R = 26; // number of possible childs for every node
    private TrieNode root; // trie root
    private int size; // number of words in trie

    public RWayTrie() {
        size = 0;
    }


    private class TrieNode {
        /**
         * Mark that some nodes are last letters in words
         */
        private T value;
        /**
         * Next letters
         */
        private TrieNode[] next;

        private TrieNode() {
            next = (TrieNode[]) new RWayTrie.TrieNode[R];
        }
    }


    public void add(Tuple<T> tuple) {
        root = add(root, tuple.getTerm(), tuple.getWeight(), 0);
    }

    private TrieNode add(TrieNode node, String key, T value, int depth) {
        if (node == null) {
            node = new TrieNode();
        }

        if (depth == key.length()) {
            if (node.value == null) {
                size++;
            }
            node.value = value;
            return node;
        }

        char ch = key.charAt(depth);
        node.next[ch - 'a'] = add(node.next[ch - 'a'], key, value, depth + 1);
        return node;
    }

    private T get(String key) {
        TrieNode node = get(root, key, 0);
        if (node == null) return null;
        return node.value;
    }

    private TrieNode get(TrieNode node, String key, int depth) {
        if (node == null) return null;
        if (depth == key.length()) return node;

        char ch = key.charAt(depth);
        return get(node.next[ch - 'a'], key, depth + 1);
    }

    public boolean contains(String word) {
        return get(word) == null ? false : true;
    }


    public boolean delete(String word) {
        int prevSize = size;
        root = delete(root, word, 0);
        if (prevSize > size) return true;
        else return false;
    }

    private TrieNode delete(TrieNode node, String key, int depth) {
        if (node == null) return null;

        if (depth == key.length()) {
            node.value = null;
            size--;
        } else {
            char ch = key.charAt(depth);
            node.next[ch - 'a'] = delete(node.next[ch - 'a'], key, depth + 1);
        }

        if (node.value != null) {
            return node;
        }

        for (TrieNode n: node.next) {
            if (n != null) return node;
        }

        return null;
    }

    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    public Iterable<String> wordsWithPrefix(final String pref) {

        return new Iterable<String>() {

            public Iterator<String> iterator() {
                return new Iterator<String>() {

                    private Queue<Tuple<TrieNode>> q = new LinkedList();
                    private String lastPref;

                    {
                        q.add(new Tuple(pref, get(root, pref, 0)));
                    }


                    public boolean hasNext() {

                        //if we have not prefix to return - start search for next prefix
                        if (lastPref == null) {
                            while (!q.isEmpty()) {
                                Tuple<TrieNode> currTuple = q.poll();
                                TrieNode currNode = currTuple.getWeight();
                                String currPref = currTuple.getTerm();
                                if (currNode == null) continue;

                                for (char c = 0; c < R; c++) {
                                    if (currNode.next[c] != null) {
                                        q.add(new Tuple(currPref + (char) (c + 'a'), currNode.next[c]));
                                    }
                                }
                                if (currNode.value != null) {
                                    lastPref = currPref;
                                    break;
                                }
                            }

                            if (lastPref == null) return false; //if we traverse whole trie
                            return true;
                        }

                        return true;
                    }

                    public String next() {

                        if (!this.hasNext()) { //nothing to return
                            throw new NoSuchElementException();
                        }

                        //return current prefix
                        String res = lastPref;
                        lastPref = null;
                        return res;
                    }

                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                };
            }
        };
    }


    public int size() {
        return this.size;
    }

}
