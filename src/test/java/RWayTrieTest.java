import org.junit.Assert;
import org.junit.Test;
import trie.RWayTrie;
import trie.Tuple;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RWayTrieTest extends Assert {

    @Test
    public void testForEmptySize() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        int expectedSize = 0;

        assertEquals("Empty size test", expectedSize, testTrie.size());
    }

    @Test
    public void testSizeWithEqualWords() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        String a = new String("abc");
        Tuple<Integer> tuple = new Tuple(a, a.length());
        int expectedSize = 1;

        testTrie.add(tuple);
        testTrie.add(tuple);
        testTrie.add(tuple);

        assertEquals("Size must be 1", expectedSize, testTrie.size());
    }


    @Test
    public void testSizeWithDifferentWords() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        String a = new String("first");
        String b = new String("second");
        int expectedSize = 2;

        testTrie.add(new Tuple<Integer>(a, a.length()));
        testTrie.add(new Tuple<Integer>(b, b.length()));

        assertEquals("Size must be 2 with two different words", expectedSize, testTrie.size());
    }

    @Test
    public void testSizeWithDeleteFromTrie() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        String a = new String("first");
        String b = new String("second");
        int expectedSize = 0;

        testTrie.add(new Tuple<Integer>(a, a.length()));
        testTrie.add(new Tuple<Integer>(b, b.length()));
        testTrie.delete(a);
        testTrie.delete(b);

        assertEquals("Size must change after delete words", expectedSize, testTrie.size());
    }



    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testAddWithWrongInput() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        String wrong = "123";

        testTrie.add(new Tuple<Integer>(wrong, wrong.length()));
    }


    @Test
    public void testContainsOnExistingWord() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        String existingWord = "hello";

        testTrie.add(new Tuple<Integer>(existingWord, existingWord.length()));

        assertTrue("Contains fails on existing item", testTrie.contains(existingWord));
    }

    @Test
    public void testContainsOnUnexistingWord() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();

        assertFalse("Contains fails on existing item", testTrie.contains("unexisting"));
    }

    @Test
    public void testDeleteExistingWord() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        String word = "test";
        testTrie.add(new Tuple<Integer>(word, word.length()));
        assertTrue("Fails when delete existing word", testTrie.delete(word));
    }

    @Test
    public void testDeleteUnexistingWord() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        String word = "test";

        testTrie.add(new Tuple<Integer>(word, word.length()));

        assertFalse("True when delete unexisting word", testTrie.delete("hello"));
    }

    @Test
    public void testWordsWithExistingPrefix() {
        String str = new String("string");
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        testTrie.add(new Tuple<Integer>(str, str.length()));

        Iterator<String> it = testTrie.wordsWithPrefix("st").iterator();

        assertTrue(it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testWordsWithUnExistingPrefix() {
        String str = new String("hello");
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        Tuple<Integer> tuple = new Tuple<Integer>(str, str.length());

        testTrie.add(tuple);

        Iterator<String> it = testTrie.wordsWithPrefix("aaa").iterator();
        it.next();
    }


}
