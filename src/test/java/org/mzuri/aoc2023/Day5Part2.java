package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.LongStream;

/**
 The almanac (your puzzle input) lists all of the seeds that need to be planted.
 It also lists what type of soil to use with each kind of seed, what type of fertilizer to use with each kind of soil,
 what type of water to use with each kind of fertilizer, and so on. Every type of seed, soil, fertilizer and so on is identified with a number,
 but numbers are reused by each category - that is, soil 123 and fertilizer 123 aren't necessarily related to each other.
 */
@Slf4j(topic = "Day5")
public class Day5Part2 extends AdventOfCode2023Test {

    record AlmanacRange(Long destinationStart, Long sourceStart, Long range) {}

    @Test
    void test() throws URISyntaxException, IOException {
        List<String> lines = loadInput("day5.txt");

        long result = Long.MAX_VALUE;

        List<Long> seedRangeList = Arrays.stream(lines.get(0).split(": ")[1].split(" "))
                .map(String::trim)
                .map(Long::parseLong).toList();

        List<List<AlmanacRange>> almanacRangeList = getAlmanacRangeList(lines);

        for (int i = 0; i < seedRangeList.size(); i+= 2) {
            Long startInclusive = seedRangeList.get(i);
            long endInclusive = startInclusive + seedRangeList.get(i + 1);

            OptionalLong min = LongStream.range(startInclusive, endInclusive).map(seedValue -> {
                log.info("seedValue {}", seedValue);
                for (int j = 0; j < almanacRangeList.size(); j++) {
                    //is in range?
                    List<AlmanacRange> almanacRanges = almanacRangeList.get(j);
                    for (int k = 0; k < almanacRanges.size(); k++) {
                        AlmanacRange almanacRange = almanacRanges.get(k);
                        if (seedValue >= almanacRange.sourceStart && seedValue <= almanacRange.sourceStart + almanacRange.range) {
                            //got a match
                            seedValue += almanacRange.destinationStart - almanacRange.sourceStart;
                            break;
                        }
                    }
                }
                return seedValue;
            }).min();

            if(min.isPresent()){
                result = Math.min(result, min.getAsLong());
            }
        }

        log.info("result : {}", result);
    }

    @NotNull
    private static List<List<AlmanacRange>> getAlmanacRangeList(List<String> lines) {
        List<List<AlmanacRange>> almanacRangeList = new ArrayList<>();
        List<AlmanacRange> currentList = new ArrayList<>();
        for (int i = 3; i < lines.size(); i++) {
            String line = lines.get(i);
            if(line.isEmpty()) {
                almanacRangeList.add(currentList);
                currentList = new ArrayList<>();
                i++;
                continue;
            }
            List<Long> almanacSource = Arrays.stream(line.split(" ")).map(String::trim).map(Long::parseLong).toList();
            AlmanacRange almanacRange = new AlmanacRange(almanacSource.get(0), almanacSource.get(1), almanacSource.get(2));
            currentList.add(almanacRange);
        }
        //last one
        almanacRangeList.add(currentList);
        return almanacRangeList;
    }
}
