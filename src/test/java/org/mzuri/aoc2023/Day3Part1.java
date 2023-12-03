package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The engine schematic (your puzzle input) consists of a visual representation of the engine.
 * There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol,
 * even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 */
@Slf4j(topic = "Day3")
public class Day3Part1 extends AdventOfCode2023Test {

    List<String> lines;

    @BeforeEach
    void beforeEach() throws URISyntaxException, IOException {
        lines = loadInput("day3.txt");
    }

    record CharacterToCheck(Integer lineNumber, Integer position){}

    @Test
    void test() {
        int result = 0;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            log.info(line);

            char[] lineAsChars = line.toCharArray();

            for(int j = 0; j < lineAsChars.length; j++ ) {
                char c = line.charAt(j);
                int value;

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
                    boolean isNextToSymbol = isNextToSymbol(j, i, String.valueOf(value));

                    if(isNextToSymbol) {
                        result += value;
                        log.info("adding {} result {}", value, result);
                    }
                    j += String.valueOf(value).length();
                }
            }

        }

        assertEquals(535235, result);
    }

    private boolean isNextToSymbol(int pos, int linenumber, String value) {
        List<CharacterToCheck> characterToChecks = new ArrayList<>();

        //next to it?
        characterToChecks.add(new CharacterToCheck(linenumber, pos - 1));
        characterToChecks.add(new CharacterToCheck(linenumber, pos + value.length()));

        //above below including diagonals
        for(int i = pos - 1; i < pos + value.length() + 1; i++) {
            characterToChecks.add(new CharacterToCheck(linenumber - 1, i));
            characterToChecks.add(new CharacterToCheck(linenumber + 1, i));
        }

        Optional<CharacterToCheck> characterIsSymbol = characterToChecks.stream().filter(this::isSymbol).findFirst();

        return characterIsSymbol.isPresent();
    }

    private boolean isSymbol(CharacterToCheck characterToCheck) {
        //check in range
        if(characterToCheck.lineNumber < 0) return false;
        if(characterToCheck.lineNumber > lines.size() - 1) return false;

        if(characterToCheck.position < 0) return false;
        if(characterToCheck.position > lines.get(0).length() - 1) return false;

        char c = lines.get(characterToCheck.lineNumber).charAt(characterToCheck.position);
        return isSymbol(c);
    }


    private boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

}
