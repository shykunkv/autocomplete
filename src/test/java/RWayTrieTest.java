import org.junit.Assert;
import org.junit.Test;
import trie.RWayTrie;
import trie.Tuple;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RWayTrieTest extends Assert {

    @Test
    public void testForEmptySize() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        assertEquals("Empty size test", 0, testTrie.size());
    }

    @Test
    public void testSizeWithEqualWords() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        String a = new String("abc");
        testTrie.add(new Tuple<Integer>(a, a.length()));
        assertEquals("Size must be 1", 1, testTrie.size());
        testTrie.add(new Tuple<Integer>(a, a.length()));
        testTrie.add(new Tuple<Integer>(a, a.length()));
        assertEquals("Size must be 1", 1, testTrie.size());
    }


    @Test
    public void testSizeWithDeleteFromTrie() {
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();
        String a = new String("first");
        String b = new String("second");
        testTrie.add(new Tuple<Integer>(a, a.length()));
        testTrie.add(new Tuple<Integer>(b, b.length()));
        assertEquals("", 2, testTrie.size());
        testTrie.delete(a);
        assertEquals("Size doesn't change after delete", 1, testTrie.size());
    }
    @Test
    public void testSizeWithInputFile() {
        Scanner sc = null;
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();

        try {
             sc = new Scanner(Paths.get("src/test/resources/words.txt"));
        } catch(IOException ex) {
            assertTrue("IOException when open file", false);
        }

        sc.nextLine();
        int realSize = 0;

        while (sc.hasNext()) {
            sc.next();
            String curr = sc.next();
            realSize++;
            testTrie.add(new Tuple<Integer>(curr, curr.length()));
        }

        assertEquals("Size isn't equal to number of words in input file", realSize, testTrie.size());
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
        String[] strings = {"abc", "abcd", "abcdef", "abce", "abcdefg"};
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();

        for (String s: strings) {
            Tuple<Integer> tuple = new Tuple<Integer>(s, s.length());
            testTrie.add(tuple);
        }

        Iterator<String> it = testTrie.wordsWithPrefix("abcd").iterator();
        int n = 0;
        while (it.hasNext()) {
            n++;
            it.next();
        }

        assertEquals("Wrong number of word with prefix", 3, n);
    }

    @Test(expected = NoSuchElementException.class)
    public void testWordsWithUnExistingPrefix() {
        String[] strings = {"abc", "abcd", "abcdef", "abce", "abcdefg"};
        RWayTrie<Integer> testTrie = new RWayTrie<Integer>();

        for (String s: strings) {
            Tuple<Integer> tuple = new Tuple<Integer>(s, s.length());
            testTrie.add(tuple);
        }

        Iterator<String> it = testTrie.wordsWithPrefix("aaa").iterator();
        it.next();
    }


}
