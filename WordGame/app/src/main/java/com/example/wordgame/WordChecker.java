package com.example.wordgame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class WordChecker {
    public static boolean checkWord(String word) {
        // System.out.println(word);
        try {
            BufferedReader in = new BufferedReader(new FileReader();
            String str;
            while ((str = in.readLine()) != null) {
                if (str.indexOf(word) != -1) {
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {
        }
        return false;
    }
}