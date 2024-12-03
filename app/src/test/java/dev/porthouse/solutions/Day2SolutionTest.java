package dev.porthouse.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day2SolutionTest {
    @Test
    void part1MatchesExample() {
        Day2Solution solution = new Day2Solution("day02-example.txt");
        assertEquals(2, solution.runPart1());
    }

    @Test
    void part2MatchesExample() {
        Day2Solution solution = new Day2Solution("day02-example.txt");
        assertEquals(4, solution.runPart2());
    }
}
