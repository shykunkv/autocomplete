import match.PrefixMatches;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrefixMatchesTest extends Assert {

    @Test
    public void addStringsWithUnacceptableLength() {
        PrefixMatches testMatcher = new PrefixMatches();
        String[] strings = {"", "a", "aa"};
        int expectedSize = 0;

        testMatcher.add(strings);

        assertEquals("Must not add words with length smaller than 3", expectedSize, testMatcher.size());
    }


    @Test
    public void addStringsWithAcceptableLength() {
        PrefixMatches testMatcher = new PrefixMatches();
        String[] strings = {"abc", "abcd", "abcdef"};

        int expectedSize = testMatcher.add(strings);

        assertEquals("Matcher must add words longer than 2", expectedSize, testMatcher.size());
    }


    @Test
    public void addTwoEqualsWords() {
        PrefixMatches testMatcher = new PrefixMatches();
        String[] strings = {"abc", "abc"};

        int expectedSize = testMatcher.add(strings);

        assertEquals("Matcher must not add words that already exist in trie", expectedSize, testMatcher.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void wordsWithUnexistingPrefix() {
        PrefixMatches testMatcher = new PrefixMatches();
        String[] strings = {"abc", "cde", "fgh"};

        testMatcher.add(strings);
        Iterator<String> it = testMatcher.wordsWithPrefix("zx").iterator();

        it.next();
    }


    public void wordsWithExistingPrefix() {
        PrefixMatches testMatcher = new PrefixMatches();
        String[] strings = {"abc", "abcd", "abcdef"};

        testMatcher.add(strings);
        Iterator<String> it = testMatcher.wordsWithPrefix("ab").iterator();

        assertTrue(it.hasNext());
    }
}
