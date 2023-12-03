package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The engine schematic (your puzzle input) consists of a visual representation of the engine.
 * There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol,
 * even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 */
@Slf4j(topic = "Day3")
public class Day3 extends AdventOfCode2023Test {

    @Test
    void test_part1() throws IOException, URISyntaxException {
        List<String> lines = loadInput("day3.txt");

        int result = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            log.info(line);

            char[] lineAsChars = line.toCharArray();

            for(int j = 0; j < lineAsChars.length; j++ ) {
                char c = line.charAt(j);
                int value = 0;

                if (Character.isDigit(c)) {
                    //find full digit
                    if (Character.isDigit(line.charAt(j + 1))) {
                        if (j < lineAsChars.length - 2 && Character.isDigit(line.charAt(j + 2))) {
                            value = Integer.parseInt(line.substring(j, j + 3));

                        } else {
                            value = Integer.parseInt(line.substring(j, j + 2));
                        }
                    } else {
                        value = Integer.parseInt(String.valueOf(c));
                    }

                    //is it next to symbol?
                    boolean isNextToSymbol = isNextToSymbol(lines, j, i, String.valueOf(value));

                    if(isNextToSymbol) {
                        result += value;
                        log.info("adding {} result {}", value, result);
                    }
                    j += String.valueOf(value).length();
                }
            }

        }

        assertEquals(404915, result);
    }

    private boolean isNextToSymbol(List<String> lines, int pos, int linenumber, String value) {
        String currentLine = lines.get(linenumber);
        //next to it?
        if(pos > 1 && isSymbol( currentLine.charAt(pos - 1) )) return true;

        //in front?
        if(pos < currentLine.length() - value.length() && isSymbol(currentLine.charAt(pos + value.length()))) return true;


        if(linenumber > 1) {


            //check diagonals
            String lineAbove = lines.get(linenumber - 1);
            if(pos > 1) {
                if(isSymbol(lineAbove.charAt(pos - 1))) return true;
            }
            if(pos < currentLine.length() - value.length()) {
                if(isSymbol(lineAbove.charAt(pos + value.length()))) return true;
            }

            //and above
            for(int i = pos; i < pos + value.length(); i++) {
                if(isSymbol(lineAbove.charAt(i))) return true;
            }
        }

        if(linenumber < lines.size() -1) {

            String lineBelow = lines.get(linenumber + 1);

            //check diagonals
            if(pos > 1) {
                if(isSymbol(lineBelow.charAt(pos - 1))) return true;
            }
            if(pos < currentLine.length() - value.length()) {
                if(isSymbol(lineBelow.charAt(pos + value.length()))) return true;
            }

            //and below
            for(int i = pos; i < pos + value.length(); i++) {
                if(isSymbol(lineBelow.charAt(i))) return true;
            }
        }

        return false;
    }

    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }


}
