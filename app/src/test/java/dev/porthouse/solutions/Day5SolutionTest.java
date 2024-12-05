package dev.porthouse.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.porthouse.ISolution;

public class Day5SolutionTest {

    private ISolution solution;

    @BeforeEach
    public void init() {
        solution = new Day5Solution("day05-example.txt");
    }

    @Test
    void part1MatchesExample() {
        assertEquals(143, solution.runPart1());
    }

    @Test
    void part2MatchesExample() {
        assertEquals(-1, solution.runPart2());
    }
}
