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

    // Формирует in-memory словарь слов. Метод принимает слово, строку, массив слов/строк. Если приходит строка, то она разбивается на слова по пробелам.
    // В словарь должны добавляться слова длиннее 2х символов. Предполагается что знаки пунктуации отсутствуют
    public int add(String... strings) {
        int prevSize = trie.size();
        for (String str: strings) {
            str = str.toLowerCase();

            if (str.indexOf(' ') != -1) {
                String[] words = str.split(" ");
                for (String word: words) {
                    if (word.length() > 2)
                        trie.add(new Tuple<Integer>(word, word.length()));
                }
            } else {
                if (str.length() > 2)
                    trie.add(new Tuple<Integer>(str, str.length()));
            }
        }
        return trie.size() - prevSize;
    }

    // есть ли слово в словаре
    public boolean contains(String word) {
        return trie.contains(word);
    }

    // удаляет слово из словаря
    public boolean delete(String word) {
        return trie.delete(word);
    }

    // к-во слов в словаре
    public int size() {
        return trie.size();
    }

    // если введенный pref длиннее или равен 2м символам, то возвращает набор слов k разных длин начиная с минимальной, и начинающихся с данного префикса pref.
    // Пример, даны слова следующей длины и pref='abc':
    // abc 3
    // abcd 4
    // abce 4
    // abcde 5
    // abcdef 6
    // - при k=1 возвращаются 'abc'
    // - при k=2 возвращаются 'abc', 'abcd', 'abce'
    // - при k=3 возвращаются 'abc', 'abcd', 'abce', 'abcde'
    // - при k=4 возвращаются 'abc', 'abcd', 'abce', 'abcde', 'abcdef'
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

                if (n == k + 1) break;

                result.add(next);
            }
        }

        return result;
    };

    // если введенный pref длиннее или равен 2м символам, то возвращает набор слов k=3 разных длин начиная с минимальной, и начинающихся с данного префикса pref.
    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }
}
