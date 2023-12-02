package org.mzuri.aoc2023;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class AdventOfCode2023Test {

    List<String> getInput() throws URISyntaxException, IOException {
        URI uri = Day2.class.getResource("../../../input/day1.txt").toURI();
        return Files.readAllLines(Path.of(uri));
    }
}
