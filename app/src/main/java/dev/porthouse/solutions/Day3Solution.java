package dev.porthouse.solutions;

import java.util.logging.Logger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import dev.porthouse.BaseSolution;

public class Day3Solution extends BaseSolution<Integer> {

    public static final Logger LOGGER = Logger.getLogger(Day2Solution.class.getName());


    public Day3Solution(String file) {
        super(file);
    }

    @Override
    public Integer runPart1() {

        String inputValue = input.lines().reduce("", (acc, val) -> acc + val);

        Stream<String> muls = getValidMuls(inputValue);

        Pattern valuesPattern = Pattern.compile("^mul\\((\\d+),(\\d+)\\)$");

        return muls.map((input) -> {
            return valuesPattern
                .matcher(input)
                .results()
                .map((match) -> {
                    Integer val = Integer.valueOf(match.group(1)) * Integer.valueOf(match.group(2));
                    return val;
                })
                .reduce(0, (acc, val) -> acc + val);
        })
        .reduce(0, (acc, val) -> acc + val);
    }

    @Override
    public Integer runPart2() {

        var inputValue = input.lines().reduce("", (acc, val) -> acc + val);
        var muls = getValidMulsWithPauses(inputValue);
        var valuesPattern = Pattern.compile("^mul\\((\\d+),(\\d+)\\)$|^do\\(\\)$|^don\\'t\\(\\)$");
        var wrapper = new Object(){ boolean paused = false; };

        return muls.map((input) -> {
            return valuesPattern
                .matcher(input)
                .results()
                .map((match) -> {
                    if (match.group().equals("do()")) {
                        wrapper.paused = false;
                        return 0;
                    }

                    if (match.group().equals("don't()")) {
                        wrapper.paused = true;

                        return 0;
                    }

                    if (wrapper.paused) {
                        return 0;
                    }

                    Integer val = Integer.valueOf(match.group(1)) * Integer.valueOf(match.group(2));
                    return val;
                })
                .reduce(0, (acc, val) -> acc + val);
        })
        .reduce(0, (acc, val) -> acc + val);
    }

    private Stream<String> getValidMuls(String input) {
        return Pattern
            .compile("mul\\(\\d+,\\d+\\)")
            .matcher(input)
            .results()
            .map(MatchResult::group);
    }

    private Stream<String> getValidMulsWithPauses(String input) {
        return Pattern
            .compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)")
            .matcher(input)
            .results()
            .map(MatchResult::group);
    }
}
