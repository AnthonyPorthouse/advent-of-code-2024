package dev.porthouse.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.porthouse.ISolution;

public class Day6SolutionTest {
private ISolution solution;

    @BeforeEach
    public void init() {
        solution = new Day6Solution("day06-example.txt");
    }

    @Test
    void part1MatchesExample() {
        assertEquals(41, solution.runPart1());
    }

    @Test
    void part2MatchesExample() {
        assertEquals(-1, solution.runPart2());
    }
}
