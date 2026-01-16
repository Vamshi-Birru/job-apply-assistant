package com.vamshi.jobapply.worker.util;

public class TextNormalizer {
    public static String normalizer(String input){
        if(input == null) return "";

        return input
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]","")
                .replaceAll("\\s+"," ")
                .trim();
    }
}
