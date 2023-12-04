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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The engine schematic (your puzzle input) consists of a visual representation of the engine.
 * There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol,
 * even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 */
@Slf4j(topic = "Day4")
public class Day4Part1 extends AdventOfCode2023Test {

    List<String> cards;

    record Card(Integer gameId, List<Integer> winningNumbers,  List<Integer> cardNumbers) {}

    @BeforeEach
    void beforeEach() throws URISyntaxException, IOException {
        cards = loadInput("day4.txt");
    }

    @Test
    void test() throws URISyntaxException, IOException {
        long result = cards.stream().map(this::getCardFromLine)
                .map(card -> card.cardNumbers().stream().filter(card.winningNumbers::contains).reduce(0, timesByTwoUnlessZeroThenSetToOne()))
                .mapToInt(Integer::intValue)
                .sum();

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

    private Card getCardFromLine(String line) {
        //Get card number
        Pattern cardNumberPattern = Pattern.compile("\\d{1,3}");
        Matcher matcher = cardNumberPattern.matcher(line);
        matcher.find();
        String id = matcher.group();

        //winning numbers
        String winningNumbersText = line.substring(line.indexOf(":") + 2, line.indexOf("|") - 1);
        List<Integer> winningNumbers = extractNumbers(winningNumbersText);

        //my numbers
        String cardNumbersText = line.substring(line.indexOf("|") + 2);
        List<Integer> cardNumbers = extractNumbers(cardNumbersText);

        return new Card(Integer.parseInt(id), winningNumbers, cardNumbers);
    }
}
