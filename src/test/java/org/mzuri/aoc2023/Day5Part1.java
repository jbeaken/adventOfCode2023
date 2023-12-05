package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**


 The almanac (your puzzle input) lists all of the seeds that need to be planted.
 It also lists what type of soil to use with each kind of seed, what type of fertilizer to use with each kind of soil,
 what type of water to use with each kind of fertilizer, and so on. Every type of seed, soil, fertilizer and so on is identified with a number,
 but numbers are reused by each category - that is, soil 123 and fertilizer 123 aren't necessarily related to each other.
 */
@Slf4j(topic = "Day5")
public class Day5Part1 extends AdventOfCode2023Test {

    record AlmanacRange(Long destinationStart, Long sourceStart, Long range) {}

    @Test
    void test() throws URISyntaxException, IOException {
        List<String> lines = loadInput("day5.txt");
        List<List<AlmanacRange>> almanacRangeList = new ArrayList<>();

        long result = Long.MAX_VALUE;

        List<Long> seeds = Arrays.stream(lines.get(0).split(": ")[1].split(" "))
                .map(String::trim)
                .map(Long::parseLong).toList();

        int mapCount = 0;
        List<AlmanacRange> currentList = new ArrayList<>();
        for (int i = 3; i < lines.size(); i++) {
            String line = lines.get(i);
            if(line.isEmpty()) {
                almanacRangeList.add(currentList);
                currentList = new ArrayList<>();
                mapCount++;
                i++;
                continue;
            }
            List<Long> almanacSource = Arrays.stream(line.split(" ")).map(String::trim).map(Long::parseLong).toList();
            AlmanacRange almanacRange = new AlmanacRange(almanacSource.get(0), almanacSource.get(1), almanacSource.get(2));
            currentList.add(almanacRange);
        }
        //last one
        almanacRangeList.add(currentList);


        //go thorough seeds
        for (int i = 0; i < seeds.size(); i++) {
            Long seed = seeds.get(i);
            for (int j = 0; j < almanacRangeList.size(); j++) {
                //is in range?
                List<AlmanacRange> almanacRanges = almanacRangeList.get(j);
                for (int k = 0; k < almanacRanges.size(); k++) {
                    AlmanacRange almanacRange = almanacRanges.get(k);
                    if(seed >= almanacRange.sourceStart && seed <= almanacRange.sourceStart + almanacRange.range) {
                        //got a match
                        seed = seed + almanacRange.destinationStart - almanacRange.sourceStart;
                        break;
                    }
                }
                log.info("j {} seed {}", j, seed);
            }
            log.info("i {} seed {}", i, seed);
            log.info("");
            result = Math.min(result, seed);
        }
        //Seed 14, soil 14, fertilizer 53, water 49, light 42, temperature 42, humidity 43, location 43.
        log.info("result : {}", result);

    }
}
