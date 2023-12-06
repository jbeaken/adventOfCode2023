package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j(topic = "Day6")
/**
 * Time:      7  15   30
 * Distance:  9  40  200
 */
public class Day6Part1 extends AdventOfCode2023Test {

    record Race(Integer time, Integer distance){}

    @Test
    void test() throws URISyntaxException, IOException {
        List<String> lines = loadInput("day6.txt");
        int result = 1;

        Pattern pattern = Pattern.compile("\\d{1,3}");
        Matcher timeMatcher = pattern.matcher(lines.get(0));
        Matcher distanceMatcher = pattern.matcher(lines.get(1));

        List<Race> raceList = new ArrayList<>();
        while(timeMatcher.find() && distanceMatcher.find()) {
            Integer time = Integer.valueOf(timeMatcher.group());
            Integer distance = Integer.valueOf(distanceMatcher.group());
            Race race = new Race(time, distance);
            raceList.add(race);
        }

        //cycle through races, getting number of ways to beat record
        for (Race race : raceList) {
            int noOfWaysToWin = 0;
            for (int milisecondsPressed = 1; milisecondsPressed < race.time; milisecondsPressed++) {
                if(milisecondsPressed * (race.time - milisecondsPressed) > race.distance) {
                    //beat record
                    noOfWaysToWin++;
                }
            }
            result *= noOfWaysToWin;
        }

        Assertions.assertEquals(288, result);
    }
}
