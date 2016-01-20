package trie;


import javafx.util.Pair;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class RWayTrie<T> implements Trie<T> {

    private static int R = 26;
    private TrieNode root;
    private int size;

    public RWayTrie() {
        size = 0;
    }

    private class TrieNode {
        private T value;
        private TrieNode[] next;

        private TrieNode() {
            next = (TrieNode[]) new RWayTrie.TrieNode[R];
        }
    }


    public void add(Tuple<T> tuple) {
        root = add(root, tuple.getTerm(), tuple.getWeight(), 0);
    }

    private TrieNode add(TrieNode node, String key, T value, int index) {
        if (node == null) node = new TrieNode();

        if (index == key.length()) {
            if (node.value == null) size++;
            node.value = value;
            return node;
        }

        char ch = key.charAt(index);
        node.next[ch - 'a'] = add(node.next[ch - 'a'], key, value, index + 1);
        return node;
    }

    public T get(String key) {
        TrieNode node = get(root, key, 0);
        if (node == null) return null;
        return node.value;
    }

    private TrieNode get(TrieNode node, String key, int index) {
        if (node == null) return null;
        if (index == key.length()) return node;

        char ch = key.charAt(index);
        return get(node.next[ch - 'a'], key, index + 1);
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

    private TrieNode delete(TrieNode node, String key, int index) {
        if (node == null) return null;

        if (index == key.length()) {
            node.value = null;
        } else {
            char ch = key.charAt(index);
            node.next[ch - 'a'] = delete(node.next[ch - 'a'], key, index + 1);
        }

        if (node.value != null) return node;

        for (TrieNode n: node.next) {
            if (n != null) return node;
        }

        return null;
    }

    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    public Iterable<String> wordsWithPrefix(String pref) {

        return new Iterable<String>() {

            public Iterator<String> iterator() {
                return new Iterator<String>() {

                    private Queue<Tuple<TrieNode>> q = new LinkedList();
                    private String lastPref;

                    {
                        q.add(new Tuple(pref, get(root, pref, 0)));
                    }

                    public boolean hasNext() {

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
                            if (lastPref == null) return false;
                            return true;
                        }

                        return true;
                    }

                    public String next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }

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
