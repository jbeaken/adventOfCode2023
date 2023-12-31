package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Slf4j
abstract class AdventOfCode2023Test {

    final String BASE_PATH = "../../../input/%s";

    List<String> loadInput(String fileName) throws URISyntaxException, IOException {
        String absoluteFilename = String.format(BASE_PATH, fileName);

        log.info("Loading input from file {}", absoluteFilename);

        URI uri = AdventOfCode2023Test.class.getResource(absoluteFilename).toURI();
        List<String> lines = Files.readAllLines(Path.of(uri));

        return Collections.unmodifiableList(lines);
    }
}
