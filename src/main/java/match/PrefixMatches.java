package match;

import trie.RWayTrie;
import trie.Trie;
import trie.Tuple;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrefixMatches {

    private Trie trie;

    private static final int PREFIX_MIN_LENGTH = 3;
    private static final int DEFAULT_LENGHTS_NUM = 3;

    public PrefixMatches() {
        trie = new RWayTrie<Integer>();
    }

    public int add(final String... strings) {
        int prevSize = trie.size();
        for (String str: strings) {
            str = str.toLowerCase();

            if (str.indexOf(' ') != -1) {
                String[] words = str.split(" ");
                for (String word: words) {
                    if (word.length() >= PREFIX_MIN_LENGTH) {
                        trie.add(new Tuple<Integer>(word, word.length()));
                    }
                }
            } else {
                if (str.length() >= PREFIX_MIN_LENGTH) {
                    trie.add(new Tuple<Integer>(str, str.length()));
                }
            }
        }
        return trie.size() - prevSize;
    }

    public boolean contains(final String word) {
        return trie.contains(word);
    }

    public boolean delete(final String word) {
        return trie.delete(word);
    }

    public int size() {
        return trie.size();
    }

    public Iterable<String> wordsWithPrefix(final String pref, final int k) {
        return new Iterable<String>() {

            public Iterator<String> iterator() {


                return new Iterator<String>() {

                    private Iterator<String> trieIt = trie.wordsWithPrefix(pref).iterator();
                    private int changes = 0;
                    private int lastLen = 0;


                    public boolean hasNext() {
                        return trieIt.hasNext() && changes != k;
                    }

                    public String next() {
                        String curr = trieIt.next();

                        if (curr.length() > lastLen) {
                            lastLen = curr.length();
                            changes++;
                        }

                        if (changes == k) {
                            throw new NoSuchElementException();
                        }

                        return curr;
                    }

                    public void remove () {
                        trieIt.remove();
                    }
                };
            }
        };
    }

    public Iterable<String> wordsWithPrefix(final String pref) {
        return wordsWithPrefix(pref, DEFAULT_LENGHTS_NUM);
    }
}
