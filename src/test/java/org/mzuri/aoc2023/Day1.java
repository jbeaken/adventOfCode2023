package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
public class Day1 {


    @Test
    void test1() throws IOException, URISyntaxException {
        List<String> calibrations = readCalibrations();

        int total = 0;

        for (String line : calibrations) {
            Character first = null;
            Character last = null;

            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    if (first == null) {
                        first = c;
                    }
                    last = c;
                }
            }

            if (last == null) {
                last = first;
            }
            String toAdd = first.toString() + last.toString();
            total += Integer.parseInt(toAdd);

            log.info("line {} with toAdd : {}  with total : {}", line, toAdd, total);


        }
        log.info("Finished with {}", total);
    }

    @Test
    void test2() throws IOException, URISyntaxException {

        List<String> calibrations = readCalibrations();

        Map<String, Integer> numbers = Map.of(
                "one", 1,
                "two", 2,
                "three", 3,
                "four", 4,
                "five", 5,
                "six", 6,
                "seven", 7,
                "eight", 8,
                "nine", 9);

        int total = 0;

        for (String line : calibrations) {
            //pos, value
            TreeMap<Integer, Integer> numbersInLine = new TreeMap<>();

            //check for spelt out numbers
            for (String number : numbers.keySet()) {
                int firstIndexOf = line.indexOf(number);
                int lastIndexOf = line.lastIndexOf(number, firstIndexOf);
                if (firstIndexOf != -1) {
                    //got one
                    numbersInLine.put(firstIndexOf, numbers.get(number));
                }
                if (lastIndexOf != -1 && lastIndexOf != firstIndexOf) {
                    //got one
                    numbersInLine.put(lastIndexOf, numbers.get(number));
                }
            }

            //check for digits
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (Character.isDigit(c)) {
                    //got one
                    numbersInLine.put(i, Character.getNumericValue(c));
                }
            }

            Integer firstNumber = numbersInLine.firstEntry().getValue();
            Integer lastNumber = numbersInLine.lastEntry().getValue();

            String toAdd = "" + firstNumber + lastNumber;
            total += Integer.parseInt(toAdd);

            log.debug("line {} toAdd : {} with total : {}", line, toAdd, total);
        }

        log.info("Finished with {}", total);
    }

    private static List<String> readCalibrations() throws URISyntaxException, IOException {
        URI uri = Day1.class.getResource("../../../day1/input.txt").toURI();
        return Files.readAllLines(Path.of(uri));
    }
}
