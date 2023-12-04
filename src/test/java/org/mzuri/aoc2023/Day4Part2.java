package org.mzuri.aoc2023;

import kotlin.ranges.IntRange;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class Day4Part2 extends AdventOfCode2023Test {

    record Card(List<Integer> winningNumbers,  List<Integer> cardNumbers){

    }


    @Test
    void test() throws URISyntaxException, IOException {
        List<String> lines = loadInput("day4.txt");
        int result = 0;

        List<Card> cardList = lines.stream().map(this::getCardFromLine).toList();
        Map<Integer, Integer> mapOfCardLineNumberToNumberOfCopies = new HashMap<>();

        for (int i = 0; i < cardList.size(); i++) {
            Card card = cardList.get(i);
            long numberOfWinningNumbers = card.cardNumbers.stream().filter(card.winningNumbers()::contains).count();

            Integer noOfCopies = mapOfCardLineNumberToNumberOfCopies.get(i);
            int incrementValue = noOfCopies == null ? 1 : noOfCopies + 1;
            log.debug("Have {} winners for card {} with no of copies {}", numberOfWinningNumbers, i, incrementValue);
            for (int j = i + 1; j < i + 1 + numberOfWinningNumbers; j++) {
                Integer numberOfCopies = mapOfCardLineNumberToNumberOfCopies.get(j);
                Integer newValue = numberOfCopies == null ? incrementValue : numberOfCopies + incrementValue;
                mapOfCardLineNumberToNumberOfCopies.put(j, newValue);
                log.debug("Updating card {} with noOfCopies {}", j, newValue);
            }
        }

        //calculate result
        for (int i = 0; i < cardList.size(); i++) {
            Integer noOfCopies = mapOfCardLineNumberToNumberOfCopies.get(i);
            if(noOfCopies == null) noOfCopies = 0;
            noOfCopies++; //take care of original
            log.debug("Get {} cards for card number {}", noOfCopies, i);
            result += noOfCopies;
        }

        log.info("result {}", result);

//        assertEquals(22674, result);
    }

    private Card getCardFromLine(String line) {
        //winning numbers
        String winningNumbersText = line.substring(line.indexOf(":") + 2, line.indexOf("|") - 1);
        List<Integer> winningNumbers = extractNumbers(winningNumbersText);

        //my numbers
        String cardNumbersText = line.substring(line.indexOf("|") + 2);
        List<Integer> cardNumbers = extractNumbers(cardNumbersText);

        return new Card(winningNumbers, cardNumbers);
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
