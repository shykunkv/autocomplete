import match.PrefixMatches;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import trie.Trie;
import trie.Tuple;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {

    @Mock
    Trie trieMock;

    @InjectMocks
    PrefixMatches testMatch;


    @Test
    public void sizeInvokeTrieSizeTest() {
        testMatch.size();
        verify(trieMock).size();
    }

    @Test
    public void containsInvokeTrieContains() {
        testMatch.contains("test");
        verify(trieMock).contains(anyString());
    }

    @Test
    public void deleteInvokedTrieDeleteTest() {
        testMatch.delete("test");
        verify(trieMock).delete("test");
    }

    @Test
    public void addInvokeTrieAdd() {
        String[] strings = {"abc", "abcd", "abcde", "abcdef"};
        testMatch.add(strings);
        verify(trieMock, atLeast(1)).add(any(Tuple.class));
    }

    @Test
    public void addWithWrongInputDoesntInvokeTrieAdd() {
        String[] strings = {"ab", "ad", "a", ""};
        testMatch.add(strings);
        verify(trieMock, never()).add(any(Tuple.class));
    }

}
