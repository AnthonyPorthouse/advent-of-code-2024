package dev.porthouse.solutions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.porthouse.ISolution;

public class Day3SolutionTest {

    private ISolution solution;

    @BeforeEach
    public void init() {
        solution = new Day3Solution("day03-example.txt");
    }

    @Test
    void part1MatchesExample() {
        assertEquals(161, solution.runPart1());
    }

    @Test
    void part2MatchesExample() {
        solution = new Day3Solution("day03-example2.txt");
        assertEquals(48, solution.runPart2());
    }
}
