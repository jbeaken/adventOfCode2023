package org.mzuri.aoc2023.day1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class Day1 {


    @Test
    void test() throws IOException, URISyntaxException {
        URI uri = Day1.class.getResource("day1Input.txt").toURI();
        List<String> strings = Files.readAllLines(Path.of(uri));
    }
}
