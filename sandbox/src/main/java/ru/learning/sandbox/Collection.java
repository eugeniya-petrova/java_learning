package ru.learning.sandbox;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.reverse;

public class Collection {

    public static void main(String[] args) {
        String[] langs = {"английский", "французский", "русский", "немецкий"};

        /*
        List<String> languages = new ArrayList<String>();
        languages.add("Java");
        languages.add("Python");
        languages.add(null);
         */

        List<String> languages = Arrays.asList("Java", "Python", null, "C#");
        System.out.println("Языки в обратном порядке:");

        reverse(languages);
        for (String l : languages) {
            if (l != null) {
                System.out.println("Я изучаю " + l);
            } else {
                System.out.println("Я ничего не изучаю");
            }
        }

        System.out.println();
        System.out.println("А теперь наоборот:");

        reverse(languages);
        for (String l : languages) {
            if (l != null) {
                System.out.println("Я изучаю " + l);
            } else {
                System.out.println("Я ничего не изучаю");
            }
        }
    }
}
