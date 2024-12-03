package dev.porthouse.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Day1SolutionTest {
    @Test void part1MatchesExample() {
        Day1Solution solution = new Day1Solution("day01-example.txt");
        assertEquals(solution.runPart1(), 11);
    }


    @Test void part2MatchesExample() {
        Day1Solution solution = new Day1Solution("day01-example.txt");
        assertEquals(solution.runPart2(), 31);
    }
}
