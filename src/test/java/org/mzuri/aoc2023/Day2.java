package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
 *
 * For example, the record of a few games might look like this:
 *
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 *
 * In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.
 *
 * The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
 *
 * In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.
 *
 * Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
 */
@Slf4j(topic = "Day2")
public class Day2 extends AdventOfCode2023Test {

    record Game(Integer id, Boolean isPossible) {}
    record ColourConfig(Integer skipLength, Integer maxAllowed) {}

    Map<Character, ColourConfig> colourMap = Map.of(
            'r', new ColourConfig(5, 12),
            'g', new ColourConfig(7, 13),
            'b', new ColourConfig(6, 14));

    @Test
    void test1() throws IOException, URISyntaxException {
        List<String> lines = loadInput("day2.txt");

        int result = 0;

        for(String line : lines) {
            Game game = getGameFromLine(line);
            if(game.isPossible) {
                result += game.id;
            }
        }

        log.info("Result {}", result);
    }

    private Game getGameFromLine(String line) {
        //get id, between first space and :
        int startId = line.indexOf(" ");
        int endId = line.indexOf(":");
        Integer id = Integer.valueOf(line.substring(startId + 1, endId));

        String turns = line.substring(endId + 2);
        for (int i = 0; i < turns.length(); i++) {
            char c = turns.charAt(i);

            if(colourMap.containsKey(c)) {
                //got one,
                StringBuffer numberHolder = new StringBuffer(2);

                if (i > 2 && Character.isDigit(turns.charAt(i - 3))) {
                    numberHolder.append(turns.charAt(i - 3));
                }
                numberHolder.append(turns.charAt(i - 2));

                int amount = Integer.parseInt(numberHolder.toString());

                ColourConfig colourConfig = colourMap.get(c);
                i += colourConfig.skipLength;

                if(amount > colourConfig.maxAllowed) {
                    return new Game(id, false);
                }

            }
        }

        return new Game(id, true);
    }

}
