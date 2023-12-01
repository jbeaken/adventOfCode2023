package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
public class Day1 {


    @Test
    void test1() throws IOException, URISyntaxException {
        URI uri = Day1.class.getResource("../../../day1/input.txt").toURI();
        List<String> calibrations = Files.readAllLines(Path.of(uri));

        Integer total = 0;
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
            total += Integer.valueOf(toAdd);

            log.info("line {} with toAdd : {}  with total : {}", line, toAdd, total);


        }
        log.info("Finished with {}", total);
    }

    @Test
    void test2() throws IOException, URISyntaxException {

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
        URI uri = Day1.class.getResource("../../../day1/input.txt").toURI();
        List<String> calibrations = Files.readAllLines(Path.of(uri));

        int total = 0;

        for (String line : calibrations) {

            //pos, value
            Map<Integer, Integer> numbersInLine = new HashMap<>();

            //check for spelt out numbers
            for (String number : numbers.keySet()) {
                int pos = line.lastIndexOf(number);
                if (pos != -1) {
                    //got one
                    numbersInLine.put(pos, numbers.get(number));
                }
            }

            //check for digits
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (Character.isDigit(c)) {
                    numbersInLine.put(i, Character.getNumericValue(c));
                }
            }

            List<Integer> numbersFoundInOrder = numbersInLine.keySet().stream().sorted().toList();

            String first = numbersInLine.get(numbersFoundInOrder.get(0)).toString();
            Integer lastNumber = numbersInLine.get(numbersFoundInOrder.get(numbersFoundInOrder.size() - 1));
            String last = lastNumber == null ? first : lastNumber.toString();


            String toAdd = first + last;
            total += Integer.parseInt(toAdd);

            log.info("line {} toAdd : {} with total : {}", line, toAdd, total);
        }

        log.info("Finished with {}", total);
    }
}
