package ru.learning.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

    public static void main(String[] args) {
        String[] langs = {"английский", "французский", "русский", "немецкий"};

        /*
        List<String> languages = new ArrayList<String>();
        languages.add("Java");
        languages.add("Python");
        languages.add(null);
         */

        List<String> languages = Arrays.asList("Java", "Python", null, "C#");

        for (String l : languages) {
            if (l != null) {
                System.out.println("Я изучаю " + l);
            } else {
                System.out.println("Я ничего не изучаю");
            }
        }
    }
}
