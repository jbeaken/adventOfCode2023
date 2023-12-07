package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j(topic = "Day7")
public class Day7Part1 extends AdventOfCode2023Test {

    record Hand(Integer bid, Map<Integer, Long> result){}

    enum Rank {}

    @Test
    void test() throws URISyntaxException, IOException {
        List<String> lines = loadInput("day7.txt");
        int result = 0;

        lines.stream().map(this::getHand).toList();


        Assertions.assertEquals(288, result);
    }

    private Hand getHand(String line) {
        List<Integer> cards = line.chars().limit(5).boxed().toList();

        Map<Integer, Long> result = cards.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

       return new Hand(Integer.parseInt(line.substring(6)), result);

    }
}
