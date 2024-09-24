package com.example.srcScanner;

public class RuleParser {

    public static void parseRule(String line) {
        // Read attribut
        int idx;
        StringBuilder attributName = new StringBuilder();
        for (idx = 0; idx < line.length() && line.charAt(idx) != '('; idx++) {
            attributName.append(line.charAt(idx));
        }

        System.out.println(attributName.toString());

        // Reade value
        StringBuilder value = new StringBuilder();
        for (idx = idx+1; idx < line.length() && line.charAt(idx) != ')'; idx++) {
            value.append(line.charAt(idx));
        }

        System.out.println(value.toString());


    }

    public static

}
