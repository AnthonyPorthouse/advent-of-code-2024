package dev.porthouse.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import dev.porthouse.BaseSolution;

public class Day2Solution extends BaseSolution {

    enum Direction {
        Ascending,
        Descending,
    }

    public static final Logger LOGGER = Logger.getLogger(Day2Solution.class.getName());

    public Day2Solution(String file) {
        super(file);
    }

    @Override
    public Integer runPart1() {

        Integer safeTotal = 0;

        for (String line: input.lines().toList()) {
            List<Integer> values = lineToIntegers(line);

            if (isSafe(values)) {
                LOGGER.info(String.format("%s is safe", line));
                safeTotal += 1;
                continue;
            }

            LOGGER.info(String.format("%s is not safe", line));
        }

        return safeTotal;
    }

    @Override
    public Integer runPart2() {
        Integer safeTotal = 0;

        for (String line: input.lines().toList()) {
            List<Integer> values = lineToIntegers(line);

            if (isSafeWithDamper(values)) {
                LOGGER.info(String.format("%s is safe", line));
                safeTotal += 1;
                continue;
            }

            LOGGER.info(String.format("%s is not safe", line));
        }

        return safeTotal;
    }

    private List<Integer> lineToIntegers(String line) {
        return new ArrayList<>(Arrays.asList(line.split(" ")).stream().map((value) -> Integer.valueOf(value)).toList());
    }

    private Boolean isSafe(List<Integer> values) {

        Boolean incrementing = isIncrementing(values.get(0), values.get(1));

        for (int i = 0; i < values.size() - 1; i++) {
            Integer a = values.get(i);
            Integer b = values.get(i + 1);

            if (!incrementing.equals(isIncrementing(a, b))) {
                LOGGER.info(String.format("%d %d is not %s", a, b, incrementing ? "incrementing" : "decrementing"));
                return false;
            }

            if (!isSafeDistance(a, b)) {
                LOGGER.info(String.format("%d %d is not a safe distance", a, b));
                return false;
            }
        }

        return true;
    }

    private Boolean isSafeWithDamper(List<Integer> values) {

        if (isSafe(values)) {
            return true;
        }

        for (int i = 0; i < values.size(); i++) {
            var newValues = new ArrayList<>(values);
            newValues.remove(i);

            if (isSafe(newValues)) {
                return true;
            }
        }

        return false;
    }


    private Boolean isIncrementing(Integer a, Integer b) {
        return a < b;
    }

    private Boolean isSafeDistance(Integer a, Integer b) {
        Integer distance = Integer.max(a, b) - Integer.min(a, b);
        return 0 < distance && distance <= 3;
    }
}
