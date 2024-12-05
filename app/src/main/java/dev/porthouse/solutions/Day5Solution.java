package dev.porthouse.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.porthouse.BaseSolution;

public class Day5Solution extends BaseSolution {

    private Map<Integer, List<Integer>> rules;
    private List<List<Integer>> updates;

    public Day5Solution(String file) {
        super(file);

        rules = new HashMap<>();
        updates = new ArrayList<>();

        var settings = new Object(){ boolean isExamples = false; };

        input.lines().forEach((line) -> {
            if (line.isBlank()) {
                settings.isExamples = true;
                return;
            }

            if (!settings.isExamples) {
                var chunks = line.split("\\|");

                var key = Integer.valueOf(chunks[0]);
                var value = Integer.valueOf(chunks[1]);

                rules.putIfAbsent(key, new ArrayList<>());
                rules.get(key).add(value);
                return;
            }

            updates.add(Arrays.stream(line.split(","))
                .map((val) -> Integer.valueOf(val))
                .toList()
            );
        });
    }

    @Override
    public Integer runPart1() {

        var validUpdates = new ArrayList<List<Integer>>();

        updates.forEach((update) -> {
            if (isValidUpdate(update)) {
                validUpdates.add(update);
            }
        });

        return validUpdates.stream()
            .reduce(0, (acc, list) -> acc + list.get(list.size() / 2), Integer::sum);
    }

    @Override
    public Integer runPart2() {
        var invalidUpdates = new ArrayList<List<Integer>>();

        updates.forEach((update) -> {
            if (!isValidUpdate(update)) {
                invalidUpdates.add(sortUpdate(update));
            }
        });

        return invalidUpdates.stream()
            .reduce(0, (acc, list) -> acc + list.get(list.size() / 2), Integer::sum);
    }

    private Boolean isValidUpdate(List<Integer> update) {

        Set<Integer> currentVals = new HashSet<>();

        for (Integer val : update) {
            for (Integer check: rules.getOrDefault(val, new ArrayList<>())) {
                if (currentVals.contains(check)) {
                    return false;
                }
            }

            currentVals.add(val);
        }

        return true;
    }

    private List<Integer> sortUpdate(List<Integer> update) {

        List<Integer> sortedUpdate = new ArrayList<>(update);

        sortedUpdate.sort((a, b) -> {
            if (rules.getOrDefault(a, new ArrayList<>()).contains(b)) {
                return -1;
            }

            if (a.equals(b)) {
                return 0;
            }

            return 1;
        });

        return sortedUpdate;
    }
}
