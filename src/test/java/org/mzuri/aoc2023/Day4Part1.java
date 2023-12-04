package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The engine schematic (your puzzle input) consists of a visual representation of the engine.
 * There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol,
 * even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 */
@Slf4j(topic = "Day4")
public class Day4Part1 extends AdventOfCode2023Test {

    List<String> cards;

    @BeforeEach
    void beforeEach() throws URISyntaxException, IOException {
        cards = loadInput("day4.txt");
    }

    @Test
    void test() {
        int result = 0;

        Pattern numberPattern = Pattern.compile("\\d{1,2}");

        for (int i = 0; i < cards.size(); i++) {
            String card = cards.get(i);

            //winning numbers
            String winningNumbersText = card.substring(card.indexOf(":") + 2, card.indexOf("|") - 1);
            Matcher winningNumbersMatcher = numberPattern.matcher(winningNumbersText);
            List<Integer> winningNumbers = new ArrayList<>();
            while (winningNumbersMatcher.find()) {
                winningNumbers.add(Integer.parseInt(winningNumbersMatcher.group()));
            }

            //my numbers
            String myNumbersText = card.substring(card.indexOf("|"));
            Matcher muNumbersMatcher = numberPattern.matcher(myNumbersText);
            muNumbersMatcher.find();
            List<Integer> myNumbers = new ArrayList<>();
            while (muNumbersMatcher.find()) {
                winningNumbers.add(Integer.parseInt(muNumbersMatcher.group()));
            }

            myNumbers.stream().filter(winningNumbers::contains).reduce(1, (a, b) -> a * 2).v;


        }
    }
}
