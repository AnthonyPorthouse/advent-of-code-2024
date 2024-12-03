package dev.porthouse.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

        HashMap<Direction, Integer> directionCount = new HashMap<>();
        directionCount.put(Direction.Ascending, 0);
        directionCount.put(Direction.Descending, 0);

        Integer unsafeGaps = 0;

        for (int i = 0; i < values.size() - 1; i++) {
            Integer a = values.get(i);
            Integer b = values.get(i + 1);

            if (isIncrementing(a, b)) {
                directionCount.put(Direction.Ascending, directionCount.get(Direction.Ascending) + 1);
            } else {
                directionCount.put(Direction.Descending, directionCount.get(Direction.Descending) + 1);
            }

            if (directionCount.get(Direction.Ascending) > 1 && directionCount.get(Direction.Descending) > 1) {
                LOGGER.info("Too many direction changes");
                return false;
            }

            // Check number distance
            if (!isSafeDistance(a, b)) {
                // if start or end element, we can always remove
                if (i == 0 || i == values.size() - 1) {
                    unsafeGaps += 1;
                }

                // If distance between a and c is too large we skip
                if (i < values.size() - 2) {
                    Integer c = values.get(i + 2);
                    if (!isSafeDistance(a, c)) {
                        return false;
                    } else {
                        unsafeGaps += 1;
                    }
                }

            }

            // if too many unsafe gaps, skip
            if (unsafeGaps > 1) {
                LOGGER.info("Too many gaps");
                return false;
            }
        }

        return true;
    }


    private Boolean isIncrementing(Integer a, Integer b) {
        return a < b;
    }

    private Boolean isSafeDistance(Integer a, Integer b) {
        Integer distance = Integer.max(a, b) - Integer.min(a, b);
        return 0 < distance && distance <= 3;
    }
}
