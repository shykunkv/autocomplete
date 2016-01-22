import match.PrefixMatches;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;


public class InputFileTest extends Assert {

    static String[] input;

    @BeforeClass
    public static void init() throws IOException {
        Scanner sc = new Scanner(Paths.get("src/test/resources/words.txt"));
        int size = sc.nextInt();
        sc.nextLine();

        input = new String[size];
        int k = 0;

        while (sc.hasNext()) {
            sc.next();
            String curr = sc.next();
            input[k++] = curr;
        }
    }


    @Test
    public void testSizeWithInputFile() {
        PrefixMatches testMatcher = new PrefixMatches();
        int quantity = testMatcher.add(input);

        assertEquals("Size isn't equal to number of added words", quantity, testMatcher.size());
    }

}
