package match;

import trie.RWayTrie;
import trie.Trie;
import trie.Tuple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrefixMatches {

    private Trie trie;

    public PrefixMatches() {
        trie = new RWayTrie<Integer>();
    }

    public int add(String... strings) {
        int prevSize = trie.size();
        for (String str: strings) {
            str = str.toLowerCase();

            if (str.indexOf(' ') != -1) {
                String[] words = str.split(" ");
                for (String word: words) {
                    if (word.length() > 2) {
                        trie.add(new Tuple<Integer>(word, word.length()));
                    }
                }
            } else {
                if (str.length() > 2) {
                    trie.add(new Tuple<Integer>(str, str.length()));
                }
            }
        }
        return trie.size() - prevSize;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public int size() {
        return trie.size();
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        List<String> result = new ArrayList();
        int n = 0;
        int lastLength = 0;

        if (pref.length() >= 2) {

            Iterator<String> it = trie.wordsWithPrefix(pref).iterator();
            while (it.hasNext()) {
                String next = it.next();

                if (next.length() > lastLength) {
                    n++;
                    lastLength = next.length();
                }

                if (n == k + 1) {
                    break;
                }

                result.add(next);
            }
        }

        return result;
    };

    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }
}
