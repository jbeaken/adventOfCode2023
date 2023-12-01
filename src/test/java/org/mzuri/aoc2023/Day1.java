package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class Day1 {


    @Test
    void test() throws IOException, URISyntaxException {
        URI uri = Day1.class.getResource("../../../day1/input.txt").toURI();
        List<String> calibrations = Files.readAllLines(Path.of(uri));

        Integer total = 0;
        for(String line : calibrations) {
            Integer first = null;
            Integer last = null;

            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    int numericValue = Character.getNumericValue(c);
                    if (first == null) {
                        first = numericValue;
                    }
                    last = numericValue;
                }
            }

            if(last == null) {
                last = first;
            }
            total += (first + last);

            log.info("line {} with first : {} and last : {} with total : {}", line, first, last, total);



        }
        log.info("Finished with {}", total);

    }
}
