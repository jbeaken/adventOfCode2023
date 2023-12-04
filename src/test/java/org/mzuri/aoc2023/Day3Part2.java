package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The engine schematic (your puzzle input) consists of a visual representation of the engine.
 * There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol,
 * even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 */
@Slf4j(topic = "Day3")
public class Day3Part2 extends AdventOfCode2023Test {

    List<String> lines;

    @BeforeEach
    void beforeEach() throws URISyntaxException, IOException {
        lines = loadInput("day3.txt");
    }

    record NumberInInput(Integer lineNumber, Integer position, Integer value) {}

    @Test
    void test()  {
        int result = 0;

        List<NumberInInput> numberInInputs = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
//            log.info(line);
//            log.info("" + i);

            char[] lineAsChars = line.toCharArray();

            for (int j = 0; j < lineAsChars.length; j++) {
                char c = line.charAt(j);

                if (Character.isDigit(c)) {
                    //find full digit
                    String value;
                    if (Character.isDigit(line.charAt(j + 1))) {
                        if (j < lineAsChars.length - 2 && Character.isDigit(line.charAt(j + 2))) {
                            value = line.substring(j, j + 3);
                        } else {
                            value = line.substring(j, j + 2);
                        }
                    } else {
                        value = String.valueOf(c);
                    }

                    numberInInputs.add(new NumberInInput(i, j, Integer.parseInt(value)));

                    j += value.length();
                }
            }
        }

        //now find *
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            char[] lineAsChars = line.toCharArray();

            for (int j = 0; j < lineAsChars.length; j++) {
                char c = line.charAt(j);
                if (c != '*') {
                    continue;
                }

                //find adjacent numbers
                List<NumberInInput> adjacentNumbers = new ArrayList<>();
                for(NumberInInput numberInInput : numberInInputs) {
                    if(isNextTo(i, j, numberInInput)) {
                        adjacentNumbers.add(numberInInput);
                    }
                }

                if(adjacentNumbers.size() == 2) {
                    log.debug("Got for line {} position {} {} {}", i + 1, j, adjacentNumbers.get(0).value, adjacentNumbers.get(1).value);
                    result = result + (adjacentNumbers.get(0).value * adjacentNumbers.get(1).value);
                } else {
                    log.debug("skip {} {}", i + 1, j);
                }
            }
        }

        log.info("result {}", result);

        assertEquals(79844424, result);
    }

    private boolean isNextTo(int linenumber, int position, NumberInInput numberInInput) {
        //short circuit
        if(numberInInput.lineNumber < linenumber - 1) return false;
        if(numberInInput.lineNumber > linenumber + 1) return false;

        int numberLength = String.valueOf(numberInInput.value).length();
        int numberStart = numberInInput.position;
        int numberEnd = numberStart + numberLength - 1;

        //same line, behind or ahead
        if(numberInInput.lineNumber == linenumber) {
            return numberEnd == position - 1 || numberStart == position + 1;
        }

        //line above or below
        return numberStart <= position + 1 && numberEnd >= position - 1;
    }

}
