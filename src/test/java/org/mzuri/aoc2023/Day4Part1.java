package org.mzuri.aoc2023;

import kotlin.ranges.IntRange;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
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

        for (int i = 0; i < cards.size(); i++) {
            String card = cards.get(i);

            //winning numbers
            String winningNumbersText = card.substring(card.indexOf(":") + 2, card.indexOf("|") - 1);
            List<Integer> winningNumbers = extractNumbers(winningNumbersText);

            //my numbers
            String myNumbersText = card.substring(card.indexOf("|") + 2);
            List<Integer> myNumbers = extractNumbers(myNumbersText);
            double pow = Math.pow(2, 0);  //todo
            Integer cardResult = myNumbers.stream().filter(winningNumbers::contains).reduce(0, timesByTwoUnlessZeroThenSetToOne());
            long noOfWinningNumbers = myNumbers.stream().filter(winningNumbers::contains).count();

            result += cardResult;

            log.debug("card {} : {} {}", i, cardResult, noOfWinningNumbers);
        }

        log.info("result {}", result);

        assertEquals(22674, result);
    }

    @NotNull
    private static BinaryOperator<Integer> timesByTwoUnlessZeroThenSetToOne() {
        return (a, b) -> a == 0 ? 1 : a * 2;
    }

    @NotNull
    private static List<Integer> extractNumbers(String text) {
        Pattern numberPattern = Pattern.compile("\\d{1,2}");
        Matcher numbersMatcher = numberPattern.matcher(text);

        List<Integer> numbers = new ArrayList<>();

        while (numbersMatcher.find()) {
            numbers.add(Integer.parseInt(numbersMatcher.group()));
        }

        return numbers;
    }
}
