package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j(topic = "Day6")
/**
 * Time:      7  15   30
 * Distance:  9  40  200
 */
public class Day6Part2 extends AdventOfCode2023Test {

    record Race(Long time, Long distance){}

    @Test
    void test() throws URISyntaxException, IOException {
        List<String> lines = loadInput("day6.txt");
        int result = 1;

        Pattern pattern = Pattern.compile("\\d{1,4}");
        Matcher timeMatcher = pattern.matcher(lines.get(0));
        Matcher distanceMatcher = pattern.matcher(lines.get(1));

        List<Race> raceList = new ArrayList<>();
        StringBuilder timeBuilder = new StringBuilder();
        StringBuilder distanceBuilder = new StringBuilder();
        while(timeMatcher.find() && distanceMatcher.find()) {
            timeBuilder.append(timeMatcher.group());
            distanceBuilder.append(distanceMatcher.group());

        }
        Race race = new Race(Long.parseLong(timeBuilder.toString()), Long.parseLong(distanceBuilder.toString()));
        raceList.add(race);

        //cycle through races, getting number of ways to beat record
        for (Race r : raceList) {
            log.debug("r {}", r);
            int noOfWaysToWin = 0;
            for (int milisecondsPressed = 1; milisecondsPressed < r.time; milisecondsPressed++) {
                if(milisecondsPressed * (r.time - milisecondsPressed) > r.distance) {
                    //beat record
//                    log.info("" + milisecondsPressed);
                    noOfWaysToWin++;
                }
            }
            result *= noOfWaysToWin;
//            log.info("noOfWaysToWin {}", noOfWaysToWin);
        }

        Assertions.assertEquals(288, result);
    }
}
