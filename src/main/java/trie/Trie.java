package trie;

/**
 * Represent prefix tree to effective word search
 * @param <T> type of words weight
 */
public interface Trie<T> {

    /**
     * Add pair (word, weight) to trie
     * @param tuple pair to add
     */
    public void add(Tuple<T> tuple);

    /**
     * Check is there a word in trie
     * @param word word for search
     * @return true if there is such word in trie (false in other case)
     */
    public boolean contains(String word);

    /**
     * Delete word from trie
     * @param word word to delete
     * @return true if word was deleted from trie and false if didn't
     */
    public boolean delete(String word);


    /**
     * Get set of all words in trie
     * @return set of all words in trie
     */
    public Iterable<String> words();

    /**
     * Get set of all words in trie with specific prefix
     * @param pref prefix for words
     * @return set of words
     */
    public Iterable<String> wordsWithPrefix(String pref);

    /**
     * Get size (number of words) of the trie
     * @return trie size
     */
    public int size();
}
