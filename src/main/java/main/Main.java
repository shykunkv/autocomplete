package main;

import match.PrefixMatches;

public class Main {

    public static void main(String[] args) {
        String[] str = {"abc", "abcd", "abce", "abcde", "abcdef", "abcc", "abcaa"};
        PrefixMatches test = new PrefixMatches();

        test.add(str);
        //System.out.println(test.size());

        System.out.println(test.wordsWithPrefix("abc", 10));
    }

}
