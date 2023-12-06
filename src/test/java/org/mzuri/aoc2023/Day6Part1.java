package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j(topic = "Day6")
/**
 * Time:      7  15   30
 * Distance:  9  40  200
 */
public class Day6Part1 extends AdventOfCode2023Test {

    record AlmanacRange(Long destinationStart, Long sourceStart, Long range) {}

    record Race(Integer duration, Integer record){}

    @Test
    void test() throws URISyntaxException, IOException {
        List<String> lines = loadInput("day6.txt");
        int result = 0;

        lines.get(0).to

        Assertions.assertEquals(177942185, result);
    }
}
