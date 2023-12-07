package org.mzuri.aoc2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j(topic = "Day7")
public class Day7Part1 extends AdventOfCode2023Test {

    record Hand(Integer bid, String cards, Map<Integer, Long> result){}

    enum Rank {
        FOUR_OF_A_KIND(4),
        FULL_HOUSE(3), TWO_PAIR(1),
        ONE_PAIR(0),
        HIGH_CARD(-1),
        FIVE_OF_A_KIND(5),
        THREE_OF_A_KIND(2);

        private final int score;

        Rank(int score) {
            this.score = score;
        }
    }

    @Test
    void test() throws URISyntaxException, IOException {
        List<String> lines = loadInput("day7.txt");
        int result = 0;

        List<Hand> handList = lines.stream().map(this::getHand).sorted(this::sortHands).toList();

        Assertions.assertEquals(288, result);
    }

    private int sortHands(Hand thiz, Hand that) {
        Rank rankThiz = getRank(thiz);
        Rank rankThat = getRank(that);
        int result = rankThat.score - rankThiz.score;

        if(result == 0) {
            return thiz.cards.charAt(0) > that.cards.charAt(0) ? 1 : -1;
        }

        return result;
    }

    private Rank getRank(Hand hand) {
        if(hand.result.size() == 1) {
            return Rank.FIVE_OF_A_KIND;
        }
        if(hand.result.size() == 2) {
            //four of a kind or full house
            return (hand.result.get(0) == 1 || hand.result.get(0) == 4) ? Rank.FOUR_OF_A_KIND : Rank.FULL_HOUSE;
        }
        if(hand.result.size() == 3) {
            //two pair or three of a kind
            Optional<Long> hasThreeOfAKind = hand.result.values().stream().filter( l -> l > 2).findFirst();
            return hasThreeOfAKind.isPresent() ? Rank.THREE_OF_A_KIND : Rank.TWO_PAIR;
        }
        if(hand.result.size() == 4) {
            return Rank.ONE_PAIR;
        }
        return Rank.HIGH_CARD;
    }

    private Hand getHand(String line) {
        List<Integer> cards = line.chars().limit(5).boxed().toList();

        Map<Integer, Long> result = cards.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

       return new Hand(Integer.parseInt(line.substring(6)), line, result);
    }
}
